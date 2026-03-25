#!/bin/bash
set -euo pipefail

echo "[localstack-init] create S3 bucket & CORS"

ENDPOINT="http://localhost:4566"
BUCKET="hwhub-dev-file"
KNOWLEDGE_BUCKET="hwhub-knowledge-dev"
INIT_DIR="/etc/localstack/init/ready.d"

# awslocal / aws が入っているのでどちらでもOK
# awslocal を使うがもし awslocal が無い場合は 'aws --endpoint-url=...' を使う

if command -v awslocal >/dev/null 2>&1; then
  AWS_CMD="awslocal"
else
  AWS_CMD="aws --endpoint-url=${ENDPOINT}"
fi

# 添付ファイル用バケット作成
$AWS_CMD s3 mb "s3://${BUCKET}" 2>/dev/null || true

# CORS 設定
$AWS_CMD s3api put-bucket-cors \
  --bucket "${BUCKET}" \
  --cors-configuration file:///etc/localstack/init/ready.d/s3-cors.json

# ナレッジ用バケット作成
$AWS_CMD s3 mb "s3://${KNOWLEDGE_BUCKET}" 2>/dev/null || true
echo "[localstack-init] knowledge bucket created: ${KNOWLEDGE_BUCKET}"

# faq.md / howto.md をアップロード（ファイルがあれば）
for FILE in faq.md howto.md; do
  if [ -f "${INIT_DIR}/${FILE}" ]; then
    $AWS_CMD s3 cp "${INIT_DIR}/${FILE}" "s3://${KNOWLEDGE_BUCKET}/${FILE}"
    echo "[localstack-init] uploaded: ${FILE}"
  else
    echo "[localstack-init] skipped (not found): ${FILE}"
  fi
done

echo "[localstack-init] S3 init done"

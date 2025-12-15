#!/bin/bash
set -euo pipefail

echo "[localstack-init] create S3 bucket & CORS"

ENDPOINT="http://localhost:4566"
BUCKET="hwhub-dev-file"

# LocalStack イメージには基本的に awslocal / aws が入っているのでどちらでも OK
# ここでは awslocal を使う書き方にしておきます
# （もし awslocal が無いと怒られたら 'aws --endpoint-url=...' に変える）

if command -v awslocal >/dev/null 2>&1; then
  AWS_CMD="awslocal"
else
  AWS_CMD="aws --endpoint-url=${ENDPOINT}"
fi

# バケット作成（既にある場合はエラー無視）
$AWS_CMD s3 mb "s3://${BUCKET}" 2>/dev/null || true

# CORS 設定
$AWS_CMD s3api put-bucket-cors \
  --bucket "${BUCKET}" \
  --cors-configuration file:///etc/localstack/init/ready.d/s3-cors.json

echo "[localstack-init] S3 init done"

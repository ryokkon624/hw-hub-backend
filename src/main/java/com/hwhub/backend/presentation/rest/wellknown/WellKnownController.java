package com.hwhub.backend.presentation.rest.wellknown;

import com.hwhub.backend.config.DeepLinkProperties;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/.well-known")
@RequiredArgsConstructor
public class WellKnownController {

  private final DeepLinkProperties props;

  /** iOS Universal Links 用 AASA ファイルを配信する。 */
  @GetMapping(value = "/apple-app-site-association", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Object>> appleAppSiteAssociation() {
    Map<String, Object> component1 = new LinkedHashMap<>();
    component1.put("/", "/email-verify");

    Map<String, Object> component2 = new LinkedHashMap<>();
    component2.put("/", "/invite/*");

    Map<String, Object> component3 = new LinkedHashMap<>();
    component3.put("/", "/password/reset");

    Map<String, Object> detail = new LinkedHashMap<>();
    detail.put("appIDs", List.of(props.getIosAppId()));
    detail.put("components", List.of(component1, component2, component3));

    Map<String, Object> applinks = new LinkedHashMap<>();
    applinks.put("details", List.of(detail));

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("applinks", applinks);

    return ResponseEntity.ok(body);
  }

  /** Android App Links 用 assetlinks.json を配信する。 */
  @GetMapping(value = "/assetlinks.json", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Map<String, Object>>> assetlinks() {
    Map<String, Object> target = new LinkedHashMap<>();
    target.put("namespace", "android_app");
    target.put("package_name", props.getAndroidPackageName());
    target.put("sha256_cert_fingerprints", List.of(props.getAndroidSha256CertFingerprint()));

    Map<String, Object> entry = new LinkedHashMap<>();
    entry.put("relation", List.of("delegate_permission/common.handle_all_urls"));
    entry.put("target", target);

    return ResponseEntity.ok(List.of(entry));
  }
}

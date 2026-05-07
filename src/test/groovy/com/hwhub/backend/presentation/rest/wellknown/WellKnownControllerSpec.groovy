package com.hwhub.backend.presentation.rest.wellknown

import com.hwhub.backend.config.DeepLinkProperties
import org.springframework.http.HttpStatus
import spock.lang.Specification

class WellKnownControllerSpec extends Specification {

    DeepLinkProperties props = new DeepLinkProperties(
            iosAppId: "ABCDE12345.com.hwhub.app",
            androidPackageName: "com.hwhub.app",
            androidSha256CertFingerprint: "AA:BB:CC:DD:EE:FF"
    )
    WellKnownController controller = new WellKnownController(props)

    def "appleAppSiteAssociationは200とAASA構造を返す"() {
        when:
        def result = controller.appleAppSiteAssociation()

        then:
        result.statusCode == HttpStatus.OK

        def body = result.body
        body.containsKey("applinks")

        def applinks = body.applinks as Map
        def details = applinks.details as List
        details.size() == 1

        def detail = details[0] as Map
        detail.appIDs == ["ABCDE12345.com.hwhub.app"]

        def components = detail.components as List
        components.size() == 3

        and: "ディープリンクパスが含まれること"
        (components[0] as Map)["/"] == "/email-verify"
        (components[1] as Map)["/"] == "/invite/*"
        (components[2] as Map)["/"] == "/password/reset"
    }

    def "assetlinksは200とassetlinks構造を返す"() {
        when:
        def result = controller.assetlinks()

        then:
        result.statusCode == HttpStatus.OK

        def body = result.body as List
        body.size() == 1

        def entry = body[0] as Map
        entry.relation == ["delegate_permission/common.handle_all_urls"]

        def target = entry.target as Map
        target.namespace == "android_app"
        target.package_name == "com.hwhub.app"
        target.sha256_cert_fingerprints == ["AA:BB:CC:DD:EE:FF"]
    }

    def "appleAppSiteAssociationはiosAppIdをプロパティから取得する"() {
        given:
        def customProps = new DeepLinkProperties(
                iosAppId: "ZZZZZ99999.com.hwhub.app",
                androidPackageName: "com.hwhub.app",
                androidSha256CertFingerprint: "XX:YY:ZZ"
        )
        def ctrl = new WellKnownController(customProps)

        when:
        def result = ctrl.appleAppSiteAssociation()

        then:
        def details = ((result.body.applinks as Map).details as List)
        (details[0] as Map).appIDs == ["ZZZZZ99999.com.hwhub.app"]
    }

    def "assetlinksはandroidPackageNameとsha256をプロパティから取得する"() {
        given:
        def customProps = new DeepLinkProperties(
                iosAppId: "X.com.test",
                androidPackageName: "com.test.app",
                androidSha256CertFingerprint: "11:22:33"
        )
        def ctrl = new WellKnownController(customProps)

        when:
        def result = ctrl.assetlinks()

        then:
        def target = ((result.body as List)[0] as Map).target as Map
        target.package_name == "com.test.app"
        target.sha256_cert_fingerprints == ["11:22:33"]
    }
}

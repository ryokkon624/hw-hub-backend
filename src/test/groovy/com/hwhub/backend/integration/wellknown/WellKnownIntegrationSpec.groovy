package com.hwhub.backend.integration.wellknown

import com.hwhub.backend.integration.IntegrationTestBase

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class WellKnownIntegrationSpec extends IntegrationTestBase {

    def "GET /.well-known/apple-app-site-association - 認証なしで200とAASA構造が返ること"() {
        when:
        def result = mockMvc.perform(get("/.well-known/apple-app-site-association"))

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.applinks').exists())
        result.andExpect(jsonPath('$.applinks.details').isArray())
        result.andExpect(jsonPath('$.applinks.details[0].appIDs').isArray())
        result.andExpect(jsonPath('$.applinks.details[0].components').isArray())
        result.andExpect(jsonPath('$.applinks.details[0].components[0]["/"]').value("/email-verify"))
        result.andExpect(jsonPath('$.applinks.details[0].components[1]["/"]').value("/invite/*"))
        result.andExpect(jsonPath('$.applinks.details[0].components[2]["/"]').value("/password/reset"))
    }

    def "GET /.well-known/assetlinks.json - 認証なしで200とassetlinks構造が返ること"() {
        when:
        def result = mockMvc.perform(get("/.well-known/assetlinks.json"))

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$').isArray())
        result.andExpect(jsonPath('$[0].relation').isArray())
        result.andExpect(jsonPath('$[0].relation[0]').value("delegate_permission/common.handle_all_urls"))
        result.andExpect(jsonPath('$[0].target.namespace').value("android_app"))
        result.andExpect(jsonPath('$[0].target.package_name').value("com.hwhub.app"))
        result.andExpect(jsonPath('$[0].target.sha256_cert_fingerprints').isArray())
    }
}

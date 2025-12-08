package com.hwhub.backend.presentation.rest.code

import com.hwhub.backend.application.service.CodeService
import com.hwhub.backend.domain.model.CodeModel
import com.hwhub.backend.presentation.rest.code.dto.CodeListResponse
import spock.lang.Specification

class CodeControllerSpec extends Specification {

    CodeService codeService = Mock()
    CodeController controller = new CodeController(codeService)

    def "listCodes は codeType なしで CodeService.listCodes(null) を呼びレスポンスを返す"() {
        given:
        // ★ 型を Object ではなく CodeModel にする
        def model1 = Stub(CodeModel)
        def model2 = Stub(CodeModel)

        when:
        CodeListResponse response = controller.listCodes(null)

        then:
        1 * codeService.listCodes(null) >> [model1, model2]

        and:
        response.codes.size() == 2
    }

    def "listCodes は codeType 指定時にその値で CodeService.listCodes を呼ぶ"() {
        given:
        String codeType = "001"
        def model = Stub(CodeModel)

        when:
        CodeListResponse response = controller.listCodes(codeType)

        then:
        1 * codeService.listCodes("001") >> [model]

        and:
        response.codes.size() == 1
    }
}

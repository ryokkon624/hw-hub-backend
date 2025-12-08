package com.hwhub.backend.application.service

import com.hwhub.backend.domain.model.CodeModel
import com.hwhub.backend.domain.repository.CodeRepository
import spock.lang.Specification
import spock.lang.Subject

class CodeServiceSpec extends Specification {

    @Subject
    CodeService codeService

    CodeRepository codeRepository = Mock()

    def setup() {
        codeService = new CodeService(codeRepository)
    }

    def "codeType が null や空白の場合は findAll() を呼び出して全件返す"() {
        given: "リポジトリが返すダミーデータ"
        def allCodes = [Mock(CodeModel), Mock(CodeModel)]

        when: "listCodes を呼ぶ（codeType は #caseName）"
        def result = codeService.listCodes(input)

        then: "findAll が 1 回呼ばれ、その戻り値が結果になる"
        1 * codeRepository.findAll() >> allCodes
        0 * codeRepository.findByCodeType(_)
        result == allCodes

        where:
        caseName        | input
        "null の場合"   | null
        "空文字の場合" | ""
        "空白のみ"     | "   "
    }

    def "codeType が非空なら findByCodeType(codeType) を呼び出す"() {
        given:
        def filteredCodes = [Mock(CodeModel)]

        when:
        def result = codeService.listCodes("0001")

        then:
        1 * codeRepository.findByCodeType("0001") >> filteredCodes
        0 * codeRepository.findAll()
        result == filteredCodes
    }
}

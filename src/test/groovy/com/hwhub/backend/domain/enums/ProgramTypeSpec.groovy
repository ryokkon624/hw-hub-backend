package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class ProgramTypeSpec extends Specification {

    def "getCodeは各ProgramTypeに対応するコードを返す"() {
        expect:
        ProgramType.SYSTEM.code       == "SYSTEM"
        ProgramType.ADMIN.code        == "ADMIN"
        ProgramType.ONL_AUTH.code     == "OnlAuth"
        ProgramType.ONL_CODE.code     == "OnlCode"
        ProgramType.ONL_HLDAUTH.code  == "OnlHldAuth"
        ProgramType.ONL_HLDINVI.code  == "OnlHldInvi"
        ProgramType.ONL_HLDMEM.code   == "OnlHldMem"
        ProgramType.ONL_HLD.code      == "OnlHld"
        ProgramType.ONL_HWR.code      == "OnlHwr"
        ProgramType.ONL_HWRTSK.code   == "OnlHwrTsk"
        ProgramType.ONL_SHPATCH.code  == "OnlShpAtch"
        ProgramType.ONL_SHP.code      == "OnlShp"
        ProgramType.ONL_USRICON.code  == "OnlUsrIcon"
        ProgramType.BTC_INV_EXPR.code == "BtcInvExpr"
        ProgramType.BTC_TSK_GEN.code  == "BtcTskGen"
        ProgramType.BTC_TSK_RECL.code == "BtcTskRecl"
        ProgramType.ONL_USR.code      == "OnlUsr"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するProgramType '#expected' を返す"() {
        expect:
        ProgramType.fromCode(code) == expected

        where:
        code          || expected
        "SYSTEM"      || ProgramType.SYSTEM
        "ADMIN"       || ProgramType.ADMIN
        "OnlAuth"     || ProgramType.ONL_AUTH
        "OnlCode"     || ProgramType.ONL_CODE
        "OnlHldAuth"  || ProgramType.ONL_HLDAUTH
        "OnlHldInvi"  || ProgramType.ONL_HLDINVI
        "OnlHldMem"   || ProgramType.ONL_HLDMEM
        "OnlHld"      || ProgramType.ONL_HLD
        "OnlHwr"      || ProgramType.ONL_HWR
        "OnlHwrTsk"   || ProgramType.ONL_HWRTSK
        "OnlShpAtch"  || ProgramType.ONL_SHPATCH
        "OnlShp"      || ProgramType.ONL_SHP
        "OnlUsrIcon"  || ProgramType.ONL_USRICON
        "BtcInvExpr"  || ProgramType.BTC_INV_EXPR
        "BtcTskGen"   || ProgramType.BTC_TSK_GEN
        "BtcTskRecl"  || ProgramType.BTC_TSK_RECL
        "OnlUsr"      || ProgramType.ONL_USR
    }

    def "fromCodeは不正なコードの場合IllegalArgumentExceptionを投げる"() {
        when:
        ProgramType.fromCode("UNKNOWN_CODE")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid ProgramType code: UNKNOWN_CODE"
    }

    def "fromCodeはnullを渡した場合もIllegalArgumentExceptionを投げる"() {
        when:
        ProgramType.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}

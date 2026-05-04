package com.hwhub.backend.domain.enums;

public enum ProgramType implements CodeEnum {
  SYSTEM("SYSTEM"),
  ADMIN("ADMIN"),
  ONL_AUTH("OnlAuth"),
  ONL_CODE("OnlCode"),
  ONL_HLDAUTH("OnlHldAuth"),
  ONL_HLDINVI("OnlHldInvi"),
  ONL_HLDMEM("OnlHldMem"),
  ONL_HLD("OnlHld"),
  ONL_HWR("OnlHwr"),
  ONL_HWRTSK("OnlHwrTsk"),
  ONL_SHPATCH("OnlShpAtch"),
  ONL_SHP("OnlShp"),
  ONL_USRICON("OnlUsrIcon"),
  ONL_USR("OnlUsr"),
  ONL_PWDRST("OnlPwdRst"),
  ONL_AUTH_GOOGLE("OnlAuthGgl"),
  ONL_NTF_QRY("OnlNtfQry"),
  ONL_INQRY("OnlInqry"),
  ONL_USR_ROLE("OnlUsrRole"),
  ONL_ADM_INQ("OnlAdmInq"),
  ONL_ADM_USR("OnlAdmUsr"),
  ONL_ADM_HW_TP("OnlAdmHwTp"),
  BTC_INV_EXPR("BtcInvExpr"),
  BTC_TSK_GEN("BtcTskGen"),
  BTC_TSK_RECL("BtcTskRecl"),
  BTC_HLD_CLEN("BtcHldClen"),
  BTC_NTF_AGGR("BtcNtfAggr"),
  BTC_INQ_AI("BtcInqAI");

  private final String code;

  ProgramType(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static ProgramType fromCode(String code) {
    for (ProgramType v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid ProgramType code: " + code);
  }
}

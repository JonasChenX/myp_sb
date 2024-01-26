package com.jonas.myp_sb.executionUtil.insertTestData;

public class Detail {

    private Integer id;
    private String taxJurisCd;
    private String c11;
    private String orgCd;
    private String invCnt;
    private String ban;
    private String vatLosn;
    private String banNm;
    private String banAddr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaxJurisCd() {
        return taxJurisCd;
    }

    public void setTaxJurisCd(String taxJurisCd) {
        this.taxJurisCd = taxJurisCd;
    }

    public String getC11() {
        return c11;
    }

    public void setC11(String c11) {
        this.c11 = c11;
    }

    public String getOrgCd() {
        return orgCd;
    }

    public void setOrgCd(String orgCd) {
        this.orgCd = orgCd;
    }

    public String getInvCnt() {
        return invCnt;
    }

    public void setInvCnt(String invCnt) {
        this.invCnt = invCnt;
    }

    public String getBan() {
        return ban;
    }

    public void setBan(String ban) {
        this.ban = ban;
    }

    public String getVatLosn() {
        return vatLosn;
    }

    public void setVatLosn(String vatLosn) {
        this.vatLosn = vatLosn;
    }

    public String getBanNm() {
        return banNm;
    }

    public void setBanNm(String banNm) {
        this.banNm = banNm;
    }

    public String getBanAddr() {
        return banAddr;
    }

    public void setBanAddr(String banAddr) {
        this.banAddr = banAddr;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "id=" + id +
                ", taxJurisCd='" + taxJurisCd + '\'' +
                ", c11='" + c11 + '\'' +
                ", orgCd='" + orgCd + '\'' +
                ", invCnt='" + invCnt + '\'' +
                ", ban='" + ban + '\'' +
                ", vatLosn='" + vatLosn + '\'' +
                ", banNm='" + banNm + '\'' +
                ", banAddr='" + banAddr + '\'' +
                '}';
    }
}

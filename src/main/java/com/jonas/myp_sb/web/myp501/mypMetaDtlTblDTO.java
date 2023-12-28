package com.jonas.myp_sb.web.myp501;

public class mypMetaDtlTblDTO {

    private String columnNm;
    private String columnType;
    private String columnDesc;

    public String getColumnNm() {
        return columnNm;
    }

    public void setColumnNm(String columnNm) {
        this.columnNm = columnNm;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnDesc() {
        return columnDesc;
    }

    public void setColumnDesc(String columnDesc) {
        this.columnDesc = columnDesc;
    }

    @Override
    public String toString() {
        return "mypMetaDtlTblDTO{" +
                "columnNm='" + columnNm + '\'' +
                ", columnType='" + columnType + '\'' +
                ", columnDesc='" + columnDesc + '\'' +
                '}';
    }
}

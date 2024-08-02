package com.jonas.myp_sb.web.myp502.dto;

public class ResponseCodeOptionDTO {
    private String productName;
    private String optionCode;
    private String productCode;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOptionCode() {
        return optionCode;
    }

    public void setOptionCode(String optionCode) {
        this.optionCode = optionCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return "ResponseCodeOptionDTO{" +
                "productName='" + productName + '\'' +
                ", optionCode='" + optionCode + '\'' +
                ", productCode='" + productCode + '\'' +
                '}';
    }
}

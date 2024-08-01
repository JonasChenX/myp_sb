package com.jonas.myp_sb.web.myp502.dto;

public class ResponseCodeOptionDTO {
    private String productName;
    private String optionCode;

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

    @Override
    public String toString() {
        return "getCodeOptionDTO{" +
                "productName='" + productName + '\'' +
                ", optionCode='" + optionCode + '\'' +
                '}';
    }
}

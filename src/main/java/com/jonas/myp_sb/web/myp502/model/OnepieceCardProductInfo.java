package com.jonas.myp_sb.web.myp502.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "onepiece_card_product_info")
public class OnepieceCardProductInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "option_code", nullable = false, length = 255)
    private String optionCode;

    @Column(name = "product_code", length = 255)
    private String productCode;

    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @Column(name = "price")
    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OnepieceCardProductInfo{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", optionCode='" + optionCode + '\'' +
                ", productCode='" + productCode + '\'' +
                ", releaseDate=" + releaseDate +
                ", price=" + price +
                '}';
    }
}

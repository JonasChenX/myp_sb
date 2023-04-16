package com.jonas.myp_sb.example.sensitive;

import com.jonas.myp_sb.annotation.Sensitive;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserVo {

    @Sensitive(strategy = SensitiveStrategy.USERNAME)
    private String name;

    @Sensitive(strategy = SensitiveStrategy.PHONE)
    private String phone;

    @Sensitive(strategy = SensitiveStrategy.EMAIL)
    private String email;

    @Sensitive(strategy = SensitiveStrategy.ID_CARD)
    private String idCard;

    @Sensitive(strategy = SensitiveStrategy.ADDRESS)
    private String address;

}

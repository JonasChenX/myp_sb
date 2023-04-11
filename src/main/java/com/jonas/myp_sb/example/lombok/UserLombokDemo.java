package com.jonas.myp_sb.example.lombok;

import lombok.*;

@EqualsAndHashCode //equals hashCode
@ToString //toString
@Data //get set
@NoArgsConstructor //無餐建構子
@AllArgsConstructor //有參建構子
public class UserLombokDemo {
    private Integer age;
    private String name;
}

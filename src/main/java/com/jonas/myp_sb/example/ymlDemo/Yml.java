package com.jonas.myp_sb.example.ymlDemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ConfigurationProperties(prefix = "yml")
@Component
public class Yml {
    private String lastName;
    private Integer age;
    private Boolean isBoss;

    private Date birthday;
    private Map<String, String> map;
    private Map<String, String> map2;
    private List<Dog> list;

    private Dog dog;
    private String[] arr;
    private String[] arr2;

    private Map<String, Dog> dogMap;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Dog {
    private String name;
    private Integer age;
}

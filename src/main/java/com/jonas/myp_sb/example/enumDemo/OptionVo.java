package com.jonas.myp_sb.example.enumDemo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionVo<T> {

    /**
     * 文本描述
     */
    private String label;

    /**
     * 值
     */
    private T value;

}

package edu.sysu.sdcs.coupon.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultVO<T> {
    private Integer code;
    private T data;
    private String msg;
}

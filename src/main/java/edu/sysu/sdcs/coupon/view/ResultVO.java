package edu.sysu.sdcs.coupon.view;

import lombok.Data;

@Data
public class ResultVO<T> {
    private Integer code;
    private T data;
    private String msg;
}

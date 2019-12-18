package edu.sysu.sdcs.coupon.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeckillFailException extends RuntimeException{
    private String msg;
}

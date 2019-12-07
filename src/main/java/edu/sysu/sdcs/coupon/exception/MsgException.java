package edu.sysu.sdcs.coupon.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MsgException extends RuntimeException {
    private String msg;
}

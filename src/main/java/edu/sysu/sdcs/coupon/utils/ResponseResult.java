package edu.sysu.sdcs.coupon.utils;

import edu.sysu.sdcs.coupon.enums.ResultEnum;
import edu.sysu.sdcs.coupon.view.ResultVO;

public class ResponseResult {

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg(ResultEnum.SUCCESS.getMessage());
        return resultVO;
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO error(String msg) {
        return error(ResultEnum.ERROR.getCode(), msg);
    }
    public static ResultVO error(ResultEnum msg) {
        return error(ResultEnum.ERROR.getCode(), msg.getMessage());
    }
}
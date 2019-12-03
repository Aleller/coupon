package edu.sysu.sdcs.web.util;

import edu.sysu.sdcs.web.enums.ResultEnum;

import java.io.Serializable;

/**
 * @author: anan
 * @date: Created in 2019/12/3 10:59
 */
public class Result implements Serializable {
  private static final long serialVersionUID = 3203427701000088853L;

  public static ResultVO success() {
    return success(null);
  }

  public static ResultVO success(Object object) {
    ResultVO resultVO = new ResultVO();
//  Gson G = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//  Gson G = new GsonBuilder().setDateFormat(DateFormat.Long).create();
//    resultVO.setData(new Gson().toJson(object));
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

  public static ResultVO result(ResponseResult result) {
    if(!result.hasMessages() || null != result.getMessage())
      return success();
    return error(result.getCode(), result.getMessage());
  }

}

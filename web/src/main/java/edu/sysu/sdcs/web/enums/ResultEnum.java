package edu.sysu.sdcs.web.enums;

import lombok.Getter;

/**
 * @author anan
 * Created on 2018/8/23.
 */
@Getter
public enum ResultEnum {

  /**
   * 不可预测条件
   */
  SUCCESS(0, "成功"),
  FAILURE(-1,"失败"),
  ERROR(-2,"the program is gg... "),


  /**
   * 数据错误导致
   */
  PARAM_ERROR(1, "参数不正确"),
  DELETE_SECTION(15, "部分删除失败"),
  DUPLICATE_ENTRY(16,"已存在，请重试"),



  /**
   * 登陆情况
   */
  LOGIN_FAIL(95, "登录失败, 登录信息不正确"),
  LOGOUT_SUCCESS(90, "登出成功"),

  ;

  private Integer code;
  private String message;

  ResultEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
}

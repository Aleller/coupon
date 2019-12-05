package edu.sysu.sdcs.consumer.mq.enums;

import lombok.Getter;

/**
 * @author anan
 * Created on 2018/8/23.
 */
@Getter
public enum RedisEnum {



  /**
   * 用来区分对象的。
   * redis 会 存两个，
   *  - user
   *  - ticket_user
   */
  USER(1),
  TICKET_USER(0),

  ;

  private long type;

  RedisEnum(long type) {
    this.type = type;
  }
}

package edu.sysu.sdcs.web.exception;

import java.io.Serializable;

/**
 * @author: anan
 * @date: Created in 2019/11/26 15:55
 */
public class SessionTimeoutException extends RuntimeException implements Serializable {
  private static final long serialVersionUID = 4220819479910245410L;
  public void SessionTimeoutException(){}
}

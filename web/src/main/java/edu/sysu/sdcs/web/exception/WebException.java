package edu.sysu.sdcs.web.exception;

import edu.sysu.sdcs.web.util.Result;
import edu.sysu.sdcs.web.util.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author anan
 * @date: Created in 2019/9/2 9:38
 */
@ControllerAdvice
@Slf4j
public class WebException {//extends OpStackException {

  /**
   * get the ServerResponseException and catch it
   * @param e
   * @return
   */
//  @ExceptionHandler(ServerResponseException.class)
//  @ResponseBody
//  public ResultVO getException(ServerResponseException e) {
//    log.error("ServerResponseException.getMessage=>{},{}","ServerResponseException",e);
//    return Result.errorAgent(StatusEnum.SERVICE_UNAVAILABLE);
//  }

  /**
   * get the JSchException and catch it
   * @param e
   * @return
   */
  @ExceptionHandler(AuthenticationException.class)
  @ResponseBody
  public ResultVO getAuthenticationException(AuthenticationException e) {
    log.error("==> [AuthenticationException]: getMessage ==> {}, {} ","AuthenticationException",e);

    String message = e.getMessage();
    if(message.contains("Authentication failed for token submission") && message.contains("Possible unexpected error?")) {
      String[] ids = message.split("UsernamePasswordToken - ");
      String[] userName = ids[1].split(",");
      log.error("==> [AuthenticationException]: 用户名为 {} ，登录失败; ", userName[0]);
      return Result.error("用户名为 "+userName[0]+" ，登录失败");
    }
    return Result.error(e.getMessage());
  }

  /**
   * get the JSchException and catch it
   * @param e
   * @return
   */
  @ExceptionHandler(UnauthenticatedException.class)
  @ResponseBody
  public ResultVO getUnauthenticatedException(UnauthenticatedException e) {
    log.error("==> [UnauthenticatedException]: getMessage ==> {}, {} ","UnauthenticatedException",e);

    String message = e.getMessage();
    if(message.contains("authorization is denied")) {
      log.error("==> [UnauthenticatedException]: 未授权客户机访问数据 ");
      return Result.error(401,"未授权客户机访问数据");
    }
    return Result.error(e.getMessage());
  }

  /**
   * 处理获取当前登录超时出错问题
   *
   * @param response
   * @param request
   */
  @ExceptionHandler({ SessionTimeoutException.class })
  public ResultVO SessionTimeoutException(HttpServletResponse response, HttpServletRequest request) {
// Redirect
//    try {
//      String path = request.getContextPath();
//      String strBackUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
//        + path + "/";
//      response.sendRedirect(strBackUrl);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
    log.error("==> [SessionTimeoutException]: 登录超时");
    return Result.error("Session Timeout Exception");
  }
}

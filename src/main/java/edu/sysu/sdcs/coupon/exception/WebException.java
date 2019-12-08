package edu.sysu.sdcs.coupon.exception;

import edu.sysu.sdcs.coupon.utils.ResponseResult;
import edu.sysu.sdcs.coupon.view.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
            return ResponseResult.error("用户名为 "+userName[0]+" ，登录失败");
        }
        return ResponseResult.error(e.getMessage());
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
            return ResponseResult.error(401,"未授权客户机访问数据");
        }
        return ResponseResult.error(e.getMessage());
    }

    /**
     * 处理获取当前登录超时出错问题
     *
     * @param e
     */
    @ExceptionHandler(SessionTimeoutException.class)
    @ResponseBody
    public ResultVO getSessionTimeoutException(SessionTimeoutException e) {
        log.error("==> [SessionTimeoutException]: {}登录超时", e);
        return ResponseResult.error("Session Timeout Exception");
    }

    /**
     * 处理获取
     *
     * @param e
     */
    @ExceptionHandler(MsgException.class)
    @ResponseBody
    public ResultVO getMsgException(MsgException e) {
        log.error(e.getMsg());
        return ResponseResult.error(e.getMsg());
    }
}
package edu.sysu.sdcs.coupon.config;

import edu.sysu.sdcs.coupon.core.Handler.LoginFailureHandler;
import edu.sysu.sdcs.coupon.core.Handler.LoginSuccessHandler;
import edu.sysu.sdcs.coupon.core.LoginValidateAuthenticationProvider;
import edu.sysu.sdcs.coupon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    //自定义认证
    @Resource
    private LoginValidateAuthenticationProvider loginValidateAuthenticationProvider;

    //登录成功handler
    @Resource
    private LoginSuccessHandler loginSuccessHandler;

    //登录失败handler
    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Resource
    private UserService userService;

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.httpBasic()
                .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/toLogin")
                    .loginProcessingUrl("/doLogin")
                    .defaultSuccessUrl("/index")
                    .successHandler(loginSuccessHandler)//成功登录处理器
                    .failureHandler(loginFailureHandler)//失败登录处理器
                    .permitAll()//登录成功后有权限访问所有页面
                .and()
                    .rememberMe()//记住我功能
                    .userDetailsService(userService);//设置用户业务层
                    //.tokenRepository(persistentTokenRepository())//设置持久化token
                    //.tokenValiditySeconds(24 * 60 * 60); //记住登录1天(24小时 * 60分钟 * 60秒)

        //关闭csrf跨域攻击防御
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //这里要设置自定义认证
        auth.authenticationProvider(loginValidateAuthenticationProvider);
    }

    /**
     * BCrypt加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

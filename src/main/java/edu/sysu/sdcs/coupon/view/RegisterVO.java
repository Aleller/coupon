package edu.sysu.sdcs.coupon.view;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterVO {
    @JSONField(name="username")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @JSONField(name="password")
    @NotBlank(message = "密码不能为空")
    private String passWord;

    @NotBlank(message = "类型不能为空")
    private String kind;
}

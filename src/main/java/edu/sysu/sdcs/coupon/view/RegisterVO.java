package edu.sysu.sdcs.coupon.view;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterVO {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "类型不能为空")
    private String kind;
}

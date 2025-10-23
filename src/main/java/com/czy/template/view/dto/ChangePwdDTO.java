package com.czy.template.view.dto;

import lombok.Data;

@Data
public class ChangePwdDTO {
    private String password;     // 原密码
    private String newPassword;  // 新密码
}

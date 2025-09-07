package com.czy.template.dto;

import lombok.Data;

@Data
public class changePwdDTO {
    private String password;     // 原密码
    private String newPassword;  // 新密码
}

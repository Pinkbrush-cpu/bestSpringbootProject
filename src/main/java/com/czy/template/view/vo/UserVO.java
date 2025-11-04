package com.czy.template.view.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {
    private Long id;
    private String username;
    private String realname;
    private String phone;
    private String email;
    private Integer identity;
}

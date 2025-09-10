package com.czy.template.vo;

import lombok.Data;

import java.io.Serializable;

@Data

public class UserVO implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private Integer identity;
}

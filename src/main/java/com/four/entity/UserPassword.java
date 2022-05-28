package com.four.entity;

import lombok.Data;

@Data
public class UserPassword {
    private String username;
    private String oldPwd;
    private String newPwd;
}

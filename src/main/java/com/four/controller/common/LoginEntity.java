package com.four.controller.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginEntity {
    private String userName;
    private String password;
    private String code;
    private String pCode;
}

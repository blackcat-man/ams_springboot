package com.four.service;

import com.four.entity.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;

public interface ILoginService {

    SecurityUser loadByUsername(String username);
}

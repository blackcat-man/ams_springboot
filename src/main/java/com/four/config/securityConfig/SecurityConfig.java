package com.four.config.securityConfig;


import com.four.config.securityConfig.UserAuthenticationEntryPoint;
import com.four.config.securityConfig.UserLoginFailureHandler;
import com.four.config.securityConfig.UserLoginSuccessHandler;
import com.four.config.securityConfig.UserLogoutSuccessHandler;
import com.four.filter.UriFilter;
import com.four.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserLoginFailureHandler userLoginFailureHandler;

    @Autowired
    private UserLoginSuccessHandler userLoginSuccessHandler;

    @Autowired
    private UserAuthenticationEntryPoint userAuthenticationEntryPoint;

    @Autowired
    private UserLogoutSuccessHandler userLogoutSuccessHandler;

    @Autowired
    private UserAccessDeniedHandler userAccessDeniedHandler;

    @Autowired
    private UriFilter uriFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭crsf和frameOptions
        http.csrf().disable()
                // 不通过session获取SecurityContext上下文对象
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();

        http.cors();//开启跨域请求

        http.authorizeRequests()
                .antMatchers("/login",
                        "/createImg",
                        "/doc.html",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v2/**").permitAll() // 放行login，以及swagger页面
                .anyRequest().authenticated()
                .and().formLogin()
                .successHandler(userLoginSuccessHandler) // 自定义认证成功处理器
                .failureHandler(userLoginFailureHandler) // 自定义认证失败处理器
                .and()
                // 自定义匿名登录器
                .exceptionHandling()
                .authenticationEntryPoint(userAuthenticationEntryPoint) // 未认证异常处理器
                .accessDeniedHandler(userAccessDeniedHandler) // 访问未授权资源时异常处理器
                .and()
                .logout()
                .permitAll()// 放行logout
                .logoutSuccessHandler(userLogoutSuccessHandler) // 自定义注销处理器
                .deleteCookies("JSESSIONID")
                .and()
                .addFilterBefore(uriFilter, LogoutFilter.class);
    }


    /**
     * 密码加密
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份验证组件
     *
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


}

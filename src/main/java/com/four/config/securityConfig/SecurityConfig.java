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
        // ??????crsf???frameOptions
        http.csrf().disable()
                // ?????????session??????SecurityContext???????????????
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();

        http.cors();//??????????????????

        http.authorizeRequests()
                .antMatchers("/login",
                        "/createImg",
                        "/doc.html",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v2/**").permitAll() // ??????login?????????swagger??????
                .anyRequest().authenticated()
                .and().formLogin()
                .successHandler(userLoginSuccessHandler) // ??????????????????????????????
                .failureHandler(userLoginFailureHandler) // ??????????????????????????????
                .and()
                // ????????????????????????
                .exceptionHandling()
                .authenticationEntryPoint(userAuthenticationEntryPoint) // ????????????????????????
                .accessDeniedHandler(userAccessDeniedHandler) // ???????????????????????????????????????
                .and()
                .logout()
                .permitAll()// ??????logout
                .logoutSuccessHandler(userLogoutSuccessHandler) // ????????????????????????
                .deleteCookies("JSESSIONID")
                .and()
                .addFilterBefore(uriFilter, LogoutFilter.class);
    }


    /**
     * ????????????
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * ??????????????????
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

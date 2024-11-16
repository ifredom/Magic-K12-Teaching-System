package com.magic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



public class SecurityConfig {}

//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        super.configure(http);
//
//        // 禁用CSRF
//        http.csrf().disable();
//
//        // 请求url配置
//        http.authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/login.html").permitAll()
//                .anyRequest().authenticated();
//
//        // 登录配置
//        http.formLogin()
//                .loginProcessingUrl("/login")
//                .loginPage("/login.html");
//
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        // 这里可以自定义用户验证逻辑，这里简单返回一个固定账号密码的用户
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user1").password("123456").authorities("xx1").build());
//        manager.createUser(User.withUsername("user2").password("654321").authorities("xx2").build());
//        return manager;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // 这里可以自定义密码加密算法
//        return  NoOpPasswordEncoder.getInstance();
//    }
//}
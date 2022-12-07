package com.ashok.shopInventory.web.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;


@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter  {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers( "/js/**", "/scss/**",
                        "/css/**",
                        "/vendor/**",
                        "/img/**",
                        "/webjars/**").permitAll()
                .antMatchers("/","/user*//**//**").permitAll()
                .antMatchers("/Eedu**","/Checkout**","/Charge**").hasAnyRole("USER","ADMIN")
                .antMatchers("/Admin**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/",true)
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers( "/js/**", "/scss/**",
//                        "/css/**",
//                        "/vendor/**",
//                        "/img/**",
//                        "/webjars/**").permitAll()
//                .antMatchers("/","/user*//**//**").permitAll()
//                .antMatchers("/Eedu**","/Checkout**","/Charge**").hasAnyRole("USER","ADMIN")
//                .antMatchers("/Admin**").hasAnyRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .defaultSuccessUrl("/",true)
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }

//    protected void configure1(HttpSecurity http) throws Exception {
//        http
//                .csrf()
//                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/login"))
//                .and()
//                .authorizeRequests()
//                .antMatchers("/home").hasRole("USER")
//                .and()
//                .formLogin()
//                .defaultSuccessUrl("/home")
//                .loginPage("/login")
//                .and()
//                .logout()
//                .permitAll();
//    }
//    protected void configure2(HttpSecurity http) throws Exception {
//        http
//                .csrf()
//                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/login"))
//                .and()
//                .authorizeRequests()
//                .antMatchers("/home").hasRole("admin")
//                .and()
//                .formLogin()
//                .defaultSuccessUrl("/")
//                .loginPage("/login")
//                .and()
//                .logout()
//                .permitAll();
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

}

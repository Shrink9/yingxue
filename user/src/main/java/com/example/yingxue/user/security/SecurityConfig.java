package com.example.yingxue.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.UrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.UUID;

@Configuration
//开启方法上注释权限控制
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    //未登录处理器
    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;
    //无权限处理器
    @Autowired
    AccessDeniedHandler accessDeniedHandler;
    @Autowired
    UserDetailsService userDetailsService;
    //登录成功处理器
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    //登录失败处理器
    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;
    //退出成功处理器
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    //POST方式以json传递账号和密码的过滤器
    @Bean
    JsonLoginFilter jsonLoginFilter(){
        JsonLoginFilter jsonLoginFilter=new JsonLoginFilter();
        try{
            jsonLoginFilter.setAuthenticationManager(authenticationManagerBean());
            //以下六项只在使用json认证时有效
            jsonLoginFilter.setFilterProcessesUrl("/tokens");
            jsonLoginFilter.setUsernameParameter("phone");
            jsonLoginFilter.setPasswordParameter("captcha");
            jsonLoginFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
            jsonLoginFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return jsonLoginFilter;
    }
    //配置userDetailsService
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }
    //默认情况下AuthenticationManager是没办法从工厂中获取的,需要这样来声明它是bean.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    //刚被注入时就会调这个方法进行预设
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
            .antMatchers("/captchas","/user/{userId}","/user/video/{videoId}","/get-is-dislike-by-user-id-and-video" +
                    "-id","/get-is-favorite-by-user-id-and-video-id","/get-is-following-by-two-user-id","/get-is-like" +
                    "-by-user-id-and-video-id","/get-like-count-by-video-id","/get-play-count-by-video-id","/user/played/{id}","/get-comments-by-video-id")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            //把默认的表单登录切换为json格式登录
            .addFilterAt(jsonLoginFilter(),UsernamePasswordAuthenticationFilter.class)
            //采用表单认证(如果切换为json了的话,底层实现已经被自定义的过滤器换掉了.)
            .formLogin()
            .and()
            //对于退出配置,不管是表单登录还是json登录都有效.
            .logout()
            //以下这项设置是可选的
            //自定义的退出url以及请求方式
            .logoutRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/tokens","DELETE")))
            //成功后的处理器，相关介绍在下面。
            .logoutSuccessHandler(logoutSuccessHandler)
            .and()
            .csrf()
            .disable()
            .exceptionHandling()
            //未登录处理器
            .authenticationEntryPoint(authenticationEntryPoint)
            //无权限处理器
            .accessDeniedHandler(accessDeniedHandler);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}

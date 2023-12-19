package com.myblog.myblog.config;

import com.myblog.myblog.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //Enables method-level security with pre and post annotations such as @PreAuthorize, @PostAuthorize, @PreFilter, and @PostFilter
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //@EnableWebSecurity tells Spring to use this class to configure web-based security.
    //WebSecurityConfigurerAdapter provides default security configurations that can be overridden.
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Bean
    PasswordEncoder getEncodedPassword(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected  void configure(HttpSecurity http) throws Exception{

        //chaining of statement or security filter chain
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

    }


      //******************************** IN MEMORY AUTHENTICATION ******************************

//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        UserDetails user = User.builder().username("Pankaj").password(getEncodedPassword().encode("Testing")).roles("USER").build();
//        UserDetails admin = User.builder().username("Admin").password(getEncodedPassword().encode("Dilshan")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }




    //*********************SPRING SECURITY WITH DATABASE*******************************
    //configure()-->this method configures how Spring Security performs authentication.
    //It uses the **customUserDetailsService** to load user details and
    // the passwordEncoder() method to handle password encoding.

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws
            Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getEncodedPassword());
    }

    @Override
    @Bean // Expose AuthenticationManager bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

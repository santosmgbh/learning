package br.com.almir.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
		

	@Autowired
	private DataSource dataSource;
	
	@Autowired
    private CustomAuthenticationProvider authProvider;

	
//    @Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {		 
//    	auth.authenticationProvider(authProvider);
//	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home", "/register", "/app/**", "/js/**", "/css/**", "/images/**", "/fonts/**", "/sass/**", "/external/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")                
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	System.out.println("configureGlobal");
    	auth.
		jdbcAuthentication()
			.usersByUsernameQuery("SELECT LOGIN,SENHA FROM USER WHERE LOGIN=?")					
			.dataSource(dataSource)
			.passwordEncoder(new BCryptPasswordEncoder ());    	   
    }
}
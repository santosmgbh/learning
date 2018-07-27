package br.com.almir.projetos.hm.security;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource restDataSource;
	
	
	@Bean
	PasswordEncoder getEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/static/**").permitAll()
			.antMatchers("/dist/**").permitAll()
			.antMatchers("/home").permitAll()
			.antMatchers("/h2/**").permitAll()			
			.antMatchers("/users/*").permitAll()
			.antMatchers(HttpMethod.POST, "/login").permitAll()
			.anyRequest().authenticated()
			.and()
						.addFilterBefore(new SimpleCORSFilter(),
								SimpleCORSFilter.class)
			// filtra requisições de login
			.addFilterBefore(new JWTLoginFilter("/login", authenticationManagerBean()),
	                UsernamePasswordAuthenticationFilter.class)
			
			// filtra outras requisições para verificar a presença do JWT no header
			.addFilterBefore(new JWTAuthenticationFilter(),
	                UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {		
		auth.jdbcAuthentication().dataSource(restDataSource)
		.usersByUsernameQuery("SELECT USERNAME,PASSWORD, ACTIVE as ENABLED FROM USER WHERE USERNAME=?")		
		.authoritiesByUsernameQuery("SELECT U.USERNAME, U.NAME FROM USER U WHERE U.USERNAME=?")
		.passwordEncoder(getEncoder());
    	
	}
	
}

package com.example.demo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().regexMatchers("/xds/service/*");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/js/**", "/common/**", "/page/**", "/css/**", "/medisys/**", "/images/**", "/img/**",
						"/h2-console/**", "/webjars/**")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and()
				.logout().permitAll();

		 http.authorizeRequests().                                                                                  
	        antMatchers("/service/**").hasRole("USER").and().httpBasic().and().
	        csrf().disable();
		 
		// .antMatchers("/setup/**").
		//
		// hasRole("USER").anyRequest().authenticated().and().formLogin()
		// .loginPage("/login").permitAll().and().logout().invalidateHttpSession(true).clearAuthentication(true)
		// .logoutRequestMatcher(new
		// AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
		// .permitAll();

		// .authorizeRequests()
		// .antMatchers("/", "/home").permitAll()
		// .anyRequest().authenticated()
		// .and()
		// .formLogin()
		// .loginPage("/login")
		// .permitAll()
		// .and()
		// .logout()
		// .permitAll();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder().username("user").password("123").roles("USER").build();

		return new InMemoryUserDetailsManager(user);
	}
}
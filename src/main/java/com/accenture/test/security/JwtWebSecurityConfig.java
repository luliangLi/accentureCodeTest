package com.accenture.test.security;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.accenture.test.domain.AccentureUser;
import com.accenture.test.domain.Permission;
import com.accenture.test.repository.UserH2Repository;

@Configuration
@Order(SecurityProperties.IGNORED_ORDER)
public class JwtWebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private UserH2Repository userRepo;
	
	@Autowired
	private Environment env;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    public JwtWebSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/app/user/v1/query").permitAll()
                .antMatchers("/app/user/v1/delete").permitAll()
                .antMatchers("/web/user/query").permitAll()
                .antMatchers("/web/user/delete").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtLoginFilter(authenticationManager()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), userRepo));
        
        insertAdminUser();
    }
    
    private void insertAdminUser() {
        AccentureUser admin = new AccentureUser();
        admin.setPassword(bCryptPasswordEncoder.encode(env.getProperty("accenture.user.ps")));
        admin.setPermission(Permission.ADMIN.ordinal());
        admin.setUsername(env.getProperty("accenture.user.admin"));
        userRepo.save(admin);
        
        AccentureUser user = new AccentureUser();
        user.setPassword(bCryptPasswordEncoder.encode(env.getProperty("accenture.user.ps")));
        user.setPermission(Permission.USER.ordinal());
        user.setUsername(env.getProperty("accenture.user.user"));
        userRepo.save(user);
        
        List<AccentureUser> users = userRepo.findAll();
        for (AccentureUser curr : users) {
        	if (curr.getPermission()==(Permission.ADMIN.ordinal())) {
        		Logger.getLogger(this.getClass()).info("The admin is " + curr);
        	} else {
        		Logger.getLogger(this.getClass()).info("The user is " + curr);
        	}
        }
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl)
        .passwordEncoder(bCryptPasswordEncoder)
        ;
    }
}

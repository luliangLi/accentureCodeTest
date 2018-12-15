package com.accenture.test.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.accenture.test.domain.AccentureUser;
import com.accenture.test.domain.Permission;
import com.accenture.test.repository.UserH2Repository;
import com.accenture.test.utils.Utilies;

import io.jsonwebtoken.Jwts;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

	private UserH2Repository userRepo;

	public JwtAuthenticationFilter(AuthenticationManager authManager, UserH2Repository userRepo) {
		super(authManager);
		this.userRepo = userRepo;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String user = Utilies.getUserFromReq(request);
		
		if (user != null) {
			AccentureUser userEntity = userRepo.findById(Long.valueOf(user)).get();
			Integer p = userEntity.getPermission();
			if (p == Permission.ADMIN.ordinal()) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
		}
		return null;
	}
}

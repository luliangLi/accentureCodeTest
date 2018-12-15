package com.accenture.test.utils;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Jwts;

public class Utilies {

	public static String getUserFromReq(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token != null) {
			// parse the token.
			String user = Jwts.parser().setSigningKey("MyJwtSecret").parseClaimsJws(token.replace("Bearer ", ""))
					.getBody().getSubject();

			if (user != null) {
				return user;
			}
			return null;
		}
		return null;
	}
}

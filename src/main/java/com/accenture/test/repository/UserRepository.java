package com.accenture.test.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.accenture.test.domain.Permission;
import com.accenture.test.domain.AccentureUser;
import com.accenture.test.domain.UserProfile;

@Repository
public class UserRepository {

//	private static final UserRepository us = new UserRepository();
	private static final Map<Long, AccentureUser> users = new ConcurrentHashMap<>();
	
	public UserRepository() {
		AccentureUser admin = new AccentureUser();
		admin.setId(0l);
		admin.setPermission(Permission.ADMIN.ordinal());
		
		UserProfile up = new UserProfile();
		up.setFirstName("admin");
		admin.setUp(up);
		
		users.put(admin.getId(), admin);
	}
	
//	public static UserRepository getInstance() {
//		return us;
//	}
	
	public AccentureUser addUser(AccentureUser user) {
		if (user.getId() == null) {
			user.setId(generateNewId());
		}
		
		if (!users.containsKey(user.getId())) {
			users.putIfAbsent(user.getId(), user);
		}
		
		return user;
	}
	
	public AccentureUser readUser(Long id) {
		return users.getOrDefault(id, null);
	}
	
	public boolean delUser(Long id) {
		if (users.containsKey(id)) {
			users.remove(id);
			return true;
		} else {
			return false;
		}
	}
	
	public AccentureUser updateUser(Long id, UserProfile user) {
		if (!users.containsKey(id)) {
			return null;
		} 
		
		if (user != null) {
			users.get(id).setUp(user);	
		}
		
		return users.get(id);
	}
	
	public AccentureUser changePermission(Long id, Permission p) {
		if (!users.containsKey(id)) {
			return null;
		} 
		
		users.get(id).setPermission(p.ordinal());
		
		return users.get(id);
	}
	
	private Long generateNewId() {
		return System.currentTimeMillis();
	}
}

package com.accenture.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.accenture.test.domain.AccentureUser;
import com.accenture.test.domain.Permission;
import com.accenture.test.domain.UserProfile;
import com.accenture.test.repository.UserH2Repository;
import com.accenture.test.vo.ResultVo;

@Service
public class UserServices {
//
//	@Autowired
//	private UserRepository userRepository;
//	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserH2Repository userH2Repository;
	
	@Autowired
	private Environment env;
	
    public ResultVo<AccentureUser> getUserProfile(String id) { 
    	ResultVo<AccentureUser> rlt = new ResultVo<>();
    	
    	try {
    		AccentureUser u = userH2Repository.findById(Long.valueOf(id)).get();
            rlt.setOjb(u);
    	} catch (Exception ex) {
    		rlt.setSuccess(false);
    		rlt.addMessage(ex.getMessage());
    	}
        
        return rlt;
    }
	
    public ResultVo<AccentureUser> deleteUserProfile(String id) {
		ResultVo<AccentureUser> rlt = new ResultVo<>();
		rlt.setOjb(null);
		
		userH2Repository.deleteById(Long.valueOf(id));
        
        return rlt;
    } 
    
    public ResultVo<AccentureUser> createUserProfile(boolean isAdmin, UserProfile userProfile) {
		AccentureUser user = new AccentureUser();
		user.setUp(userProfile);
		user.setPermission(isAdmin ? Permission.ADMIN.ordinal() : Permission.USER.ordinal());
		user.setPassword(bCryptPasswordEncoder.encode(env.getProperty("accenture.user.ps")));
		
		AccentureUser u = userH2Repository.save(user);
        
        ResultVo<AccentureUser> rlt = new ResultVo<>();
        rlt.setOjb(u);
        
        return rlt;
    }
	
    public ResultVo<AccentureUser> updateUserProfile(UserProfile userProfile, String id) { 
        ResultVo<AccentureUser> rlt = new ResultVo<>();

        try {
        	AccentureUser user = userH2Repository.findById(Long.valueOf(id)).get();
        	
        	user.setUp(userProfile);
        	user = userH2Repository.saveAndFlush(user);
        	rlt.setOjb(user);
        } catch (Exception ex) {
        	rlt.addMessage("User " + id + " does not exist");
            rlt.setSuccess(false);
        }
        
        return rlt;
    } 
	
	public ResultVo<AccentureUser> changePermission(boolean isAdmin, String id) { 
		ResultVo<AccentureUser> rlt = new ResultVo<>();
		
        try {
        	AccentureUser user = userH2Repository.findById(Long.valueOf(id)).get();
        	
        	user.setPermission(isAdmin? Permission.ADMIN.ordinal() : Permission.USER.ordinal());
			user = userH2Repository.saveAndFlush(user);
			rlt.setOjb(user);
        } catch (Exception ex) {
        	rlt.addMessage("User " + id + " does not exist");
            rlt.setSuccess(false);
        }
		
        return rlt;
    } 
}

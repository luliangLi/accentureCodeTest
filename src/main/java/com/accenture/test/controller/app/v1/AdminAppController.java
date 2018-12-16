package com.accenture.test.controller.app.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.test.domain.AccentureUser;
import com.accenture.test.domain.UserProfile;
import com.accenture.test.services.UserServices;
import com.accenture.test.vo.ResultVo;

import io.swagger.annotations.ApiOperation;

@RestController 
@RequestMapping(value="/app/admin/v1")
public class AdminAppController {

	@Autowired
	private UserServices userServices;

	@ApiOperation(value="Create User", notes="Only admin role can access the api to create a new user")
	@RequestMapping(value="/create", method = RequestMethod.POST, consumes = "application/json") 
    public ResultVo<AccentureUser> createUserProfile(
    		@RequestParam(value = "isAdmin", required = false, defaultValue = "false") boolean isAdmin, 
    		@RequestBody UserProfile userProfile) {
        ResultVo<AccentureUser> rlt = userServices.createUserProfile(isAdmin, userProfile);
        
        return rlt;
    }
	
	@ApiOperation(value="Update User", notes="Only admin role can access the api to update user profile")
	@RequestMapping(value="/update", method = RequestMethod.POST, consumes = "application/json") 
    public ResultVo<AccentureUser> updateUserProfile(
    		@RequestBody(required=false) UserProfile userProfile,
    		@RequestParam(value = "id") String id) { 
        ResultVo<AccentureUser> rlt = userServices.updateUserProfile(userProfile, id);
        
        return rlt;
    } 
	
	@ApiOperation(value="Grant role to user", notes="Only admin role can access the api to grant different role to the user")
	@RequestMapping(value="/grant", method=RequestMethod.GET) 
	public ResultVo<AccentureUser> changePermission(
    		@RequestParam(value = "isAdmin") boolean isAdmin,
    		@RequestParam(value = "id") String id) { 
        ResultVo<AccentureUser> rlt = userServices.changePermission(isAdmin, id);
        
        return rlt;
    } 
	
	@ApiOperation(value="Query User", notes="Only admin role can access the api to query specific user information")
	@RequestMapping(value="/query/{id}", method=RequestMethod.GET) 
    public ResultVo<AccentureUser> getUserProfile(@PathVariable String id) { 
        ResultVo<AccentureUser> rlt = userServices.getUserProfile(id);
        
        return rlt;
    }
	
	@ApiOperation(value="Delete User", notes="Only admin role can access the api to delete the specific user")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET) 
    public ResultVo<AccentureUser> deleteUserProfile(@PathVariable String id) { 
        ResultVo<AccentureUser> rlt = userServices.deleteUserProfile(id);
        
        return rlt;
    } 
}


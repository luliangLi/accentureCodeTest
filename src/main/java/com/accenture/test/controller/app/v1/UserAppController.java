package com.accenture.test.controller.app.v1;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.test.domain.AccentureUser;
import com.accenture.test.services.UserServices;
import com.accenture.test.utils.Utilies;
import com.accenture.test.vo.ResultVo;

import io.swagger.annotations.ApiOperation;

@RestController 
@RequestMapping(value="/app/user/v1")
public class UserAppController {

	@Autowired
	private UserServices userServices;
	
	@ApiOperation(value="Query User", notes="Any login user can access the api to query him/herself info")
	@RequestMapping(value="/query", method=RequestMethod.GET)
    public ResultVo<AccentureUser> getUserProfile(HttpServletRequest request) {
		String id = Utilies.getUserFromReq(request);
        ResultVo<AccentureUser> rlt = userServices.getUserProfile(id);
        
        return rlt;
    }
	
	@ApiOperation(value="Query User", notes="Any login user can access the api to delete him/herself account")
	@RequestMapping(value="/delete", method=RequestMethod.GET) 
    public ResultVo<AccentureUser> deleteUserProfile(HttpServletRequest request) { 
		String id = Utilies.getUserFromReq(request);
		ResultVo<AccentureUser> rlt = userServices.deleteUserProfile(id);
        
        return rlt;
    } 
}

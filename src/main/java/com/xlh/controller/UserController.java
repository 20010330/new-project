package com.xlh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xlh.model.User;
import com.xlh.service.ITestService;
import com.xlh.service.IUserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangzhichao on 2018-07-02.
 */
@Controller
@RequestMapping("/userController")
public class UserController{
    //日志记录
    private static final Logger logger = Logger.getLogger(UserController.class);
    
    @Autowired
    private IUserService userService;

    @Autowired
    private ITestService testService;

    @RequestMapping("/userList")
    @ResponseBody
    public String userList(Integer page,Integer rows,User user){

		Map<String,Object> map = new HashMap<String,Object>();


		 map.put("total", userService.userListPage(user).size());
    	 map.put("rows", userService.userListRows(page,rows,user));

        return JSON.toJSONString(map);
    }
    
    @RequestMapping("addUser")
    @ResponseBody
    public void addUser(User user){

	   	 JSONObject json = new JSONObject();
	   	 
	   	 try {
	   		 if(user.getUid()==null){
	   			 
	   			 userService.addUser(user); 
	   			 
	   			json.put("success", false);
	   			 
	   			json.put("msg", "添加成功");
	   			 
	   		 }
		} catch (Exception e) {

			e.printStackTrace();
			
			json.put("success", false);
   			 
			json.put("msg", "执行失败");
		}
	   	 
    }
    
    @RequestMapping("updateUser")
    @ResponseBody
    public void updateUser(User user){
	   	 
	   	 JSONObject josn = new JSONObject();
	   	 
	   	 try {
	   		testService.updateUser(user); 
			 
	   		josn.put("success", true);
			 
	   		josn.put("msg", "编辑成功");
	   			 
		} catch (Exception e) {

			e.printStackTrace();
			
			josn.put("success", false);
   			 
			josn.put("msg", "执行失败");
		}
	   	 
	   	
    }
    
    @RequestMapping("delUser")
    @ResponseBody
    public void delUser(String ids){
    	
	   	testService.delUser(ids);
    }
    
    @RequestMapping("findUserById")
    private ModelAndView findUserById(Integer id){
    	
    	ModelAndView mav = new ModelAndView("update");
    	
    	User user = testService.findUserById(id);
    	
    	mav.addObject("user", user);
    	
    	return mav;
    }
    
}

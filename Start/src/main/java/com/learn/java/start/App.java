package com.learn.java.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zlx.user.dal.mapper.CourseDOMapper;
import com.zlx.user.dal.model.CourseDO;
import com.zlx.user.dal.model.CourseDOExample;
/**
 * Hello world!
 *  
 */
@RestController
@SpringBootApplication
@ComponentScan(basePackages= {"com.learn.java.config"})
public class App {
   
	@Autowired
	private CourseDOMapper  courseDOMapper;
	
	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}
	
	@RequestMapping("/insertCourse")
	String inserCourse() {
		
		CourseDO courseDO = new CourseDO();
		courseDO.setCourseName("java");
		courseDO.setUserId(100);
		
		return courseDOMapper.insert(courseDO) + "";
	}
	
	@RequestMapping("/selectCourse")
	String selectCourse() {
		
		CourseDOExample courseDOExample = new CourseDOExample();
		courseDOExample.createCriteria().andUserIdEqualTo(100);
		
		return JSON.toJSONString(courseDOMapper.selectByExample(courseDOExample));
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}

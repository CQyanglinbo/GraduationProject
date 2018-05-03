package com.ylb.project.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ylb.project.dao.UserRepository;
import com.ylb.project.model.Role;
import com.ylb.project.model.User;

import ch.qos.logback.classic.net.SyslogAppender;

@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		System.out.println("---------------");
		System.out.println("进入认证");
		System.out.println("用户名："+userName);
		User user=userRepository.findByUserName(userName);
//		System.out.println(new BCryptPasswordEncoder().encode("123456") );
//		System.out.println("密码1："+user.getPassword().matches("123654"));
		//用户不存在
		if(user==null){
			throw new BadCredentialsException("AccountNotExist");
		}
		//账户冻结
		if(!user.isStatus()){
			throw new BadCredentialsException("Account is disabled");
		}
		//没有角色
		if(user.getRole()==null){
			throw new BadCredentialsException("AccountHasNotAnyRole");
		}
		Role roles=user.getRole();
		//获取用户授权信息
		List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
		if(roles!=null){
			//加入角色授权
			grantedAuthorities.add(new SimpleGrantedAuthority(roles.getRoleName().trim()));
		}
		//设置用户认证信息
		user.setGrantedAuthority(grantedAuthorities.toArray(new GrantedAuthority[grantedAuthorities.size()]));
		System.out.println("---------------");
		System.out.println("结束认证");
		return user;
	}

}

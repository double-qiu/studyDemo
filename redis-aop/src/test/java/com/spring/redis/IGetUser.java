package com.spring.redis;

import com.spring.redis.vo.UserVO;

public interface IGetUser {

	public UserVO get(String uid);
	
	public UserVO add(String uid,UserVO vo);
	
	public boolean delete(String uid);
	
	public UserVO update(String uid,UserVO vo);
}

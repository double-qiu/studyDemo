package com.spring.redis.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.spring.redis.IGetUser;
import com.spring.redis.annotation.DeleteThroughAssignCache;
import com.spring.redis.annotation.ParameterValueKeyProvider;
import com.spring.redis.annotation.ReadCacheType;
import com.spring.redis.annotation.ReadThroughAssignCache;
import com.spring.redis.annotation.UpdateThroughAssignCache;
import com.spring.redis.vo.UserVO;

@Service("getUserServer")
public class GetUserImpl  implements IGetUser{

	public static Map<String, UserVO> init = null ;
	
	@ReadThroughAssignCache(namespace="GETUSER",cacheType = ReadCacheType.String,valueclass =UserVO.class,expireTime=60)
	public UserVO get(@ParameterValueKeyProvider String uid) {
		System.out.println("数据库查询被执行.....");
		return init.get(uid);
	}

	@UpdateThroughAssignCache(namespace="GETUSER",cacheType = ReadCacheType.String,valueclass =UserVO.class,expireTime=0)
	public UserVO add(@ParameterValueKeyProvider String uid, UserVO value) {
		System.out.println("数据库添加被执行.....");
		value.setUid(uid);
		init.put(uid, value);
		return value;
	}

	@DeleteThroughAssignCache(namespace="GETUSER",cacheType = ReadCacheType.String)
	public boolean delete(@ParameterValueKeyProvider String uid) {
		System.out.println("数据库删除被执行.....");
		init.remove(uid);
		return true;
	}

	@UpdateThroughAssignCache(namespace="GETUSER",cacheType = ReadCacheType.String,valueclass =UserVO.class)
	public UserVO update(@ParameterValueKeyProvider String uid, UserVO vo) {
		System.out.println("数据库更新被执行.....");
		init.put(uid, vo);
		return vo;
	}

}

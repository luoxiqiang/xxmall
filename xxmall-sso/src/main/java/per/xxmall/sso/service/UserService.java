package per.xxmall.sso.service;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import per.xxmall.common.service.RedisService;
import per.xxmall.sso.mapper.UserMapper;
import per.xxmall.sso.pojo.User;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public static final String TOKEN_NAME="TOKEN_";
	
	@Autowired
	private RedisService redisService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public Boolean check(String param, Integer type) {
		if(type<1||type>3){
			return null;
		}
		User user = new User();
		switch(type){
			case 1:user.setUsername(param);
				break;
			case 2:user.setPhone(param);
				break;
			case 3:user.setEmail(param);
		}
		return userMapper.selectOne(user) == null;
	}

	public Boolean register(User user) {
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		user.setId(null);
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		return userMapper.insert(user) == 1;
		
	}

	public String doLogin(String username, String password) throws Exception {
		User record = new User();
		record.setUsername(username);
		User user = userMapper.selectOne(record);
		if(user == null) return null;
		if(StringUtils.equals(user.getPassword(), DigestUtils.md5Hex(password))) {
			//生成一个token
			String token = TOKEN_NAME+DigestUtils.md5Hex(System.currentTimeMillis()+username);
			//将token写入redis
			redisService.set(token, MAPPER.writeValueAsString(user), 30*60);
			return token;
		}
		return null;
	}

	public User checkLogin(String token) {
		String user = redisService.get(token);
		if(StringUtils.isEmpty(user)) return null;
		try {
			//重新设置缓存时间为30min
			redisService.expire(token, 30*60);
			return MAPPER.readValue(user, User.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

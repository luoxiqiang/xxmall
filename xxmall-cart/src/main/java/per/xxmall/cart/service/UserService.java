package per.xxmall.cart.service;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import per.xxmall.cart.bean.User;
import per.xxmall.common.service.RedisService;

	
@Service
public class UserService {

	@Autowired
	private RedisService redisService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public User queryUser(String token) {
		try {
			String user = redisService.get(token);
			if(StringUtils.isNotEmpty(user)) {
				return MAPPER.readValue(user, User.class);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}

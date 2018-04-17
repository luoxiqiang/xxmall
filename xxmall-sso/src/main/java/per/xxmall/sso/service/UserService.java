package per.xxmall.sso.service;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.xxmall.sso.mapper.UserMapper;
import per.xxmall.sso.pojo.User;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

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
}

package per.xxmall.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.xxmall.sso.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
}

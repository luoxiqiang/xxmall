package per.xxmall.web.bean;

import per.xxmall.web.pojo.User;

public class UserThreadLocal {
	
	private static final ThreadLocal<User> THREADLOCAL = new ThreadLocal<>();
	
	public static void set(User user) {
		THREADLOCAL.set(user);
	}
	
	public static User get() {
		return THREADLOCAL.get();
	}
}

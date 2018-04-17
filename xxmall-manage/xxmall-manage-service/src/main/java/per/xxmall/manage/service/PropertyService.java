package per.xxmall.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

	@Value("${IMAGE_PATH}")
	public String IMAGE_PATH;
	
	@Value("${IMAGE_URL}")
	public String IMAGE_URL;
}

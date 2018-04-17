package per.xxmall.web.pojo;

import org.apache.commons.lang3.StringUtils;

public class Item extends per.xxmall.manage.pojo.Item{
	
	public String[] getimages() {
		return StringUtils.split(getImage(),",");
	}
}

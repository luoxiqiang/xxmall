package per.xxmall.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import per.xxmall.common.bean.EasyUIResult;
import per.xxmall.manage.mapper.ContentMapper;
import per.xxmall.manage.pojo.Content;

@Service
public class ContentService extends BaseService<Content>{
	
	@Autowired
	private ContentMapper contentMapper;

	public EasyUIResult queryList(Long categoryId, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		/*通用Mapper内置方法实现
		Example example = new Example(Content.class);
		example.setOrderByClause("updated DESC");
		example.createCriteria().andEqualTo("categoryId", categoryId);
		List<Content> list = contentMapper.selectByExample(example);*/
		
		//用原生mybatis实现
		List<Content> list = contentMapper.queryList(categoryId);
		PageInfo<Content> pageInfo = new PageInfo<Content>(list);
		return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
	}

}

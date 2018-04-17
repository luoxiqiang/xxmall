package per.xxmall.manage.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;

import per.xxmall.manage.pojo.Content;

public interface ContentMapper extends Mapper<Content>{

	List<Content> queryList(Long categoryId);

}

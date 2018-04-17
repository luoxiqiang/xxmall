package per.xxmall.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import per.xxmall.manage.pojo.BasePojo;

public abstract class BaseService<T extends BasePojo> {

	@Autowired
	private Mapper<T> mapper;
	

	//查询所有
	public List<T> queryAll(){
	
		return mapper.select(null);
	}
	
	//根据Id查询
	public T queryById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	//根据条件查询一条数据
	public T queryOne(T record) {
		return mapper.selectOne(record);
	}
	
	//根据条件查询多条数据
	public List<T> queryListByWhere(T record){
		return mapper.select(record);
	}
	
	//根据条件查询并分页
	public PageInfo<T> queryPageListByWhere(T record,Integer rows,Integer page){
		PageHelper.startPage(page, rows);
		return new PageInfo<T>(mapper.select(record));
	}
	
	//新增数据
	public Integer save(T t) {
		t.setCreated(new Date());
		t.setUpdated(t.getCreated());
		return mapper.insert(t);
	}
	
	//有选择的新增数据
	public Integer saveSelective(T t) {
		t.setCreated(new Date());
		t.setUpdated(t.getCreated());
		return mapper.insertSelective(t);
	}
	
	//修改数据
	public Integer updateSelective(T t) {
		t.setUpdated(new Date());
		return mapper.updateByPrimaryKeySelective(t);
	}
	
	//根据id删除数据
	public Integer deleteById(Long id) {
		return mapper.deleteByPrimaryKey(id);
	}
	
	//根据某个字段批量删除数据
	public Integer deleteByIds(Class<T> clazz, String property, List<Object> list) {
		Example example = new Example(clazz);
		example.createCriteria().andIn(property, list);
		return mapper.deleteByExample(example);
	}
	
	//根据条件删除数据
	public Integer deleteByWhere(T record) {
		return mapper.delete(record);
	}
}

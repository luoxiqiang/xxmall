package per.xxmall.solr.bean;

import java.util.List;

public class SearchResult {

	
	private Long total;
	
	private List<?> list;
	
	public SearchResult() {
		// TODO Auto-generated constructor stub
	}

	public SearchResult(List<?> list,Long total) {
		super();
		this.total = total;
		this.list = list;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	
}

package per.xxmall.solr.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.xxmall.solr.bean.Item;
import per.xxmall.solr.bean.SearchResult;

@Service
public class SearchService {
	
	@Autowired
	private HttpSolrServer httpSolrServer;
	
	public SearchResult search(String keyWords, Integer page, Integer rows) {
		
		keyWords = "title:"+keyWords+" AND status:1";
		//搜索条件构建
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(keyWords);
		solrQuery.setStart((Math.max(page,1)-1)*rows);
		solrQuery.setRows(rows);
		
		//是否可高亮
		boolean isHighlighting = !StringUtils.equals("*", keyWords) && StringUtils.isNotEmpty(keyWords);
		if(isHighlighting) {
			solrQuery.setHighlight(true); // 开启高亮组件
            solrQuery.addHighlightField("title");// 高亮字段
            solrQuery.setHighlightSimplePre("<em>");// 标记，高亮关键字前缀
            solrQuery.setHighlightSimplePost("</em>");// 后缀
		}
		try {
            // 执行查询
            QueryResponse queryResponse = this.httpSolrServer.query(solrQuery);
            List<Item> items = queryResponse.getBeans(Item.class);
            if (isHighlighting) {
                // 将高亮的标题数据写回到数据对象中
                Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
                for (Map.Entry<String, Map<String, List<String>>> highlighting : map.entrySet()) {
                    for (Item item : items) {
                        if (!highlighting.getKey().equals(item.getId().toString())) {
                            continue;
                        }
                        item.setTitle(StringUtils.join(highlighting.getValue().get("title"), ""));
                        break;
                    }
                }
            }
            return new SearchResult(items,queryResponse.getResults().getNumFound());
            
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
		return null;
	}

}

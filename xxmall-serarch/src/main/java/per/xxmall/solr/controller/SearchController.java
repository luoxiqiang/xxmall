package per.xxmall.solr.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import per.xxmall.solr.bean.Item;
import per.xxmall.solr.bean.SearchResult;
import per.xxmall.solr.service.SearchService;

@Controller
public class SearchController {
	
	private static final Integer ROWS = 32;
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam("q") String keyWords,
			@RequestParam(value="page",defaultValue = "1") Integer page) {
		
		ModelAndView mav = new ModelAndView("search");
		SearchResult searchResult = null;
        try {
        	keyWords = new String(keyWords.getBytes("ISO-8859-1"),"UTF-8");
            searchResult = this.searchService.search(keyWords, page, ROWS);
        } catch (Exception e) {
            e.printStackTrace();
            searchResult = new SearchResult(new ArrayList<Item>(0), 0L);
        }
        // 搜索关键字
        mav.addObject("query", keyWords);
        // 搜索结果集
        mav.addObject("itemList", searchResult.getList());
        // 当前页数
        mav.addObject("page", page);
        // 总页数
        int total = searchResult.getTotal().intValue();
        int pages = (total+ROWS-1)/ROWS;
        mav.addObject("pages", pages);

		return mav;
	}
	
	
}

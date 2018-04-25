package per.xxmall.cart.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;

import per.xxmall.cart.bean.Item;
import per.xxmall.cart.bean.User;
import per.xxmall.cart.bean.UserThreadLocal;
import per.xxmall.cart.mapper.CartMapper;
import per.xxmall.cart.pojo.Cart;
import per.xxmall.common.utils.CookieUtils;

@Service
public class CartService {

	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private ItemService itemService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public void createCart(Long itemId) {
		User user = UserThreadLocal.get();
		Cart record = queryCartById(itemId, user.getId());
		if(record != null){
			record.setNum(record.getNum()+1);
			record.setUpdated(new Date());
			cartMapper.updateByPrimaryKey(record);
		}else{
			Cart cart = setCart(itemId);
			if(cart == null) return;
			cart.setUserId(user.getId());
			cartMapper.insert(cart);
		}
		
	}
	
	public Cart queryCartById(Long itemId,Long userId){
		try {
			Cart record = new Cart();
			record.setItemId(itemId);
			record.setUserId(userId);
			Cart cart = cartMapper.selectOne(record);
			if(cart == null) return null;
			return cart;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Cart> queryCartList() {
		User user = UserThreadLocal.get();
		Example example = new Example(Cart.class);
		example.setOrderByClause("created DESC");
		example.createCriteria().andEqualTo("userId", user.getId());
		return cartMapper.selectByExample(example);
	}

	public void updateNum(Long itemId, Integer num) {
		User user = UserThreadLocal.get();
		Cart record = new Cart();
		record.setNum(num);
		record.setUpdated(new Date());
		//条件
		Example example = new Example(Cart.class);
		example.createCriteria().andEqualTo("itemId", itemId).andEqualTo("userId", user.getId());
		cartMapper.updateByExampleSelective(record, example);
	}

	public void createCookieCart(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		try {
			String cartCookie = queryCartCookie(request);
			Cart cart = setCart(itemId);
			List<Cart> list=null;
			if(StringUtils.isEmpty(cartCookie)){
				if(cart == null) return;//商品不存在TODO
				list = new ArrayList<>();
				list.add(cart);
			}else{
				list = MAPPER.readValue(cartCookie, 
					MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
				int status = 0;
				for(Cart ca:list){
					if(ca.getItemId().longValue() == itemId.longValue()){
						ca.setNum(ca.getNum()+1);
						ca.setUpdated(new Date());
						status = 1;
						break;
					}
				}
				if(status == 0){
					list.add(cart);
				}
			}
			CookieUtils.setCookie(request, response, "TT_CART", MAPPER.writeValueAsString(list), 
					60*60*24*30*12, true);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public Cart setCart(Long itemId){
		Item item = itemService.queryItemById(itemId);
		if(item == null){
			//没有此商品 
			return null;
		}
		Cart cart = new Cart();
		cart.setNum(1);
		cart.setCreated(new Date());
		cart.setUpdated(cart.getCreated());
		cart.setItemId(itemId);
		cart.setItemImage(item.getImages()[0]);
		cart.setItemPrice(item.getPrice());
		cart.setItemTitle(item.getTitle());
		return cart;
	}
	
	public String queryCartCookie(HttpServletRequest request){
		return CookieUtils.getCookieValue(request, "TT_CART",true);
	}
	
	public List<Cart> getCookieList(HttpServletRequest request){
		String cookie = queryCartCookie(request);
		try {
		if(cookie != null)
			return MAPPER.readValue(cookie, 
					MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void updateCookieNum(Long itemId, HttpServletRequest request,
			Integer num,HttpServletResponse response){
		try {
			List<Cart> list = getCookieList(request);
			for(Cart ca:list){
				if(ca.getItemId() == itemId){
					ca.setNum(num);
					ca.setUpdated(new Date());
					break;
				}
			}
			CookieUtils.setCookie(request, response, "TT_CART", MAPPER.writeValueAsString(list), 
					60*60*24*30*12, true);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
}

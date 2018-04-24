package per.xxmall.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;

import per.xxmall.cart.bean.Item;
import per.xxmall.cart.bean.User;
import per.xxmall.cart.bean.UserThreadLocal;
import per.xxmall.cart.mapper.CartMapper;
import per.xxmall.cart.pojo.Cart;

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
			
			Item item = itemService.queryItemById(itemId);
			if(item == null){
				//没有此商品 
				return;
			}
			Cart cart = new Cart();
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cart.setItemId(itemId);
			cart.setUserId(user.getId());
			cart.setItemImage(item.getImages()[0]);
			cart.setItemPrice(item.getPrice());
			cart.setItemTitle(item.getTitle());
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

	
	
}

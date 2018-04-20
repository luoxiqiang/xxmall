package per.xxmall.solr.bean;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.beans.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
	
	@Field("id")
	private Long id;

	@Field("cid")
	private Integer cid;
	
	@Field("image")
	private String image;
	
	@Field("price")
	private Long price;
	
	@Field("updated")
	private Long updated;
	
	@Field("status")
	private Integer status;
	
	@Field("title")
	private String title;
	
	@Field("sellPoint")
	private String sellPoint;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUpdated() {
		return updated;
	}

	public void setUpdated(Long updated) {
		this.updated = updated;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	
	public String[] getImages(){
		return StringUtils.split(getImage(),",");
	}
	
}

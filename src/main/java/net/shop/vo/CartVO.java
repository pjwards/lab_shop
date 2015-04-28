package net.shop.vo;

import java.io.Serializable;

public class CartVO implements Serializable{

	private static final long serialVersionUID = 7189614286736101498L;
	
	private GoodsVO goodsVO= new GoodsVO();
	private int quantity;
	
	public CartVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartVO(GoodsVO goodsVO, int quantity) {
		super();
		this.goodsVO = goodsVO;
		this.quantity = quantity;
	}
	
	public GoodsVO getGoodsVO() {
		return goodsVO;
	}
	public void setGoodsVO(GoodsVO goodsVO) {
		this.goodsVO = goodsVO;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}

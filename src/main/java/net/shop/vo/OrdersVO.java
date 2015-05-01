package net.shop.vo;

import java.util.Date;

/*
 	First Editor : Jisung Jeon (cbajs20@gmail.com)
	Last Editor  :
	Date         : 2015-04-21
*/
public class OrdersVO {
	
	private int number;
	private Date orderDate;
	private String orderNow;
	private String userEmail;
	private String userName;
	private int goodsNumber;
	private int quantity;
	private String userAddress;
	private int userPostcode;
	private String goodsName;
	private String goodsOptions;
	private int goodsPrice;
	private String manufacturer;	
	
	public OrdersVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrdersVO(String orderNow, String userEmail,
			String userName, int goodsNumber, int quantity, String userAddress,
			int userPostcode) {
		super();
		this.orderNow = orderNow;
		this.userEmail = userEmail;
		this.userName = userName;
		this.goodsNumber = goodsNumber;
		this.quantity = quantity;
		this.userAddress = userAddress;
		this.userPostcode = userPostcode;
	}

	
	public int getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderNow() {
		return orderNow;
	}
	public void setOrderNow(String orderNow) {
		this.orderNow = orderNow;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(int goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public int getUserPostcode() {
		return userPostcode;
	}
	public void setUserPostcode(int userPostcode) {
		this.userPostcode = userPostcode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsOptions() {
		return goodsOptions;
	}
	public void setGoodsOptions(String goodsOptions) {
		this.goodsOptions = goodsOptions;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
}

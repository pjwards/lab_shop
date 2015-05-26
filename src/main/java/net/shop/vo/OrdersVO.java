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
	private String receiver;
	private int quantity;
	private String address;
	private int postcode;
	private int totalPrice;
	private int boardNumber;	
	private String title;
	
	public OrdersVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrdersVO(String orderNow, String userEmail,
			String userName, int boardNumber, int quantity, String address,
			int postcode, int totalPrice, String receiver) {
		super();
		this.orderNow = orderNow;
		this.userEmail = userEmail;
		this.userName = userName;
		this.boardNumber = boardNumber;
		this.quantity = quantity;
		this.address = address;
		this.postcode = postcode;
		this.totalPrice = totalPrice;
		this.receiver = receiver;
	}

	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
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
	public int getBoardNumber() {
		return boardNumber;
	}
	public void setBoardNumber(int boardNumber) {
		this.boardNumber = boardNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPostcode() {
		return postcode;
	}
	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}
	
}

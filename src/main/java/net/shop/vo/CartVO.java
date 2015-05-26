package net.shop.vo;

import java.io.Serializable;

public class CartVO implements Serializable{

	private static final long serialVersionUID = 7189614286736101498L;
	
	private int number;
	private int quantity;
	private int boardNumber;
	private String userEmail;
	private String imagePath;
	private int price;
	private String title;
	
	public CartVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartVO(int quantity, int boardNumber,String userEmail) {
		super();
		this.quantity = quantity;
		this.boardNumber = boardNumber;
		this.userEmail = userEmail;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getBoardNumber() {
		return boardNumber;
	}
	public void setBoardNumber(int boardNumber) {
		this.boardNumber = boardNumber;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}

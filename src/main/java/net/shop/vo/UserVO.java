package net.shop.vo;

import java.io.Serializable;
import java.util.Date;


/**
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-03-23
 * Description  :
 * Copyright ⓒ 2013-2015 Jisung Jeon All rights reserved.
 * version      :
 */

public class UserVO implements Serializable{
	
	static final long serialVersionUID = 19900317L;
	
	private int number;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Date createdDate;
	private Date lastDate;
	private String authority;
	private String imagePath;
	
	public UserVO(){}
	
	public UserVO(String firstname,String lastname,String email){
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
	}
	public UserVO(String firstname,String lastname,String email, String imagePath){
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.imagePath = imagePath;
	}
	public UserVO(String firstname,String lastname,String email, String password,Date lastDate){
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.password = password;
		this.lastDate = lastDate;
	}
	public UserVO(String firstname,String lastname,String email, String password,String imagePath){
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.password = password;
		this.imagePath = imagePath;
	}
	
    public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}

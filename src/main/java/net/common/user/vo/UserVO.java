package net.common.user.vo;

import java.util.Date;

public class UserVO {
	private int no;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private Date createddate;
	private Date lastdate;
	
	public void setNo(int no){
		this.no=no;
	}
	public int getNo(){
		return this.no;
	}
	
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	public String getFirstname(){
		return this.firstname;
	}
	
	public void setLastname(String lastname){
		this.lastname = lastname;
	}
	public String getLastname(){
		return this.lastname;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return this.email;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return this.password;
	}
	
	public void setCreateddate(Date createddate){
		this.createddate = createddate;
	}
	public Date getCreateddate(){
		return this.createddate;
	}
	
	public void setLastdate(Date lastdate){
		this.lastdate = lastdate;
	}
	public Date getLastdate(){
		return this.lastdate;
	}
}

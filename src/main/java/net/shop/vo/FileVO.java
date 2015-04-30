package net.shop.vo;

/*
First Editor : Jisung Jeon (cbajs20@gmail.com)
Last Editor  :
Date         : 2015-04-30
*/
public class FileVO {
	private int no;
	private String realName;
	private String name;
	private String ext;
	private String uploader;
	private int boardNumber;
	
	public FileVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FileVO(String realName, String name, String ext,
			String uploader, int boardNumber) {
		super();
		this.realName = realName;
		this.name = name;
		this.ext = ext;
		this.uploader = uploader;
		this.boardNumber = boardNumber;
	}

	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public int getBoardNumber() {
		return boardNumber;
	}
	public void setBoardNumber(int boardNumber) {
		this.boardNumber = boardNumber;
	}
	
}

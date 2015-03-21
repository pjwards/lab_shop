package net.shop.vo;

import java.util.Date;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-03-20
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class BoardVO {

    private int number;
    private int groupNumber;
    private String sequenceNumber;
    private String title;
    private String content;
    private Date postingDate;
    private int readCount;
    private int commentCount;
    private int userNumber;
    private String userEmail;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /*
    중첩 레벨을 구해주는 메서드
     */
    public int getLevel(){
        if(sequenceNumber == null)          return -1;
        if(sequenceNumber.length() != 16)   return -1;

        if(sequenceNumber.endsWith("999999"))   return 0;   // 루트
        if(sequenceNumber.endsWith("9999"))     return 1;   // 첫 번째 자식
        if(sequenceNumber.endsWith("99"))       return 2;   // 두 번째 자식
        return 3;                                           // 세 번째 자식
    }
}

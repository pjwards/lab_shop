package net.shop.vo;

/**
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 15. 4. 5. | 오후 4:41
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
 */

public abstract class ReplyVO {

    public String sequenceNumber;

    public abstract int getNumber();
    public abstract String getSequenceNumber();

    /*
        중첩 레벨을 구해주는 메서드
     */
    public int getLevel(){
        if(sequenceNumber == null)          return -1;
        if(sequenceNumber.length() != 12)   return -1;

        if(sequenceNumber.endsWith("99"))   return 0;   // 루트
        return 1;   // 첫 번째 자식
    }

    /* 중첩 레벨을 구해주는 메서드 (Level 3)
    public int getLevel(){
        if(sequenceNumber == null)          return -1;
        if(sequenceNumber.length() != 16)   return -1;

        if(sequenceNumber.endsWith("999999"))   return 0;   // 루트
        if(sequenceNumber.endsWith("9999"))     return 1;   // 첫 번째 자식
        if(sequenceNumber.endsWith("99"))       return 2;   // 두 번째 자식
        return 3;                                           // 세 번째 자식
    }
    */
}

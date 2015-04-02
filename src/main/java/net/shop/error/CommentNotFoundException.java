package net.shop.error;

/**
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 2015. 4. 2. | 오후 11:10
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
 */

public class CommentNotFoundException extends Exception {
    public CommentNotFoundException(String msg){
        super(msg);
    }
}

package net.shop.error;

/**
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 4/16/15 | 1:01 PM
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
 */

public class GoodsNotFoundException extends NotFoundException {
    public GoodsNotFoundException(String msg){
        super(msg);
    }
}

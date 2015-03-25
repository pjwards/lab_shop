package net.shop.error;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-03-24
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class MemberIdNotFoundException extends NotEqualsException {
    public MemberIdNotFoundException(String msg){
        super(msg);
    }
}

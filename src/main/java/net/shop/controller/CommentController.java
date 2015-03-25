package net.shop.controller;

import net.shop.service.BoardService;
import net.shop.service.UserService;
import net.shop.util.Util;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-03-25
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */

@Controller
public class CommentController {

    @Resource(name = "boardService")
    private BoardService boardService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "util")
    private Util util;

}

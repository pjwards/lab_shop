package net.shop.controller;

import net.shop.error.GoodsNotFoundException;
import net.shop.service.GoodsService;
import net.shop.service.UserService;
import net.shop.util.Util;
import net.shop.vo.CartVO;
import net.shop.vo.GoodsVO;
import net.shop.vo.OrdersVO;
import net.shop.vo.PagingVO;
import net.shop.vo.UserVO;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 4/16/15 | 11:30 AM
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController {

    @Resource(name = "goodsService")
    private GoodsService goodsService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "util")
    private Util util;

    /*
    상품 리스트
     */
    @RequestMapping(value = "/list.do")
    public ModelAndView goodsList(@RequestParam(value = "p", required = false) String requestPageString,
                                  Authentication auth) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        String memberId = util.isMemberId(auth);

        if(requestPageString == null || requestPageString.equals("")) requestPageString = "1";
        int requestPage = Integer.parseInt(requestPageString);
        if(requestPage <= 0) return (ModelAndView)new ModelAndView("redirect:/goods/list.do");

        int totalCount = goodsService.selectCount(memberId);


        /*Paging 메소드의 사용 */
        PagingVO pagingVO = util.paging(requestPage, 10, totalCount);
        modelAndView.addObject("pagingVO", pagingVO);
        modelAndView.setViewName("/goods/list");

        /*
        상품이 없을 때 수행된다.
         */
        if(totalCount == 0){
            modelAndView.addObject("goodsVOList", Collections.<GoodsVO>emptyList());
            modelAndView.addObject("hasGoods", false);
            return modelAndView;
        }

        List<GoodsVO> goodsVOList = goodsService.selectList(memberId, pagingVO.getFirstRow(), pagingVO.getEndRow());

        modelAndView.addObject("goodsVOList", goodsVOList);
        modelAndView.addObject("hasGoods", true);

        return modelAndView;
    }

    /*
    상품 등록 폼
     */
    @RequestMapping(value = "/write.do")
    public String goodsWrite(Authentication auth) throws Exception{

        util.isMemberId(auth);
        return "/goods/writeForm";
    }

    /*
    상품 등록
     */
    @RequestMapping(value = "/write.do", method = RequestMethod.POST)
    public String goodsWrite(@RequestParam(value = "name", required = true) String name,
                             @RequestParam(value = "size", required = true) String size,
                             @RequestParam(value = "material", required = true) String material,
                             @RequestParam(value = "component", required = true) String component,
                             @RequestParam(value = "options", required = true) String options,
                             @RequestParam(value = "manufacturer", required = true) String manufacturer,
                             @RequestParam(value = "madein", required = true) String madein,
                             @RequestParam(value = "description", required = true) String description,
                             @RequestParam(value = "price", required = true) Integer price,
                             @RequestParam(value = "stock", required = true) Integer stock,
                             Authentication auth) throws Exception{

        String memberId = util.isMemberId(auth);

        int userNumber = userService.selectOneNo(memberId);

        GoodsVO goodsVO = new GoodsVO();
        goodsVO.setName(name);
        goodsVO.setSize(size);
        goodsVO.setMaterial(material);
        goodsVO.setComponent(component);
        goodsVO.setOptions(options);
        goodsVO.setManufacturer(manufacturer);
        goodsVO.setMadein(madein);
        goodsVO.setDescription(description);
        goodsVO.setPrice(price);
        goodsVO.setStock(stock);
        goodsVO.setUserNumber(userNumber);
        goodsVO.setUserEmail(memberId);

        goodsService.insert(goodsVO);

        return "redirect:/goods/list.do";
    }

    /*
    상품 읽기
     */
    @RequestMapping(value = "/read.do")
    public ModelAndView goodsRead(@RequestParam(value = "goodsNumber", required = true) Integer goodsNumber)
            throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        GoodsVO goodsVO = goodsService.selectOne(goodsNumber);

        if(goodsVO == null) throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);

        modelAndView.addObject("goodsVO", goodsVO);
        modelAndView.setViewName("/goods/read");

        return modelAndView;
    }

    /*
    상품 수정 폼
     */
    @RequestMapping(value = "/update.do")
    public ModelAndView goodsUpdate(@RequestParam(value = "goodsNumber", required = true) Integer goodsNumber,
                                    Authentication auth) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        String memberId = util.isMemberId(auth);

        GoodsVO goodsVO = goodsService.selectOne(goodsNumber);
        if(goodsVO == null) throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);

        util.isEqualMemberId(goodsVO.getUserEmail(), memberId);

        modelAndView.addObject("goodsVO", goodsVO);
        modelAndView.setViewName("/goods/updateForm");
        return modelAndView;
    }

    /*
    상품 수정
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String goodsUpdate(@RequestParam(value = "goodsNumber", required = true) Integer goodsNumber,
                              @RequestParam(value = "name", required = true) String name,
                              @RequestParam(value = "size", required = true) String size,
                              @RequestParam(value = "material", required = true) String material,
                              @RequestParam(value = "component", required = true) String component,
                              @RequestParam(value = "options", required = true) String options,
                              @RequestParam(value = "manufacturer", required = true) String manufacturer,
                              @RequestParam(value = "madein", required = true) String madein,
                              @RequestParam(value = "description", required = true) String description,
                              @RequestParam(value = "price", required = true) Integer price,
                              @RequestParam(value = "stock", required = true) Integer stock,
                              @RequestParam(value = "p", required = false) String page,
                              Authentication auth) throws Exception {

        String memberId = util.isMemberId(auth);

        GoodsVO goodsVO = goodsService.selectOne(goodsNumber);
        if(goodsVO == null) throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);

        util.isEqualMemberId(goodsVO.getUserEmail(), memberId);

        GoodsVO newGoodsVO = new GoodsVO();
        newGoodsVO.setNumber(goodsNumber);

        if(!goodsVO.getName().equals(name)) newGoodsVO.setName(name);
        if(!goodsVO.getSize().equals(size)) newGoodsVO.setSize(size);
        if(!goodsVO.getMaterial().equals(material)) newGoodsVO.setMaterial(material);
        if(!goodsVO.getComponent().equals(component)) newGoodsVO.setComponent(component);
        if(!goodsVO.getOptions().equals(options)) newGoodsVO.setOptions(options);
        if(!goodsVO.getManufacturer().equals(manufacturer)) newGoodsVO.setManufacturer(manufacturer);
        if(!goodsVO.getMadein().equals(madein)) newGoodsVO.setMadein(madein);
        if(!goodsVO.getDescription().equals(description)) newGoodsVO.setDescription(description);
        if(goodsVO.getPrice() != price) newGoodsVO.setPrice(price);
        if(goodsVO.getStock() != stock) newGoodsVO.setStock(stock);

        int updateCount = goodsService.update(newGoodsVO);
        if (updateCount == 0) throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);

        return "redirect:/goods/read.do?p=" + page + "&goodsNumber=" + goodsNumber;
    }

    /*
    상품 삭제
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public String goodsDelete(@RequestParam(value = "goodsNumber", required = true) Integer goodsNumber,
                              @RequestParam(value = "p", required = false) String page,
                              Authentication auth) throws Exception {

        String memberId = util.isMemberId(auth);

        GoodsVO goodsVO = goodsService.selectOne(goodsNumber);
        if(goodsVO == null) throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);

        util.isEqualMemberId(goodsVO.getUserEmail(), memberId);

        List<Integer> boardGoodsList = goodsService.selectBoardGoodsByGoods(goodsNumber);

        if(!boardGoodsList.isEmpty()) {
            for(int boardNumber: boardGoodsList) {
                goodsService.decreaseGoodsCount(boardNumber);
            }
            goodsService.deleteBoardGoodsByGoods(goodsNumber);
        }

        goodsService.delete(goodsNumber);

        return "redirect:/goods/list.do?p=" + page;
    }

    /*
    게시글에 대한 상품 리스트
     */
    @RequestMapping(value = "/listByBoard.do")
    public ModelAndView goodsListByBoard(@RequestParam(value = "boardNumber", required = true) Integer boardNumber)
            throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        List<Integer> boardGoodsList = goodsService.selectBoardGoodsByBoard(boardNumber);
        List<GoodsVO> goodsVOList = new ArrayList<GoodsVO>();


        if(!boardGoodsList.isEmpty()) {
            for(int goodsNumber: boardGoodsList) {
                goodsVOList.add(goodsService.selectOne(goodsNumber));
            }

            modelAndView.addObject("goodsVOList", goodsVOList);
            modelAndView.addObject("hasGoods", true);
        } else {
            modelAndView.addObject("goodsVOList", Collections.<GoodsVO>emptyList());
            modelAndView.addObject("hasGoods", false);
        }

        modelAndView.setViewName("/goods/listByBoard");

        return modelAndView;
    }
    
    //cart list
    @RequestMapping("/cart.do")
	public String userDelete() throws Exception{
		return "/goods/cart";
	}
  
    //add cart
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/addCart.do")
    public String addCart(@RequestParam(value="number") int number,Model model,
    		@RequestParam(value="quantity") String quantity,HttpSession session,
    		@RequestParam(value = "p", required = false) String page,HttpServletRequest request)throws Exception{
    
    	GoodsVO goodsVO = goodsService.selectOne(number);
    	
    	if(quantity == null || quantity.isEmpty()){
			model.addAttribute("say", "Wrong Input");
			model.addAttribute("url", request.getContextPath()+"/goods/list.do");
			return "/error/alert";
		}
    	
    	
    	String termQan = quantity.trim().toLowerCase();
    	int quan = 1;
    	try{
    		quan = Integer.parseInt(termQan);
    	}catch(Exception e){
    		model.addAttribute("say", "Wrong Input");
			model.addAttribute("url", request.getContextPath()+"/goods/list.do");
			return "/error/alert";
    	}
    	
    	if(session.getAttribute("cart") == null){
    		List<CartVO> cart = new ArrayList<CartVO>();
    		cart.add(new CartVO(goodsVO, quan));
    		session.setAttribute("cart", cart);
    	}else{
    		List<CartVO> cart = (List<CartVO>)session.getAttribute("cart");
    		int index = isExsisting(number, session);
    		if(index == -1){
    			cart.add(new CartVO(goodsVO, quan));
    		}
    		//int amount = cart.get(index).getQuantity() + 1;
    		//cart.get(index).setQuantity(amount);
    		session.setAttribute("cart", cart);
    	}
    	
    	return "redirect:/goods/read.do?p=" + page + "&goodsNumber=" + number;
    }
    
    //delete cart
  	@SuppressWarnings("unchecked")
	@RequestMapping(value="/delCart.do")
  	public String delCart(@RequestParam("choice")String choice,@RequestParam("no")String number,
  			HttpServletRequest request, Model model, HttpSession session) throws Exception{
  		
  		if(choice == null || choice.isEmpty()){
  			model.addAttribute("say", "Wrong Input");
  			model.addAttribute("url", request.getContextPath()+"/user/wishlist.do");
  			return "/error/alert";
  		}
  		
  		if(choice.compareTo("true") != 0){
  			return "redirect:/goods/cart.do";
  		}
  		
  		int no = Integer.parseInt(number);
		List<CartVO> cart = (List<CartVO>)session.getAttribute("cart");
		int index = isExsisting(no, session);
		cart.remove(index);
		session.setAttribute("cart", cart);

  		return "/goods/cart";	
  	}
  	
  	//add order
  	@SuppressWarnings("unchecked")
	@RequestMapping(value="/addOrders.do")
  	public String addOrders(HttpServletRequest request, Model model, HttpSession session
  			,Authentication auth) throws Exception{
  		
  		UserDetails vo = (UserDetails) auth.getPrincipal();
		String email = vo.getUsername();
		UserVO userVO = userService.selectOneVo(email);
		List<CartVO> cart = (List<CartVO>)session.getAttribute("cart");
		
		for(int i = 0 ; i<cart.size() ;i++){	//run two "for" statement cause they doesn't work together
  			OrdersVO ordersVO = new OrdersVO("Ready", email, userVO.getLastName(), cart.get(i).getGoodsVO().getNumber(), cart.get(i).getQuantity(), userVO.getAddress(), userVO.getPostcode());
  			goodsService.addorderlist(ordersVO);
  			
		}
		for(int i = 0 ; i<cart.size() ;i++){
			cart.remove(i);
		}
		
  		model.addAttribute("say", "Buy it successfully");
		model.addAttribute("url", request.getContextPath()+"/user/orders.do");
		return "/error/alert";
  	}
  	//check if data of id exists
    @SuppressWarnings("unchecked")
	private int isExsisting(int id, HttpSession session){
		List<CartVO> cart = (List<CartVO>)session.getAttribute("cart");
		for(int i =0 ; i<cart.size() ;i++)
			if(cart.get(i).getGoodsVO().getNumber() == id)
				return i;
		return -1;
    }
}

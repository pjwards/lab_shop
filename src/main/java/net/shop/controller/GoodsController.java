package net.shop.controller;

import net.shop.error.GoodsNotFoundException;
import net.shop.service.BoardService;
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
import javax.servlet.http.HttpServletResponse;

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

    @Resource(name = "boardService")
    private BoardService boardService;
    
    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "util")
    private Util util;

    /*
    상품 리스트
     */
    @RequestMapping(value = "/list.do")
    public ModelAndView goodsList(@RequestParam(value = "p", required = false) String requestPageString,
                                  @RequestParam(value="q",required=false) String keyword,
                                  @RequestParam(value = "s", required = false, defaultValue = "default") String s,
                                  Authentication auth) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        if(s.equals("product")){
            modelAndView.addObject("isProduct", true);
        } else  {
            modelAndView.addObject("isProduct", false);
        }


        String memberId = util.isMemberId(auth);

        if(requestPageString == null || requestPageString.equals("")) requestPageString = "1";
        int requestPage = Integer.parseInt(requestPageString);
        if(requestPage <= 0) return (ModelAndView)new ModelAndView("redirect:/goods/list.do");

        if(keyword == null || keyword.equals("")) {
            keyword = null;
        }

        int totalCount = goodsService.selectCount(memberId, keyword);


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

        List<GoodsVO> goodsVOList = goodsService.selectList(memberId, pagingVO.getFirstRow(), pagingVO.getEndRow(), keyword);

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
        newGoodsVO.setPrice(price);
        newGoodsVO.setStock(stock);

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
	public ModelAndView cartList(HttpServletRequest request, Authentication auth) throws Exception{
   
    	ModelAndView modelandview = new ModelAndView();
    	UserDetails vo = (UserDetails) auth.getPrincipal();
    	String email = vo.getUsername();
            
        List<CartVO> lists = userService.cartList(email);
        if(lists.isEmpty()){
        	request.setAttribute("hasUser", false);
        	return modelandview;
        }
        UserVO userVO = userService.selectOneVo(email);
        modelandview.setViewName("/goods/cart");
        modelandview.addObject("cartlist", lists);
        modelandview.addObject("user", userVO);
    	request.setAttribute("hasUser", true);
    		
    	return modelandview;
	}
    
    //ajax add cart
    @RequestMapping(value="/addCartAjax.do", method=RequestMethod.POST)
    public void addCartAjax(@RequestParam(value="number") int boardNumber,
    		@RequestParam(value = "choice") String choice,
    		HttpServletResponse response, Authentication auth)throws Exception{
    	
    	if(choice == null || choice.isEmpty()){
    		response.getWriter().print("400");
			return;
		}
    	//int requestPage = Integer.parseInt(boardNumber);
    	UserDetails vo = (UserDetails) auth.getPrincipal();
    	String email = vo.getUsername();
    	
    	if(goodsService.cartOne(boardNumber,email) != null){
    		response.getWriter().print("404");
			return;
    	}
    	
		CartVO cartVO = new CartVO(1,boardNumber,email);
		
    	if(choice.compareTo("go") != 0){
    		goodsService.addcartlist(cartVO);
			response.getWriter().print("200");
			return;
		}else{
			goodsService.addcartlist(cartVO);
    		userService.delWishlist(email, boardNumber);
    		response.getWriter().print("202");
			return;
		}
    	
    }
    
    //ajax change Quantity of specific data
    @RequestMapping(value="/changeQuan.do", method=RequestMethod.POST)
    public void changeQuan(@RequestParam(value="quantity") int quantity,
    		@RequestParam(value = "number") int cartNumber,
    		HttpServletResponse response, Authentication auth)throws Exception{
    	
    	if(quantity == 0){
    		response.getWriter().print("400");
			return;
    	}
    	
    	if(goodsService.cartChange(quantity,cartNumber) == 0){
    		response.getWriter().print("400");
			return;
		}
    	
    	response.getWriter().print(quantity);
    }
    //add cart
	@RequestMapping(value="/addCart.do")
    public String addCart(@RequestParam(value="number") int boardNumber,Model model,
    		@RequestParam(value="quantity", required = false) String quantity,
    		@RequestParam(value = "p", required = false) String page,
    		@RequestParam(value = "s", required = false) String seperate,
    		HttpServletRequest request, Authentication auth)throws Exception{
        
    	if(quantity == null || quantity.isEmpty()){
			model.addAttribute("say", "Wrong Input");
			model.addAttribute("url", request.getContextPath()+"/board/read.do?s="+seperate+"&p=" + page + "&boardNumber=" + boardNumber);
			return "/error/alert";
		}

    	String termQan = quantity.trim().toLowerCase();
    	
    	int quan = 1;
    	
    	try{
    		quan = Integer.parseInt(termQan);
    	}catch(Exception e){
    		model.addAttribute("say", "Wrong Input");
			model.addAttribute("url", request.getContextPath()+"/board/read.do?s="+seperate+"&p=" + page + "&boardNumber=" + boardNumber);
			return "/error/alert";
    	}
    	
    	UserDetails vo = (UserDetails) auth.getPrincipal();
		String email = vo.getUsername();
		
    	if(goodsService.cartOne(boardNumber, email) != null){
    		model.addAttribute("say", "Already listed");
			model.addAttribute("url", request.getContextPath()+"/board/read.do?s="+seperate+"&p=" + page + "&boardNumber=" + boardNumber);
			return "/error/alert";
		}
		
		CartVO cartVO = new CartVO(quan,boardNumber,email);
    	goodsService.addcartlist(cartVO);
    	
        model.addAttribute("say", "Added it");
		model.addAttribute("url", request.getContextPath()+"/board/read.do?s="+seperate+"&p=" + page + "&boardNumber=" + boardNumber);
    	return "/error/alert";
    			
    }
    
    //delete cart
	@RequestMapping(value="/delCart.do")
  	public String delCart(@RequestParam("choice")String choice,@RequestParam("no")int number,
  			HttpServletRequest request, Model model) throws Exception{
  		
  		if(choice == null || choice.isEmpty()){
  			model.addAttribute("say", "Wrong Input");
  			model.addAttribute("url", request.getContextPath()+"/goods/cart.do");
  			return "/error/alert";
  		}
  		
  		if(choice.compareTo("true") != 0){
  			return "redirect:/goods/cart.do";
  		}
  		
  		if(goodsService.cartDelete(number) == 0){
			model.addAttribute("say", "Wrong already deleted");
			model.addAttribute("url", request.getContextPath()+"/goods/cart.do");
			return "/error/alert";
		}
  		return "redirect:/goods/cart.do";	
  	}
  	
  	//add order
	@RequestMapping(value="/addOrders.do")
  	public String addOrders(@RequestParam("no")int[] boardNumbers,
  			@RequestParam("addr")String address,
  			@RequestParam("post")int postcode,
  			@RequestParam("name")String receiver,
  			HttpServletRequest request, Model model
  			,Authentication auth) throws Exception{
  		
  		if(address == null || address.isEmpty() || address.isEmpty() || receiver == null || receiver.isEmpty()){
  			model.addAttribute("say", "Wrong Input");
  			model.addAttribute("url", request.getContextPath()+"/goods/cart.do");
  			return "/error/alert";
  		}
  		
  		UserDetails vo = (UserDetails) auth.getPrincipal();
		String email = vo.getUsername();
		UserVO userVO = userService.selectOneVo(email);
		String sender = userVO.getLastName();

        boolean check = false;
        String say = "[";
        List<OrdersVO> ordersVOList = new ArrayList<>();
		
		if(boardNumbers != null) {
            for (int boardNumber : boardNumbers) {
            	CartVO cartVO = goodsService.cartOne(boardNumber, email);
          		int price = cartVO.getPrice();
          		int quantity = cartVO.getQuantity();

                /*
                First Editor : Donghyun Seo (egaoneko@naver.com)
                Last Editor  :
                Date         : 2015-06-06
                */

                if(!checkStock(boardNumber, quantity)){
                    check = true;
                    say += boardService.selectOne(boardNumber).getTitle();
                    say += ", ";
                }

                ordersVOList.add(new OrdersVO("Ok", email, sender, boardNumber, quantity, address, postcode, price, receiver));
            }

            /*
            First Editor : Donghyun Seo (egaoneko@naver.com)
            Last Editor  :
            Date         : 2015-06-06
            */

            if (check){
                say += "] 상품의 수량이 부족합니다. 관리자에 문의하여 주세요.";

                model.addAttribute("say", say);
                model.addAttribute("url", request.getContextPath()+"/goods/cart.do");
                return "/error/alert";
            }

            for(OrdersVO ordersVO : ordersVOList){
                goodsService.addorderlist(ordersVO);
                goodsService.cartDelete(ordersVO.getBoardNumber(), ordersVO.getUserEmail());
                decreaseStock(ordersVO.getBoardNumber(), ordersVO.getQuantity());
            }
        }else{
        	model.addAttribute("say", "Wrong Input");
  			model.addAttribute("url", request.getContextPath()+"/goods/cart.do");
  			return "/error/alert";
        }
	
		
  		model.addAttribute("say", "Buy it successfully");
		model.addAttribute("url", request.getContextPath()+"/user/orders.do");
		return "/error/alert";
  	}

    /*
	First Editor : Donghyun Seo (egaoneko@naver.com)
	Last Editor  :
	Date         : 2015-06-06
	*/
    private boolean checkStock(int boardNumber, int quantity) throws Exception{

        List<Integer> boardGoodsList = goodsService.selectBoardGoodsByBoard(boardNumber);
        GoodsVO goodsVO;

        if(!boardGoodsList.isEmpty()) {
            for(int goodsNumber: boardGoodsList) {
                goodsVO = goodsService.selectOne(goodsNumber);
                if(goodsVO.getStock() < quantity) return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /*
	First Editor : Donghyun Seo (egaoneko@naver.com)
	Last Editor  :
	Date         : 2015-06-06
	*/
    private void decreaseStock(int boardNumber, int quantity) throws Exception {

        List<Integer> boardGoodsList = goodsService.selectBoardGoodsByBoard(boardNumber);

        if (!boardGoodsList.isEmpty()) {
            for (int goodsNumber : boardGoodsList) {
                goodsService.decreaseGoodsStock(goodsNumber, quantity);
            }
        }
    }
}

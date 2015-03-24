package net.shop.util;

import net.shop.error.MemberIdNotEqualsException;
import net.shop.error.MemberIdNotFoundException;
import net.shop.vo.BoardVO;
import net.shop.vo.PagingVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 2015-03-23
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
 */

@Repository("util")
public class Util {

    public PagingVO paging(int requestPage, int countPerPage, int totalCount) {

        int totalPageCount = 0;
        int firstRow = 0;
        int endRow = 0;
        int beginPage = 0;
        int endPage = 0;

        if(totalCount != 0 || requestPage < 0) {
            /*
            전체 게시글 개수로부터 전체 페이지 개수를 구해주는 부분
            */
            // 총 게시글 : 31 / 페이지 당 글 : 10 일때
            totalPageCount = totalCount / countPerPage;         // pageCount : 3
            if(totalCount % countPerPage > 0) {
                totalPageCount++;                               // 나머지가 1이므로 pageCount : 4
            }

            /*
            게시글의 첫 줄과 마지막 줄을 구하는 부분
             */
            firstRow = (requestPage - 1) * countPerPage + 1;
            endRow = firstRow + countPerPage - 1;               // 자기 자신도 포함되니 하나를 빼주어야 한다.

            if(endRow > totalCount){
                endRow = totalCount;
            }

            /*
            페이지의 시작과 끝을 알려주는 부분
             */
            if(totalCount != 0) {
                beginPage = (requestPage - 1) / countPerPage * countPerPage + 1;
                endPage = beginPage + countPerPage - 1;
                if(endPage > totalPageCount){
                    endPage = totalPageCount;
                }
            }
        } else {
            requestPage = 0;
        }

        PagingVO pagingVO = new PagingVO(requestPage, totalPageCount, firstRow, endRow, beginPage, endPage);

        return pagingVO;
    }

    public void isMemberId(String memberId) throws MemberIdNotFoundException{
        if(memberId == null || memberId.equals("")){
            throw new MemberIdNotFoundException("로그인을 하지 않았습니다.");
        }
    }

    public void isEqualMemberId(String email, String memberId) throws MemberIdNotEqualsException{
        if (!email.equals(memberId)) {
            throw new MemberIdNotEqualsException("작성자의 ID와 로그인한 사용자의 ID가 다릅니다.");
        }
    }

}

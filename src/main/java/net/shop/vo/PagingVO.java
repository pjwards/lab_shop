package net.shop.vo;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-03-23
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */

public class PagingVO {

    private int requestPage;        // 요청 페이지 번호
    private int totalPageCount;     // 전체 페이지 수
    private int firstRow;           // 첫 줄
    private int endRow;             // 마지막 줄
    private int beginPage;          // 시작 페이지
    private int endPage;            // 끝 페이지

    public PagingVO(int requestPage, int totalPageCount, int firstRow, int endRow, int beginPage, int endPage) {
        this.requestPage = requestPage;
        this.totalPageCount = totalPageCount;
        this.firstRow = firstRow;
        this.endRow = endRow;
        this.beginPage = beginPage;
        this.endPage = endPage;
    }

    public int getRequestPage() {
        return requestPage;
    }

    public void setRequestPage(int requestPage) {
        this.requestPage = requestPage;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
}

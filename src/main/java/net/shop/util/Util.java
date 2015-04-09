package net.shop.util;

import net.shop.error.*;
import net.shop.vo.PagingVO;
import net.shop.vo.ReplyVO;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

/**
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 2015-03-23
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
 */

@Component("util")
public class Util {

    /*
    페이징
     */
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

    /*
    로그인 확인
     */
    public void isMemberId(String memberId) throws MemberIdNotFoundException{
        if(memberId == null || memberId.equals("")){
            throw new MemberIdNotFoundException("로그인을 하지 않았습니다.");
        }
    }

    /*
    사용자 확인
     */
    public void isEqualMemberId(String email, String memberId) throws MemberIdNotEqualsException{
        if (!email.equals(memberId)) {
            throw new MemberIdNotEqualsException("작성자의 ID와 로그인한 사용자의 ID가 다릅니다.");
        }
    }

    /*
    부모 글이 올바른지의 여부를 검사
     */
    public void checkParent(ReplyVO parent, int parentNumber) throws NotFoundException, CannotReplyException {
        if(parent == null){
            throw new NotFoundException("부모글이 존재하지 않음 : " + parentNumber);
        }

        int parentLevel = parent.getLevel();
        if(parentLevel == 3){
            throw new CannotReplyException("마지막 레벨 글에는 답글을 달 수 없습니다 : " + parent.getNumber());
        }
    }

    /*
    최소 순서 번호를 반환한다.
     */
    public String getSearchMinSeqNum(ReplyVO parent){
        String parentSeqNum = parent.getSequenceNumber();
        DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");
        long parentSeqLongValue = Long.parseLong(parentSeqNum);
        long searchMinLongValue = 0;
        switch (parent.getLevel()){                                             // 0000009999 654321
            case 0:
                searchMinLongValue = parentSeqLongValue / 1000000L * 1000000L;  // 0000009999 000000
                break;
            case 1:
                searchMinLongValue = parentSeqLongValue / 10000L * 10000L;      // 0000009999 650000
                break;
            case 2:
                searchMinLongValue = parentSeqLongValue / 100L * 100L;          // 0000009999 654300
                break;
        }
        return decimalFormat.format(searchMinLongValue);
    }

    /*
    순서 번호를 생성한다.
     */
    public String getSequenceNumber(ReplyVO parent, String lastChildSeq) throws LastChildAleadyExistsException {
        long parentSeqLong = Long.parseLong(parent.getSequenceNumber());
        int parentLevel = parent.getLevel();

        long decUnit = 0;
        if(parentLevel == 0){
            decUnit = 10000L;
        } else if(parentLevel == 1){
            decUnit = 100L;
        } else if(parentLevel ==2){
            decUnit = 1L;
        }

        String sequenceNumber = null;

        DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");
        if(lastChildSeq == null){   // 답변글이 없음
            sequenceNumber = decimalFormat.format(parentSeqLong - decUnit);
        } else {    // 답변글이 있음
            // 마지막 답변글인지 확인
            String orderOfLastChildSeq = null;
            if(parentLevel == 0){
                orderOfLastChildSeq = lastChildSeq.substring(10, 12);       // 0000000000 00 0000
                sequenceNumber = lastChildSeq.substring(0, 12) + "9999";    // 000000000000 + 9999
            } else if(parentLevel == 1){
                orderOfLastChildSeq = lastChildSeq.substring(12, 14);       // 000000000000 00 00
                sequenceNumber = lastChildSeq.substring(0, 14) + "99";      // 00000000000000 + 99
            } else if(parentLevel == 2){
                orderOfLastChildSeq = lastChildSeq.substring(14, 16);       // 00000000000000 00
                sequenceNumber = lastChildSeq;
            }
            if(orderOfLastChildSeq.equals("00")){
                throw new LastChildAleadyExistsException("마지막 자식글이 이미 존재합니다 : " + lastChildSeq);
            }
            long seq = Long.parseLong(sequenceNumber) - decUnit;
            sequenceNumber = decimalFormat.format(seq);
        }
        return sequenceNumber;
    }
    
    /*
    Editor : Jisung Jeon
    Decription : upload
     */
    public static void fileUpload(MultipartFile multipartFile, String path, String fileName) throws IOException {
    	 //String originalFileName = multipartFile.getOriginalFilename();
    	 //String contentType = multipartFile.getContentType();
    	 long fileSize = multipartFile.getSize();
    	 InputStream is = null;
    	 OutputStream out = null;
    	 
    	 try{
    		 if (fileSize > 0) {
    			 is = multipartFile.getInputStream();
    			 File realUploadDir = new File(path);
    			 if (!realUploadDir.exists()) {     //if doesn't exist file in the path, make it
    				 realUploadDir.mkdirs();
    			 }
    			 out = new FileOutputStream(path +"/"+ fileName);
    			 FileCopyUtils.copy(is, out);       //copy file of InputStream to outputStream
    		 }else{
    			 new IOException("Wrong File");
    		 }
    	 }catch(IOException e){
    		 e.printStackTrace();
    		 new IOException("Upload failed");
    	 }finally{
    		 if(out != null){out.close();}
    		 if(is != null){is.close();}
    	 }
    }
}

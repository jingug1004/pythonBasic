package com.hanaph.gw.co.common.utils;

/**
 * <pre>
 * Class Name : PageUtil.java
 * 설명 : 페이지 Util
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 17. JaeKap Kim		생성          
 * </pre>
 * 
 * @version : 
 * @author slawin(@irush.co.kr)
 * @since   : 2014. 10. 20.
 */
public class PageUtil
{
  public static PageUtil instance = new PageUtil();
  
  public PageUtil()
  {
  }
  
  /**
 * <pre>
 * 1. 개요     : 페이징 처리를 하기 위한 pagingUtilClass
 * 2. 처리내용 : List controller에서 받은 4개의 값으로 page html 구성
 * </pre>
 * @Method Name : autoPaging
 * @param plTotalCnt  게시글 전체 카운트 수
 * @param plRowRange  list에 보여질 row갯수
 * @param plPageRange list에 보여질 page 수 
 * @param plCurrPage  현재 페이지 번호
 * @return tsRetVal.toString()
 */		
public String autoPaging(int plTotalCnt, int plRowRange, int plPageRange, int plCurrPage)
  {
	  
	long lplTotalCnt  = (long)plTotalCnt;
	long lplRowRange  = (long)plRowRange;
	long lplPageRange  = (long)plPageRange;
	long lplCurrPage  = (long)plCurrPage;
	
    StringBuffer tsRetVal = new StringBuffer();
    if (lplTotalCnt == 0L)
    {
      return "";
    }


    long plPageCnt = lplTotalCnt % lplRowRange;
    if (plPageCnt == 0L)
    {
      plPageCnt = lplTotalCnt / lplRowRange;
    }
    else
    {
      plPageCnt = lplTotalCnt / lplRowRange + 1L;
    }

    tsRetVal.append("<div class='wrap_paging'>");
    


    long plRangeCnt = lplCurrPage / lplPageRange;
    if (lplCurrPage % lplPageRange == 0L)
    {
      plRangeCnt = lplCurrPage / lplPageRange - 1L;
    }

		tsRetVal.append("<button type='button' class='prev01' onclick=\"javascript:goPage('1');\"><span class='blind'>첫번째 페이지로 이동</span></button>");

    if (lplCurrPage > lplPageRange)
    {
      String s2;
      if (lplCurrPage % lplPageRange == 0L)
      {
        s2 = Long.toString(lplCurrPage - lplPageRange);
      }
      else
      {
        s2 = Long.toString(lplCurrPage - lplCurrPage % lplPageRange);
      }
		tsRetVal.append("<button type='button' class='prev02' onclick=\"goPage('").append(s2).append("');\"><span class='blind'>다음 페이지로 이동</span></button>");
    }
    else
    {
    	tsRetVal.append("<button type='button' class='prev02'><span class='blind'>다음 페이지로 이동</span></button>");
    }

	tsRetVal.append("<ul>");

    for (long index = plRangeCnt * lplPageRange + 1L; index < (plRangeCnt + 1L) * lplPageRange + 1L; index++)
    {
      tsRetVal.append("<li>");
      if (index == lplCurrPage)
      {
	  	tsRetVal.append("<li>");
	  	tsRetVal.append("<a href=\"javascript:goPage('").append(Long.toString(index)).append("');\" class='active'>").append(Long.toString(index)).append("</a>");
      }else{
    	  tsRetVal.append("<a href=\"javascript:goPage('").append(Long.toString(index)).append("');\">").append(Long.toString(index)).append("</a>");
      }
      tsRetVal.append("</li>");
      if (index == plPageCnt)
      {
        break;
      }
    }
    
    tsRetVal.append("</ul>");

    if (plPageCnt > (plRangeCnt + 1L) * lplPageRange)
    {
    	tsRetVal.append("<button type='button' class='next02' onclick=\"goPage('").append(Long.toString((plRangeCnt + 1L) * lplPageRange + 1L)).append("');\" ").append("><span class='blind'>다음 페이지</span></button>");
    	
    }
    else
    {
    	tsRetVal.append("<button type='button' class='next02'><span class='blind'>다음 페이지</span></button>");
    }
    	tsRetVal.append("<button type='button' class='next01' onclick=\"goPage('").append(Long.toString(plPageCnt)).append("');\"><span class='blind'>마지막 페이지로 이동</span></button>");
    
    	tsRetVal.append("</div>");
    
    return tsRetVal.toString();
    
  }
} 



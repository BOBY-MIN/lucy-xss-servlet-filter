/*
 * @(#)RequestParamCheckerTest.java $version 2014. 9. 23.
 *
 * Copyright 2007 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.naver.service.filter.requestparam;

import static org.junit.Assert.*;

import org.junit.*;

import static org.hamcrest.core.Is.*;

/**
 * RequestParamChecker 에 대한 통합 테스트
 * 
 * @author tod2
 */
public class RequestParamCheckerTest {
	RequestParamChecker checker = RequestParamChecker.getInstance();
	
	@Test
	public void testDoFilter() {
		System.out.println(checker.doFilter("/api/internal/point/*", "ment", "<b>Text</b>"));
		
		assertThat(checker.doFilter("/notExistUrl.nhn", "title", "<b>Text</b>"), is("&lt;b&gt;Text&lt;/b&gt;"));
		assertThat(checker.doFilter("/notExistUrl.nhn", "q", "<b>Text</b>"), is("<b>Text</b>"));
		assertThat(checker.doFilter("/notExistUrl.nhn", "test", "<script>alert('1');</script>"), is("&lt;script&gt;alert(&#39;1&#39;);&lt;/script&gt;"));
		
		assertThat(checker.doFilter("/tlist/list.nhn", "listId", "<b>Text</b>"), is("<b>Text</b>"));
		assertThat(checker.doFilter("/tlist/list.nhn", "mode", "<script>Text</script>"), is("&lt;script&gt;Text&lt;/script&gt;"));
		assertThat(checker.doFilter("/tlist/list.nhn", "q", "<script>Text</script>"), is("&lt;script&gt;Text&lt;/script&gt;"));
		
	}
	
	@Test
	public void testUnicodeDoFilter() {
		assertThat(checker.doFilter("/notExistUrl.nhn", "title", "<b>Text</b>"), is("&lt;b&gt;Text&lt;/b&gt;"));
		assertThat(checker.doFilter("/notExistUrl.nhn", "title", "안녕"), is("안녕"));
	}
}

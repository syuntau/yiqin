package com.yiqin.util;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Page {

	public static final int DEFAULT_PAGE_SIZE = 20;// 默认每页显示行数

	public static final int DEFAULT_PAGE_INDEX_NUM = 20;// 默认页号数目

	private int totalRowNum = 0;// 总的记录数

	private int pageSize = DEFAULT_PAGE_SIZE;// 每页显示行数

	private int currentPageIndex = 1;// 当前页

	private int totalPageNum = 1;// 总页数

	private int beginPageIndex = 1;// 起始页

	private int endPageIndex = 1;// 结束页

	private boolean hasPrev = false;// 有无上一页

	private boolean hasNext = false;// 有无下一页

	private int firstResult = 0;// 本页起始行号

	private int lastResult = 0;// 本页结束行号

	private int pageIndexNum = DEFAULT_PAGE_INDEX_NUM;// 页号数目

	private List results = new ArrayList();

	/**
	 * 
	 * @param pageSize
	 *            每页显示行数
	 * @param count
	 *            总的记录数
	 * @param pageIndex
	 *            页码
	 */
	public Page(int pageSize, int count, int pageIndex) {
		this.pageSize = pageSize;
		totalRowNum = count;
		resetTotalPageNum();
		turnToPage(pageIndex);
	}

	public void turnToPage(int pageIndex) {

		if (pageIndex < 1) {
			currentPageIndex = 1;
		} else if (pageIndex > totalPageNum) {
			currentPageIndex = totalPageNum;
		} else {
			currentPageIndex = pageIndex;
		}

		hasPrev = (currentPageIndex != 1);
		hasNext = (currentPageIndex != totalPageNum);

		beginPageIndex = Math.max(1, currentPageIndex - pageIndexNum / 2);
		endPageIndex = Math.min(currentPageIndex
				+ (pageIndexNum - pageIndexNum / 2 - 1), totalPageNum);

		firstResult = (currentPageIndex - 1) * pageSize;
		lastResult = Math.min(currentPageIndex * pageSize, totalRowNum);

	}

	public void resetTotalPageNum() {
		if (totalRowNum == 0) {
			totalPageNum = 1;
		} else {
			totalPageNum = (int) ((totalRowNum + pageSize - 1) / pageSize);
		}
		turnToPage(1);
	}

	public void search(int count) {
		totalRowNum = count;
		resetTotalPageNum();
	}

	public void changePageSize(int pageSize) {
		this.pageSize = pageSize;
		resetTotalPageNum();
	}

	public void display() {
		StringBuffer sb = new StringBuffer();
		if (hasPrev) {
			sb.append("上一页 ");
		}

		for (int i = beginPageIndex; i <= endPageIndex; i++) {
			if (i == currentPageIndex) {
				sb.append("[").append(i).append("] ");
			} else {
				sb.append(i).append(" ");
			}
		}
		if (hasNext) {
			sb.append("下一页");
		}
	}

	public Page() {
		super();
	}

	public long getTotalRowNum() {
		return totalRowNum;
	}

	public void setTotalRowNum(int totalRowNum) {
		this.totalRowNum = totalRowNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}

	public boolean isHasPrev() {
		return hasPrev;
	}

	public void setHasPrev(boolean hasPrev) {
		this.hasPrev = hasPrev;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public long getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public long getLastResult() {
		return lastResult;
	}

	public void setLastResult(int lastResult) {
		this.lastResult = lastResult;
	}

	public int getPageIndexNum() {
		return pageIndexNum;
	}

	public void setPageIndexNum(int pageIndexNum) {
		this.pageIndexNum = pageIndexNum;
	}

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}
}

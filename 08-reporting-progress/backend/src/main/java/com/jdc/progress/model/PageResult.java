package com.jdc.progress.model;

import java.util.ArrayList;
import java.util.List;

public record PageResult<T>(
		List<T> content,
		int page,
		int size,
		long total
		) {
	
	public long getTotalPages() {
		var remainance = total % size;
		return remainance == 0 ? total / size : (total / size) + 1;
	}

	public List<Integer> getPageList() {
		
		List<Integer> pages = new ArrayList<Integer>();

		if(getTotalPages() > 0) {
			pages.add(page);
		}
		
		while(pages.size() < 3 && pages.size() > 0 && pages.getFirst() > 0) {
			pages.addFirst(pages.getFirst() - 1);
		}
		
		while(pages.size() < 5 && pages.size() > 0 && pages.getLast() < getTotalPages() - 1) {
			pages.add(pages.getLast() + 1);
		}

		while(pages.size() < 5 && pages.size() > 0 && pages.getFirst() > 0) {
			pages.addFirst(pages.getFirst() - 1);
		}

		return pages;
	}
}

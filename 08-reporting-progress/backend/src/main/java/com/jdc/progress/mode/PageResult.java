package com.jdc.progress.mode;

import java.util.List;

public record PageResult<T>(
		List<T> content,
		int page,
		int size,
		long total
		) {

}

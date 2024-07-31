package com.jdc.progress.model;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

	<R> List<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc);
	
	<R> PageResult<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc, Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc, int page, int size);
}

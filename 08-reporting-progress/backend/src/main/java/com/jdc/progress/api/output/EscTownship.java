package com.jdc.progress.api.output;

import com.jdc.progress.model.entity.EscServiceTownship;

public record EscTownship(
		String code,
		String township,
		String service) {
	
	public EscTownship(EscServiceTownship entity) {
		this(entity.getCode(), entity.getTownship(), entity.getService());
	}
}

package com.wbeedvc.taf.dto.facebook;

import lombok.Data;

@Data
public class StatusDTO {
	private CurrentDTO current;

	@Data
	public class CurrentDTO {
		private Integer health;
		private String subject;
	}
}


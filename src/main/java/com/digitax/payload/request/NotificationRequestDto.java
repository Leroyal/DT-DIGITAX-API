package com.digitax.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDto {
	private String target;
    private String title;
    private String body;
}

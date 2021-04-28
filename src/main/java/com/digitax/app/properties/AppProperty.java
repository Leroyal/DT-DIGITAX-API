package com.digitax.app.properties;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.digitax.app.properties.Swagger;

@Getter
@ConfigurationProperties(prefix = "digitax")
public class AppProperty {
	public final Constant constant = new Constant();
	public final Swagger swagger = new Swagger();

}

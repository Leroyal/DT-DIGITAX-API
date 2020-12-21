package com.digitax.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecaptchaService {

	private static final String GOOGLE_RECAPTCHA_VERIFY_URL =
			"https://www.google.com/recaptcha/api/siteverify";
	@Autowired RestTemplateBuilder restTemplateBuilder;
	
	public String verifyRecaptcha(String ip, String recaptchaResponse, String recaptchaSecret ){
		System.out.println("captcha service call"+ip+"ook"+recaptchaResponse);
		Map<String, String> body = new HashMap<>();
		body.put("secret", recaptchaSecret);
		body.put("response", recaptchaResponse);
		body.put("remoteip", ip);
		System.out.println("body"+body);
		log.debug("Request body for recaptcha: {}", body);
		ResponseEntity<Map> recaptchaResponseEntity = 
				restTemplateBuilder.build()
				.getForEntity("https://www.google.com/recaptcha/api/siteverify?secret=6LdoiLwZAAAAADmQpBrZnki6eFbWJS-WaD1r1luU&response=03AGdBq27mFjAo9UCOZunBj5r0srfOWfg841LEA4Xw-v-2tqqXiWj02bHlZexFQxhVRDOaQ3L4eP6qB86cEvPCl3-0cIF33EICdTvlWyQ9Mr0zn6_EKJdZ_iOrgfs8uWyZARWRse4JOgAgSs7FfhSdKawU9gLwS512nhMq4SWc15cUyfvKznINips1-mt1_zQ310mBJWZ-tJ1DZ7NJgg-MJtGyP3kddTYgmo5W7GY0GHKHceQizwSE5EiUmaDZsd4KBYmpmYDNG_TKhuFhcyychE8bk0EECow8TtHFIMftIPyKTzF26Z45BPAMxqqx-ftdttNTiQq-QkaZDAiGAS24mRTkS6ICjYpTpKFYQ6e4LPoHvDiS6bNQYKUm1Fq4Ne2DmNM2FMUGsx7y9AoXnSmBBR_m3F3gu3zmtj5ItBhCdpbqECoHXSR-9J34Ila7UrmPgbBkPp_iX6BV" 
						, Map.class, body);

		log.debug("Response from recaptcha: {}", recaptchaResponseEntity);
		@SuppressWarnings("unchecked")
		Map<String, Object> responseBody = recaptchaResponseEntity.getBody();
		System.out.println("recaptchaResponseEntity.getBody()"+recaptchaResponseEntity.getBody());
		boolean recaptchaSucess = (Boolean)responseBody.get("success");
		if ( !recaptchaSucess) {
			System.out.println("succ");
			@SuppressWarnings("unchecked")
			List<String> errorCodes = (List)responseBody.get("error-codes");
			String errorMessage = errorCodes.stream()
					.map(s -> RecaptchaUtil.RECAPTCHA_ERROR_CODE.get(s))
					.collect(Collectors.joining(", "));
			
            System.out.println("errorMessage"+errorMessage);  
			return errorMessage;
		}else {
			System.out.println("kkk");
			return StringUtils.EMPTY;
		}
	}
}

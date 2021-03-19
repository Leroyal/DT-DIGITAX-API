package com.digitax.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.digitax.model.Relation;
import com.digitax.payload.ApiRes;

@RestController
@RequestMapping("/Myapp")
public class MEFMsiController {
	@Autowired
	private RestTemplate restTemplate;
	
private static String url="https://jsonplaceholder.typicode.com/posts";
@GetMapping("/forms")
public ResponseEntity<?> getForms()
{
	Object[] form=restTemplate.getForObject(url, Object[].class);
	
    JSONObject statusObj = new JSONObject();
    statusObj.put("status_code", 200);
    statusObj.put("message", "SUCCESS");
    return new ResponseEntity<>(ApiRes.success(form, statusObj), HttpStatus.OK);
}
}

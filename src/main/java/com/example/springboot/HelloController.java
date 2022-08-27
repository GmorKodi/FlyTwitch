package com.example.springboot;

import java.rmi.ServerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.domains.VOD;
import com.example.dtos.request.DeletedVodRequest;
import com.example.dtos.request.StreamRequest;
import com.example.dtos.response.StreamResponse;
import com.example.services.TwitchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/", produces = MediaType.ALL_VALUE)
@Slf4j 
public class HelloController {
	
	
	@Autowired
	private TwitchService twitchService;


	@GetMapping("/index")
	public String index() {
		return "Greetings from Spring Boot! this is local";
	}
	
	@GetMapping("/test")
	public String test() {
		String url = "https://twitchtracker.com/lordaethelstan/streams/41076030091";
		return testApiCall(url);
	}
	
	
	private String testApiCall(String url) {
		// TODO Auto-generated method stub
		final String uri = url;

	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);

	    System.out.println(result);
		return result;
	}

	@PostMapping(path = "vodstream", 
	        consumes = MediaType.APPLICATION_JSON_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StreamResponse> create(@RequestBody StreamRequest streamRequest) throws ServerException {
		String m3u8Url = twitchService.getStream(streamRequest.getInput());
	    StreamResponse response = new StreamResponse(m3u8Url); 
	    if (m3u8Url == null) {
	        throw new ServerException("null");
	    } else {
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }
	}
	
	@PostMapping(path = "livestream", 
	        consumes = MediaType.APPLICATION_JSON_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StreamResponse> livestream(@RequestBody StreamRequest streamRequest) throws ServerException {
		String m3u8Url = twitchService.getLive(streamRequest.getInput());
	    StreamResponse response = new StreamResponse(m3u8Url); 
	    if (m3u8Url == null) {
	        throw new ServerException("null");
	    } else {
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }
	}
	
	@PostMapping(path = "deletedvod", 
	        consumes = MediaType.APPLICATION_JSON_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StreamResponse> deletedvod(@RequestBody DeletedVodRequest streamRequest) throws ServerException {
		log.info("deletedVod endpoint recieved: {}", streamRequest);
		String m3u8Url = twitchService.getDeletedVod(streamRequest.getHtml(), streamRequest.getUrl(), streamRequest.getTimeStamp());
	    StreamResponse response = new StreamResponse(m3u8Url); 
	    if (m3u8Url == null) {
	        throw new ServerException("null");
	    } else {
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }
	}
	
	
}

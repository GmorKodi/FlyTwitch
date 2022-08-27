package com.example.dtos.response;

import lombok.Data;
import lombok.NonNull;

@Data
public class StreamResponse {

	@NonNull
	private String m3u8URL; 
}

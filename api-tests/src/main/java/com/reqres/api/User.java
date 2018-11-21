package com.reqres.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data

public class User{

	@JsonProperty("name")
	private String name;

	@JsonProperty("job")
	private String job;
}
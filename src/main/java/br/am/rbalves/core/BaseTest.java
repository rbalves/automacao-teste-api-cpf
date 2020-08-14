package br.am.rbalves.core;

import io.restassured.RestAssured;

public class BaseTest {
	public BaseTest() {
		RestAssured.baseURI = "https://backend-treinamento-rest-api-f.herokuapp.com";
		RestAssured.port = 443;
		RestAssured.basePath = "/api/v1";
	}
}

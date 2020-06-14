package br.am.rbalves.core;

import io.restassured.RestAssured;

public class BaseTest {
	public BaseTest() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8888;
		RestAssured.basePath = "/api/v1";
	}
}

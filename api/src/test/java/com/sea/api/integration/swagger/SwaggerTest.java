package com.sea.api.integration.swagger;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static io.restassured.RestAssured.*;

import com.sea.api.integration.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class SwaggerTest extends AbstractIntegrationTest{

	@Test
	void shouldDisplaySwaggerUIPage() {
		String content = given()
			.basePath("/swagger-ui/index.html")
			.port(8888)
			.when()
				.get()
			.then()
				.statusCode(200)
			.extract()
				.body()
					.asString();

		assertTrue(content.contains("Swagger UI"));
	}

}
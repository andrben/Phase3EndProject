package phase3Project;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredJava {
	RequestSpecification request;
	String baseURI;
	int NumOfEmployee;
	
	@Test
	public void getAllEmployees() {
		System.out.println("GET request is triggered");
		Response response1 = this.request.get();
		System.out.println("Response code is " + response1.statusCode());
		System.out.println("The number of employees is " + this.NumOfEmployee);
		Assert.assertEquals(response1.statusCode(), 200);
		Assert.assertEquals(this.NumOfEmployee, 3);
		System.out.println("**********End of GET request**********");
		
	}
	
	@Test
	public void getEmployeeByID(int id, int statusCode) {
		System.out.println("GET request (by ID) is triggered");
		Response response = this.request.get("/"+id);
		
		if(statusCode==400) {
			System.out.println("Response code is " + response.statusCode());
			Assert.assertEquals(response.statusCode(), statusCode);
		} else if(statusCode==200) {
			System.out.println("Response code is " + response.statusCode());
			Assert.assertEquals(response.statusCode(), statusCode);
			String body = response.getBody().asString();
			JsonPath json = response.jsonPath();
			System.out.println("The name is " + json.get("firstName"));
			Assert.assertTrue(body.contains("Tom"));
		}
		System.out.println("**********End of GET request (by ID)**********");
		
	}
	
	
	@Test
	public int createNewEmployee(String firstName, String lastName, String email, String salary) {
		System.out.println("POST request is triggered");
		Response response2 = this.request.contentType(ContentType.JSON).accept(ContentType.JSON).body("{\r\n"
				+ "    \"firstName\": \""+firstName+"\",\r\n"
				+ "    \"lastName\": \""+lastName+"\",\r\n"
				+ "    \"salary\": \""+salary+"\",\r\n"
				+ "    \"email\": \""+email+"\"\r\n"
				+ "}").post();
		
//		System.out.println("{\r\n"
//				+ "    \"firstName\": \""+firstName+"\",\r\n"
//				+ "    \"lastName\": \""+lastName+"\",\r\n"
//				+ "    \"salary\": \""+salary+"\",\r\n"
//				+ "    \"email\": \""+email+"\"\r\n"
//				+ "}");
		//System.out.println("Response body is " + response.jsonPath());
		//System.out.println("The newly created ID number is: "+body.);
		System.out.println("Response is " + response2.getBody().asString());
		System.out.println("Response code is " + response2.statusCode());
		JsonPath json = response2.jsonPath();
		int id = json.get("id");
		System.out.println("The ID generted is " + id);
		System.out.println("The name is " + json.get("firstName"));
		Assert.assertEquals(json.get("firstName"), firstName);
		this.NumOfEmployee = this.NumOfEmployee + 1;
		Assert.assertEquals(this.NumOfEmployee, 4);
		System.out.println("The number of employees is " + this.NumOfEmployee);
		System.out.println("**********End of POST request**********");
		return id;
		
	}
	
	
	@Test
	public void updateEmployee(int id, String firstName, String lastName) {
		System.out.println("PUT request is triggered");
		Response response = this.request.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body("{\r\n"
						+ "    \"firstName\":\""+firstName+"\",\r\n"
						+ "    \"lastName\":\""+lastName+"\"\r\n"
						+ "}")
				.put("/"+id);
		
		String body = response.getBody().asString();
		System.out.println("Response body is " + body);
		JsonPath json = response.jsonPath();
		System.out.println("Response code is " + response.statusCode());
		Assert.assertEquals(response.statusCode(), 200);
		System.out.println("The updated first name is " + json.get("firstName"));
		Assert.assertEquals(json.get("firstName"), firstName);
		Assert.assertFalse(body.contains("John"));
		if(!body.contains("John")) {
			System.out.println("The name John cannot be found");
		}
		System.out.println("**********End of PUT request**********");
	}
	
	
	@Test
	public void deleteEmployeeByID(int id) {
		System.out.println("DELETE request is triggered");
		Response response = this.request.delete("/"+id);
			System.out.println("Response code is " + response.statusCode());
			Assert.assertEquals(response.statusCode(), 200);
			String body = response.getBody().asString();
			Assert.assertTrue(!body.contains("Tom"));
			if(!body.contains("Tom")) {
				System.out.println("The name Tom cannot be found");
			}
			this.NumOfEmployee = this.NumOfEmployee - 1;
		
		System.out.println("**********End of DELETE request**********");
		
	}
	
	public RestAssuredJava(String URI) {
		System.out.println("Constructor is triggered");
		this.baseURI=URI;
		RestAssured.baseURI = baseURI;
		this.request = RestAssured.given();		
		Response response = this.request.get();
		JsonPath json = response.jsonPath();
		ArrayList<String> names = new ArrayList<String>(); 
		names = json.get("name");
		this.NumOfEmployee = names.size();
		System.out.println("**********End of Constructor**********");
	}
	

}

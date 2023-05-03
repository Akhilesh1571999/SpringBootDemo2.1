package com.project.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import com.project.entity.Employee;
import com.project.repository.EmployeeRepository;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:/application-junit.properties")
public class EmployeeControllerTest {
	
	@LocalServerPort
	private int port;
	
	private RequestSpecification requestSpecification;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@BeforeEach
	public void setup() {
		employeeRepository.deleteAll();
	}
	
	@PostConstruct
	public void initRequestSpecification() {
		final RequestSpecBuilder tempSpec = new RequestSpecBuilder();
		requestSpecification = tempSpec.setBaseUri("http://localhost:" + port + "/emp").build();
	}
		@Test
		public void testAddEmp() {
			//given
			Employee employee = new Employee();
			employee.setCity("Belgaum");
			employee.setName("Aniket");
			
			//when
			ValidatableResponse result = RestAssured.given(requestSpecification).contentType("application/json")
			.accept("application/json").body(employee).post("/save").then();
			
			//then
			Employee empResult = result.extract().as(Employee.class);
			assertNotNull(empResult);
			assertEquals("Aniket",empResult.getName());
		}
		
		@Test
		public void testGetEmpById() {
			//given
			Employee employee = new Employee();
			employee.setCity("Belgaum");
			employee.setName("Aniket");
			
			employeeRepository.save(employee);
			assertEquals(1,employeeRepository.count() );
			
			//when
			ValidatableResponse result = RestAssured.given(requestSpecification).get("/1").then();
			
			//then
			Employee as = result.extract().as(Employee.class);
			assertNotNull(as);
		}
		@Test
		public void testGetAll() {
			//given
			Employee employee1 = new Employee();
			employee1.setCity("Belgaum");
			employee1.setName("Aniket");
			
			Employee employee2 = new Employee();
			employee2.setCity("Sangli");
			employee2.setName("Ajinkya");
			
			List<Employee> list = new ArrayList<Employee>();
			list.add(employee1);
			list.add(employee2);
			
			employeeRepository.saveAll(list);
			assertEquals(2, employeeRepository.count());
			
			//when
			ValidatableResponse response = RestAssured.given(requestSpecification).get("/allEmp").then();
			
			//then
			Employee[] as = response.extract().as(Employee[].class);
			assertNotNull(as);
			assertEquals(2,as.length);
			
		}
		@Test
		public void testUpdateEmpById() {
			//given
			Employee employee1 = new Employee();
			employee1.setCity("Belgaum");
			employee1.setName("Aniket");
			
			Employee employee2 = new Employee();
			employee2.setCity("Sangli");
			employee2.setName("Ajinkya");
			
			List<Employee> list = new ArrayList<Employee>();
			list.add(employee1);
			list.add(employee2);
			
			employeeRepository.saveAll(list);
			assertEquals(2, employeeRepository.count());
			
			//when
			Employee employee3 = new Employee();
			employee3.setId(1);
			employee3.setCity("Pune");
			employee3.setName("Akhilesh");
			String response = RestAssured.given(requestSpecification).body(employee3)
					 .put("/update").then().extract().asString();
			 
			
			//then
			assertNotNull(response);
			boolean contains = response.contains("1");
			assertTrue(contains);
			
		}
		@Test
		public void testDeleteEmployeeById() {
			//given
			Employee employee1 = new Employee();
			employee1.setCity("Belgaum");
			employee1.setName("Aniket");
		
			employeeRepository.save(employee1);
			assertEquals(1, employeeRepository.count());
			
			//when
			String response = RestAssured.given(requestSpecification).delete("/delete/1").then().extract().asString();
			
			//then
			assertNotNull(response);
			assertEquals("Employee deleted", response);
			
		}
		@Test
		public void testGetAllEmployeeByPage() {
			//given
			Employee employee1 = new Employee();
			employee1.setCity("Belgaum");
			employee1.setName("Aniket");
			
			Employee employee2 = new Employee();
			employee2.setCity("Sangli");
			employee2.setName("Ajinkya");
			
			Employee employee3 = new Employee();
			employee3.setCity("Pune");
			employee3.setName("Akhilesh");
			
			Employee employee4 = new Employee();
			employee4.setCity("Mozari");
			employee4.setName("Akanksha");
			
			List<Employee> list = new ArrayList<Employee>();
			list.add(employee1);
			list.add(employee2);
			list.add(employee3);
			list.add(employee4);
			
			employeeRepository.saveAll(list);
			assertEquals(4, employeeRepository.count());
			
			//when
			ValidatableResponse response = RestAssured.given(requestSpecification).get("/allEmp/0").then();
			
			//then
			Employee[] result = response.extract().as(Employee[].class);
			assertNotNull(result);
			assertEquals(2,result.length);
		}
		@Test
		public void testGetAllEmployeeBySorting() {
			//given
			Employee employee1 = new Employee();
			employee1.setCity("Belgaum");
			employee1.setName("Aniket");
			
			Employee employee2 = new Employee();
			employee2.setCity("Sangli");
			employee2.setName("Ajinkya");
			
			Employee employee3 = new Employee();
			employee3.setCity("Pune");
			employee3.setName("Akhilesh");
			
			Employee employee4 = new Employee();
			employee4.setCity("Mozari");
			employee4.setName("Akanksha");
			
			List<Employee> list = new ArrayList<Employee>();
			list.add(employee1);
			list.add(employee2);
			list.add(employee3);
			list.add(employee4);
			
			employeeRepository.saveAll(list);
			assertEquals(4, employeeRepository.count());
			
			//when
			ValidatableResponse response = RestAssured.given(requestSpecification).get("/allEmpSort").then();
			
			//then
			Employee[] as1 = response.extract().as(Employee[].class);
			assertNotNull(as1);
			assertEquals(4, as1.length);
				
		}
		@Test
		public void testGetEmpByCityAndName() {
			//given
			Employee employee1 = new Employee();
			employee1.setCity("Belgaum");
			employee1.setName("Aniket");
			
			Employee employee2 = new Employee();
			employee2.setCity("Sangli");
			employee2.setName("Ajinkya");
			
			List<Employee> list = new ArrayList<Employee>();
			list.add(employee1);
			list.add(employee2);
			
			employeeRepository.saveAll(list);
			assertEquals(2, employeeRepository.count());
			
			//when
			ValidatableResponse response = RestAssured.given(requestSpecification).get("/Belgaum/Aniket").then();
			
			//then
			Employee[] as = response.extract().as(Employee[].class);
			assertNotNull(as);
	
		}
		@Test
		public void testGetById() {
			//given
			Employee employee = new Employee();
			employee.setCity("Dharwad");
			employee.setName("Nikhil");
			
			employeeRepository.save(employee);
			assertEquals(1, employeeRepository.count());
			
			//when
			ValidatableResponse response = RestAssured.given(requestSpecification)
					.queryParam("empcode", 1).get("/request").then();
			
			//then
			Employee as = response.extract().as(Employee.class);
			assertNotNull(as);
			
			
		}

}

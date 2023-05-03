 package com.project.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.project.entity.Employee;
import com.project.repository.EmployeeRepository;

@SpringBootTest
@TestPropertySource("classpath:/application-junit.properties")
public class EmployeeServiceImplTest {
	
	@Autowired
	private EmployeeServiceImpl employeeServiceImpl;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@BeforeEach
	public void setup() {
		employeeRepository.deleteAll();
	}
	
	@Test
	public void testSaveEmployee() {
		//given
		Employee employee = new Employee();
		employee.setCity("Belgaum");
		employee.setName("Aniket");
		//when
		Employee result = employeeServiceImpl.saveEmployee(employee);
		//then
		assertNotNull(result);
		assertEquals("Aniket", result.getName());
	}
	@Test
	public void testGetAllEmployee() {
		
		//given
		Employee employee1 = new Employee();
		employee1.setCity("Belgaum");
		employee1.setName("Aniket");
		
		Employee employee2 = new Employee();
		employee2.setCity("sangli");
		employee2.setName("Ajinkya");
		
		List<Employee> list = new ArrayList<Employee>();
		list.add(employee1);
		list.add(employee2);
		
		List<Employee> saved = employeeRepository.saveAll(list);
		assertEquals(2,saved.size() );
		//when
		List<Employee> result = employeeServiceImpl.getAllEmployee();
		//then
		assertNotNull(result);
		assertEquals(2, result.size());
	}
	@Test
	public void testGetById() {
		//given
		Employee employee1 = new Employee();
		employee1.setCity("Belgaum");
		employee1.setName("Aniket");
		
		Employee save = employeeRepository.save(employee1);
		//when
		int id = save.getId();
		Employee byId = employeeServiceImpl.getById(id);
		//then
		assertNotNull(byId);
		assertEquals("Aniket", byId.getName());
	}
	@Test
	public void testDeleteById() {
		//given
		Employee employee1 = new Employee();
		employee1.setCity("Sangli");
		employee1.setName("Ajinkya");
		
		Employee save = employeeRepository.save(employee1);
		//when
		int id = save.getId();
		 String deleteById = employeeServiceImpl.deleteById(id);
		 //then
		 boolean existsById = employeeRepository.existsById(id);
		 assertFalse(existsById);
		
	}
	@Test
	public void testUpdateById() {
		//given
		Employee employee1 = new Employee();
		employee1.setCity("Sangli");
		employee1.setName("Ajinkya");
		
		Employee save = employeeRepository.save(employee1);
		
		//when
		Employee employee3 = new Employee();
		employee3.setId(1);
		employee3.setCity("Belgaum");
		employee3.setName("Akhilesh");
		
		int updateById = employeeServiceImpl.updateById(1, employee3);
		
		//then
		assertNotNull(updateById);
//		assertEquals("Akhilesh",employee3.getName());
		assertEquals(1, employee3.getId());
	}
	@Test
	public void testGetAllEmployeeByPage() {
		//given
		Employee employee1 = new Employee();
		employee1.setCity("Belgaum");
		employee1.setName("Aniket");
				
		Employee employee2 = new Employee();
		employee2.setCity("sangli");
		employee2.setName("Ajinkya");
				
		Employee employee3 = new Employee();
		employee3.setCity("Belgaum");
		employee3.setName("Akhilesh");
				
		Employee employee4 = new Employee();
		employee4.setCity("Mozari");
		employee4.setName("Akanksha");
				
		List<Employee> list = new ArrayList<Employee>();
		list.add(employee1);
		list.add(employee2);
		list.add(employee3);
		list.add(employee4);
	
		List<Employee> saved = employeeRepository.saveAll(list);
		assertEquals(4,saved.size() );
		//when
		List<Employee> allEmployeeByPage = employeeServiceImpl.getAllEmployeeByPage(0, 2);
		//then
		assertNotNull(allEmployeeByPage);
		assertEquals(2, allEmployeeByPage.size());
	}
	@Test
	public void testGetAllEmployeeBySorting() {
		//given
		Employee employee1 = new Employee();
		employee1.setCity("Belgaum");
		employee1.setName("Aniket");
				
		Employee employee2 = new Employee();
		employee2.setCity("sangli");
		employee2.setName("Ajinkya");
				
		Employee employee3 = new Employee();
		employee3.setCity("Agra");
		employee3.setName("Akhilesh");
				
		Employee employee4 = new Employee();
		employee4.setCity("Mozari");
		employee4.setName("Akanksha");
				
		List<Employee> list = new ArrayList<Employee>();
		list.add(employee1);
		list.add(employee2);
		list.add(employee3);
		list.add(employee4);

				
		List<Employee> saved = employeeRepository.saveAll(list);
		assertEquals(4,saved.size() );
		//when
		List<Employee> allEmployeeBySorting = employeeServiceImpl.getAllEmployeeBySorting();
		//then
		assertNotNull(allEmployeeBySorting);
		assertEquals(4, allEmployeeBySorting.size());
	}
}

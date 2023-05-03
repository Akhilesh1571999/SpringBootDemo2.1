package com.project.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.Employee;
import com.project.repository.EmployeeRepository;
import com.project.service.EmployeeService;

@RestController
@RequestMapping ("/emp")
public class EmployeeController {
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeRepository repository ;
	
	@PostMapping (path="/save",consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces= {MediaType.APPLICATION_JSON_VALUE })
	public Employee addEmp(@RequestBody Employee employee) {
		log.info("start:EmployeeController --> addEmp");
		Employee saveEmployee = employeeService.saveEmployee(employee);
		log.info("end:EmployeeController --> addEmp:Employee added {}",saveEmployee);

		return saveEmployee;
	}
	
	@GetMapping ("/allEmp")
	public ResponseEntity<List<Employee>>  getAll(){
		log.info("start:EmployeeController --> getAll");
		List<Employee> allEmployee = employeeService.getAllEmployee();
		log.info("End:EmployeeController --> getAll");
		return new ResponseEntity<List<Employee>>(allEmployee, HttpStatus.ACCEPTED) ;
	}
	@GetMapping (path="/{id}")
	public ResponseEntity<Employee> getEmpById(@PathVariable int id) {
		log.info("start:EmployeeController --> getEmpById {}",id);
		Employee byId = employeeService.getById(id);
		log.info("End:EmployeeController --> getEmpById {}" ,id);
		return new ResponseEntity<Employee>(byId, HttpStatus.OK) ;	
	}
	@GetMapping ("/request")
	public Employee getById (@RequestParam("empcode") int id) {
		log.info("start:EmployeeController --> getById {}",id);
		Employee id2 = employeeService.getById(id);
		log.info("End:EmployeeController --> getById {}",id);
		return id2;
	}
	
	@PutMapping ("/update")
	public String updateEmpById ( @RequestBody Employee employee) {
		log.info("start:EmployeeController --> updateEmpById");
		int updateById = employeeService.updateById(employee.getId(),employee);
		if(updateById == 0) {
			log.error("End:EmployeeController --> updateEmpById : Employee not found");
			return "Employee not found";
		}
		log.info("End:EmployeeController --> updateEmpById : Employee updated successfully {}",employee);
		return "Employee updated successfully";
	}
	@DeleteMapping ("/delete/{id}")
	public String deleteEmployeeById(@PathVariable int id) {
		log.info("start:EmployeeController --> deleteEmployeeById {}",id);
		String deleteById = employeeService.deleteById(id);
		log.info("End:EmployeeController --> deleteEmployeeById : Employee deleted successfully {}",id);
		return deleteById;
	}
	
	// by page
	
	@GetMapping ("/allEmp/{page}")
	public List<Employee> getAllEmployeeByPage(@PathVariable int page){
		log.info("start:EmployeeController --> getAllEmployeeByPage {}",page);
		List<Employee> allEmployeeByPage = employeeService.getAllEmployeeByPage(page , 2);
		log.info("End:EmployeeController --> getAllEmployeeByPage : Successfully fetched {}",page);
		return allEmployeeByPage ;
	}
	
	// sorting
	@GetMapping ("/allEmpSort")
	public List<Employee> getAllEmployeeBySorting(){
		log.info("start:EmployeeController --> getAllEmployeeBySorting ");
		List<Employee> allEmployeeBySort = employeeService.getAllEmployeeBySorting();
		log.info("End:EmployeeController --> getAllEmployeeBySorting :Successfully fetched ");
		return allEmployeeBySort ;
	}
	
	//findBycity
	@GetMapping ("/all/{city}")
	public List<Employee> getEmpByCity (@PathVariable String city) {
		log.info("start:EmployeeController --> getEmpByCity {} ",city);
		List<Employee> findByCity = repository.findByCity(city);
		log.info("End:EmployeeController --> getEmpByCity {} ",findByCity);
		return findByCity;
	}
	
	//findByCityAndName
	@GetMapping ("/{city}/{name}")
	public List<Employee> getEmpByCityAndName (@PathVariable String city , @PathVariable String name) {
		log.info("start:EmployeeController --> getEmpByCityAndName {} ",city,name);
		List<Employee> findByCityAndName = repository.findByCityAndName(city, name);
		log.info("End:EmployeeController --> getEmpByCityAndName {} ",findByCityAndName);
		return findByCityAndName;
	}
	
	@GetMapping (path = "/test/{city}/{name}")
	public Employee test (@PathVariable String city , @PathVariable String name) {
		log.info("start:EmployeeController --> test {} ",city,name);
		Employee getdata = repository.getdata(city, name);
		log.info("End:EmployeeController --> test {} ",getdata);
		return getdata;
	}
	
	
}

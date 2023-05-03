package com.project.service;

import java.util.List;

import com.project.entity.Employee;

public interface EmployeeService {
	
	List<Employee> getAllEmployee();
	
	Employee getById(int id);
	
	int updateById(int id ,Employee employee);
	
	String deleteById(int id);
	
	Employee saveEmployee (Employee employee);
	
	List<Employee> getAllEmployeeByPage(int page , int size);
	
	List<Employee> getAllEmployeeBySorting();
	

}

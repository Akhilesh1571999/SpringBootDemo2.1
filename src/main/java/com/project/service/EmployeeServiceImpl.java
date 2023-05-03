package com.project.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.entity.Employee;
import com.project.exception.NoSuchElementFoundException;
import com.project.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private static final Logger log =LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> getAllEmployee() {
		log.info("start: EmployeeServiceImpl --> getAllEmployee");
		Iterable <Employee> findAll = employeeRepository.findAll();
		log.info("End: EmployeeServiceImpl --> getAllEmployee");
		return (List<Employee>) findAll;
	}

	@Override
	public Employee getById(int id) {
		log.info("start: EmployeeServiceImpl --> getById {}" ,id);
		log.info("End: EmployeeServiceImpl --> getById {}" ,id);
		return employeeRepository.findById(id).orElseThrow
				(()-> new NoSuchElementFoundException("employee not found"));
		
		
	}

	@Override
	public String deleteById(int id) {
		log.info("start: EmployeeServiceImpl --> deleteById {}" ,id);
		employeeRepository.deleteById(id);
		log.info("End: EmployeeServiceImpl --> deleteById {}" ,id);
		return "Employee deleted";
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		log.info("start: EmployeeServiceImpl --> saveEmployee");
		Employee employee2 = employeeRepository.save(employee);
		log.info("end: EmployeeServiceImpl --> saveEmployee : Employee saved {}",employee2);

		return employee2;
	}

	@Override
	public int updateById(int id, Employee employee) {
		log.info("start: EmployeeServiceImpl --> updateById");
		Optional<Employee> optional = employeeRepository.findById(id);
		if(optional.isPresent()) {
			Employee save = employeeRepository.save(employee);
			log.info("End: EmployeeServiceImpl --> updateById");
			return save.getId();
		}
		log.error("End: EmployeeServiceImpl --> updateById :Id not found {}" ,id);
		return 0;
	}

	@Override
	public List<Employee> getAllEmployeeByPage(int page, int size) {
		log.info("start: EmployeeServiceImpl --> getAllEmployeeByPage {}" ,page ,size);
		PageRequest request = PageRequest.of(page, size);
		Page<Employee> findAll = employeeRepository.findAll(request);
		List<Employee> content = findAll.getContent();
		log.info("End: EmployeeServiceImpl --> getAllEmployeeByPage {}" ,content);
		return content;
	}

	@Override
	public List<Employee> getAllEmployeeBySorting() {
		log.info("start: EmployeeServiceImpl --> getAllEmployeeBySorting");
		List<Employee> list = employeeRepository.findAll(Sort.by("city").ascending());
		log.info("End: EmployeeServiceImpl --> getAllEmployeeBySorting");
		return list;
	}

}

package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Service
@Transactional
public class EmployeeService {

	@Autowired EmployeeRepository employeeRepository;

	public ObservableList<Employee> getAll() {
		List<Employee> employees = employeeRepository.findAll();
		return employees.stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
	}

	public void deleteEmployee(Employee employee) {
		employeeRepository.delete(employee);
	}

	public Employee updateEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
}

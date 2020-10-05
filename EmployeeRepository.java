package com.JpaEx1_example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JpaEx1_example.demo.model.EmployeeRyth;



public interface EmployeeRepository extends JpaRepository<EmployeeRyth, Integer>{
	List<EmployeeRyth> findByFirstNameAndLastName(String firstName,String lastName);

}

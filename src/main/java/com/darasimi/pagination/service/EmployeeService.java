package com.darasimi.pagination.service;

import com.darasimi.pagination.entity.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    Page<Employee> getEmployee(String name, int page, int size);
}

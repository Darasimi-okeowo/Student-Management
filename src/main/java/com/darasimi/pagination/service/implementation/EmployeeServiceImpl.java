package com.darasimi.pagination.service.implementation;

import com.darasimi.pagination.entity.Employee;
import com.darasimi.pagination.repository.EmployeeRepository;
import com.darasimi.pagination.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    @Override
    public Page<Employee> getEmployee(String name, int page, int size) {
        log.info("Fetching users for page {} of size {}", page, size);
        return employeeRepository.findByNameContaining(name, PageRequest.of(page, size));
    }
}

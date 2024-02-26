package com.darasimi.pagination.repository;

import com.darasimi.pagination.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    Page<Employee> findByNameContaining(String name, Pageable pageable);
}

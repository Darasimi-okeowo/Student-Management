package com.darasimi.pagination.repository;

import com.darasimi.pagination.entity.Employee;
import com.darasimi.pagination.entity.Student;
import com.darasimi.pagination.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    void deleteStudentById(Long id);

    Optional<Student> findStudentById(Long id);

    Optional<Student> findOneByEmailAndPassword(String email, String password);
    Student findByEmail(String email);
}

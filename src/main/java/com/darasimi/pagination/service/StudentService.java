package com.darasimi.pagination.service;

import com.darasimi.pagination.domain.LoginResponse;
import com.darasimi.pagination.entity.Employee;
import com.darasimi.pagination.entity.Student;
import com.darasimi.pagination.exception.UserNotFoundException;
import com.darasimi.pagination.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.hibernate.service.UnknownServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.regex.Pattern.matches;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public LoginResponse loginEmployee(Student student) {
        String msg = "";
        Student student1 = studentRepository.findByEmail(student.getEmail());
        if (student1 != null) {
            String password = student.getPassword();
            String encodedPassword = student1.getPassword();
            Boolean isPwdRight = matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<Student> employee = studentRepository.findOneByEmailAndPassword(student.getEmail(), encodedPassword);
                if (employee.isPresent()) {
                    return new LoginResponse("Login Success", true);
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                return new LoginResponse("Password Not Match", false);
            }
        } else {
            return new LoginResponse("Email not exists", false);
        }
    }

    public Student addStudent(Student student){
        student.setStudentCode(UUID.randomUUID().toString());
        student.setPassword("password");
        return studentRepository.save(student);
    }

    public List<Student> findAllStudents(){
        return studentRepository.findAll();
    }

    public Student updateStudent(Student student){
        student.setPassword("password");
        return studentRepository.save(student);
    }

    public Student findStudentById(Long id){
        return studentRepository.findStudentById(id).orElseThrow(() -> new UserNotFoundException("User by id " + id + "was not found"));
    }

    @Transactional
    public void deleteStudent(Long id){
        studentRepository.deleteStudentById(id);
    }
}

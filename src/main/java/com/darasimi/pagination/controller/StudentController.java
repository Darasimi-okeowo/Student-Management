package com.darasimi.pagination.controller;

import com.darasimi.pagination.dto.UserDto;
import com.darasimi.pagination.entity.Student;
import com.darasimi.pagination.entity.User;
import com.darasimi.pagination.repository.StudentRepository;
import com.darasimi.pagination.repository.UserRepository;
import com.darasimi.pagination.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> students = studentService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id){
        Student student = studentService.findStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        String fullName = student.getFirstName() + " " + student.getLastName();
        student.setFullName(fullName);
        Student newStudent = studentService.addStudent(student);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        String fullName = student.getFirstName() + " " + student.getLastName();
        student.setFullName(fullName);
        Student updateStudent = studentService.updateStudent(student);
        return new ResponseEntity<>(updateStudent, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Student> deleteStudentById(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody Student student, BindingResult result){
        Student student1 = studentRepository.findByEmail(student.getEmail());
        if(student1.getPassword().equals(student.getPassword()))
            return ResponseEntity.ok(student1);
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }
}

package com.darasimi.pagination.controller;

import com.darasimi.pagination.domain.HttpResponse;
import com.darasimi.pagination.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/employee")
    public ResponseEntity<HttpResponse>getUsers(@RequestParam Optional<String> name,
                                                @RequestParam Optional<Integer> page,
                                                @RequestParam Optional<Integer> size){
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(Map.of("page", employeeService.getEmployee(name.orElse(""), page.orElse(0), size.orElse(10))))
                        .message("Employee Retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );

    }
}

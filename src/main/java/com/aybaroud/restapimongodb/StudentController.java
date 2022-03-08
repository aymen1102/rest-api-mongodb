package com.aybaroud.restapimongodb;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class StudentController {

    @Autowired
    private final StudentService studentService;

    @GetMapping
    @RequestMapping("/getAllStudents")
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @PostMapping
    @RequestMapping("/getStudentById/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") String studentId){
        Optional<Student> studentById = studentService.getStudentById(studentId);
        if(studentById.isPresent()){
            return  ResponseEntity.ok(studentById.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    @RequestMapping("/addStudent")
    public ResponseEntity addStudent(@RequestBody Student student){
        studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @RequestMapping("/updateStudentEmail/{id}")
    public ResponseEntity<Student> updateStudentEmail(@PathVariable("id") String studentId,
                                                      @RequestBody String newEmail){
        Optional<Student> student = studentService.updateStudentEmail(studentId, newEmail);
        if(student.isPresent()){
            return new ResponseEntity<>(
                    studentService.addStudent(student.get()),
                    HttpStatus.OK
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @DeleteMapping
    @RequestMapping("/deleteById/{id}")
    public ResponseEntity deleteById(@PathVariable("id")  String studentId){
        studentService.deleteById(studentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

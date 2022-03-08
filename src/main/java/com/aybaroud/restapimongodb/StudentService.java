package com.aybaroud.restapimongodb;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(String studentId){
        return studentRepository.findById(studentId);
    }

    public Student addStudent(Student student){
        return studentRepository.save(student);
    }

    public Optional<Student> updateStudentEmail(String studentId, String newEmail){
        Optional<Student> studentById = studentRepository.findById(studentId);
        Optional<Student> updatedStudent = studentById;
        if(studentById.isPresent()){
            updatedStudent.get().setEmail(newEmail);
        }
        return updatedStudent;
    }

    public void deleteById(String studentId){
        studentRepository.deleteById(studentId);
    }

}

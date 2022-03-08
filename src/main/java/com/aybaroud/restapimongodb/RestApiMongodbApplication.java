package com.aybaroud.restapimongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class RestApiMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiMongodbApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository studentRepository,
							 MongoTemplate mongoTemplate){
		return  args -> {
			Address address = new Address(
					"France",
					"Paris",
					"75008");
			String email = "lausmet@gmail.com";
			Student student = new Student(
					"Laura",
					"Smet",
					email,
					Gender.FEMALE,
					address,
					List.of("Computer Science","Maths"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);


//			usingMongoTemplateAndQuery(
//					 studentRepository,
//					 mongoTemplate,
//					 email,
//					 student);
			studentRepository.findStudentByEmail(email).ifPresentOrElse(
					s -> {
						System.out.println(student + " already exists");
					}, () ->{
						System.out.println("Inserting student "+ student);
						studentRepository.insert(student);
					}
			);
		};
	}

	private void usingMongoTemplateAndQuery(
			StudentRepository studentRepository,
			MongoTemplate mongoTemplate,
			String email,
			Student student)
			{
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		List<Student> students = mongoTemplate.find(query, Student.class);
		if(students.size() > 1){
			throw  new IllegalArgumentException("found many students with email" + email);
		}
		if(students.isEmpty()){
			System.out.println("Inserting student "+student);
			studentRepository.insert(student);
		} else {
			System.out.println(student+" already exists");
		}
	}
}

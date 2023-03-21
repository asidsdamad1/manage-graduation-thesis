package utc.edu.thesis.domain.dto;

import lombok.Data;
import utc.edu.thesis.domain.entity.Class;

import java.time.LocalDate;

@Data
public class StudentDto {
    private Long id;
    private String fullName;
    private LocalDate dob;
    private String gender;
    private String address;
    private String phone;
    private String email;
    private Class aClass;
}

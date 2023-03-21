package utc.edu.thesis.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TeacherDto {
    private Long id;
    private String fullName;
    private LocalDate dob;
    private String gender;
    private String address;
    private String phone;
    private String email;
}

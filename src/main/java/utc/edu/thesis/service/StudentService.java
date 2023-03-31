package utc.edu.thesis.service;

import org.springframework.stereotype.Service;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.dto.StudentDto;

import java.util.List;

@Service
public interface StudentService {
    StudentDto addStudent(StudentDto dto);

    Boolean deleteStudent(Long id);

    StudentDto editStudent(StudentDto dto);

    List<StudentDto> getStudent(SearchDto dto);

    StudentDto findByCode(String code);
}

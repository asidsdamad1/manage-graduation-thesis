package utc.edu.thesis.service.teacher.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.dto.StudentDto;
import utc.edu.thesis.domain.entity.Assignment;
import utc.edu.thesis.repository.AssignmentRepository;
import utc.edu.thesis.service.StudentService;
import utc.edu.thesis.service.teacher.ManagerStudentService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerStudentServiceImpl implements ManagerStudentService {
    private final AssignmentRepository assignmentRepository;
    private final StudentService studentService;

    @Override
    public List<StudentDto> getStudent(SearchDto searchDto) {
        if (searchDto != null) {
            List<StudentDto> res = new ArrayList<>();
            List<Assignment> assignments = assignmentRepository.getStudentByTeacher(searchDto.getId());
            assignments.forEach(assignment -> {
                studentService.getStudent(searchDto).forEach(studentDto -> {
                    if (studentDto.getId() == assignment.getStudent().getId()) {
                        res.add(studentDto);
                    }
                });
            });
            return res;
        }

        return null;
    }
}

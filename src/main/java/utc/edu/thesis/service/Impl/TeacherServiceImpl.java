package utc.edu.thesis.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utc.edu.thesis.domain.dto.TeacherDto;
import utc.edu.thesis.domain.entity.Teacher;
import utc.edu.thesis.exception.request.BadRequestException;
import utc.edu.thesis.repository.TeacherRepository;
import utc.edu.thesis.service.TeacherService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    @Override
    public List<Teacher> getAll() {
        return teacherRepository.getAll();
    }

    @Override
    public TeacherDto getById(Long id) {
        return teacherRepository.findById(id)
                .map(TeacherDto::of)
                .orElseThrow(() -> {
                    throw new BadRequestException("Can't not find teacher by Id: %d".formatted(id));
                });
    }

    @Override
    public TeacherDto addTeacher(TeacherDto dto) {
        if(dto != null) {
            Teacher teacher = Teacher.builder()
                    .fullName(dto.getFullName())
                    .dob(dto.getDob())
                    .gender(dto.getGender())
                    .email(dto.getEmail())
                    .address(dto.getAddress())
                    .phone(dto.getPhone())
                    .build();

            teacherRepository.save(teacher);

            return TeacherDto.of(teacher);
        }
        return null;
    }


}

package utc.edu.thesis.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utc.edu.thesis.domain.entity.Teacher;
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
}

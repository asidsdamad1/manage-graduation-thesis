package utc.edu.thesis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc.edu.thesis.domain.dto.TeacherDto;
import utc.edu.thesis.domain.entity.Teacher;
import utc.edu.thesis.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping("/get-list-teacher")
    public ResponseEntity<List<Teacher>> getAll(@RequestBody String payload) {
        return ResponseEntity.ok(teacherService.getAll());
    }

    @PostMapping("/get-teacher-by-id")
    public ResponseEntity<TeacherDto> getById(@RequestBody TeacherDto dto) {
        return ResponseEntity.ok(teacherService.getById(dto.getId()));
    }

    @PostMapping("/add-teacher")
    public ResponseEntity<TeacherDto> addTeacher(@RequestBody TeacherDto dto) {
        return ResponseEntity.ok(teacherService.addTeacher(dto));
    }
}

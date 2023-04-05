package utc.edu.thesis.controller.assign;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc.edu.thesis.domain.dto.AssignmentDto;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.dto.SessionDto;
import utc.edu.thesis.domain.dto.TeacherDto;
import utc.edu.thesis.service.AssignmentService;
import utc.edu.thesis.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/assignment")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping("/get-assignment")
    public ResponseEntity<List<AssignmentDto>> getBySession(@RequestBody SearchDto dto) {
        List<AssignmentDto> assignments = assignmentService.getAssign(dto);
        return ResponseEntity.ok(assignments);
    }

    @PostMapping("/get-student/{sessionId}/{teacherId}")
    public ResponseEntity<List<AssignmentDto>> getBySession(@PathVariable Long sessionId, @PathVariable Long teacherId) {
        return ResponseEntity.ok(assignmentService.getStudent(sessionId, teacherId));
    }
}

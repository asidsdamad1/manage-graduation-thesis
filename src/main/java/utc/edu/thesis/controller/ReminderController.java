package utc.edu.thesis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utc.edu.thesis.domain.dto.ProjectDto;
import utc.edu.thesis.domain.dto.ReminderDto;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.service.ReminderService;

import java.util.List;

@RestController
@RequestMapping("/reminder")
@RequiredArgsConstructor
public class ReminderController {
    private final ReminderService reminderService;

    @PostMapping("/get-reminder")
    public ResponseEntity<List<ReminderDto>> getReminder(@RequestBody SearchDto searchDto) {
        return ResponseEntity.ok(reminderService.getReminder(searchDto));
    }

    @PostMapping("/add-reminder")
    public ResponseEntity<ReminderDto> addReminder(@RequestBody ReminderDto dto) {
        return ResponseEntity.ok(reminderService.addReminder(dto));
    }
}

package utc.edu.thesis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utc.edu.thesis.domain.dto.ProjectDto;
import utc.edu.thesis.service.ProjectService;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/add-project")
    public ResponseEntity<ProjectDto> addProject(@RequestBody ProjectDto projectDto) {
        return ResponseEntity.ok(projectService.addProject(projectDto));
    }

    @PostMapping(value = "/add-outline-file", consumes = {"multipart/form-data"})
    public ResponseEntity<ProjectDto> addOutlineFile(@RequestParam("id") Long projectId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(projectService.addOutlineFile(projectId, file));
    }
}

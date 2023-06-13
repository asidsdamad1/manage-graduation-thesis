package utc.edu.thesis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utc.edu.thesis.domain.dto.DetailDto;
import utc.edu.thesis.domain.dto.ProjectDto;
import utc.edu.thesis.domain.dto.ProjectSearchDto;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.service.DetailService;
import utc.edu.thesis.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final DetailService detailService;

    @PostMapping("/add-project")
    public ResponseEntity<ProjectDto> addProject(@RequestBody ProjectDto projectDto) {
        return ResponseEntity.ok(projectService.addProject(projectDto));
    }

    @PostMapping("/edit-project")
    public ResponseEntity<ProjectDto> editProject(@RequestBody ProjectDto projectDto) {
        return ResponseEntity.ok(projectService.editProject(projectDto));
    }

    @PostMapping("/get-projects")
    public ResponseEntity<List<ProjectDto>> getProject(@RequestBody SearchDto searchDto) {
        return ResponseEntity.ok(projectService.getProjects(searchDto));
    }

    @PostMapping("/get-projects-filter")
    public ResponseEntity<List<ProjectDto>> getProjectFilter(@RequestBody ProjectSearchDto searchDto) {
        return ResponseEntity.ok(projectService.getProjectsFilter(searchDto));
    }

    @PostMapping("/delete-project/{id}")
    public ResponseEntity<Boolean> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.deleteProject(id));
    }

    @PostMapping("/delete-file-report/{id}")
    public ResponseEntity<Boolean> deleteFileReport(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.deleteFileReport(id));
    }

    @PostMapping("/delete-file-outline/{id}")
    public ResponseEntity<Boolean> deleteFileOutline(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.deleteFileOutline(id));
    }

    @PostMapping(value = "/add-outline-file", consumes = {"multipart/form-data"})
    public ResponseEntity<ProjectDto> addOutlineFile(@RequestParam("id") Long projectId,
                                                     @RequestParam("file") MultipartFile file,
                                                     @RequestParam("type") String type) {
        return ResponseEntity.ok(projectService.addOutlineFile(projectId, file, type));
    }

    @PostMapping("/get-detail/{projectId}")
    public ResponseEntity<List<DetailDto>> getProjectDetail(@PathVariable Long projectId) {
        return ResponseEntity.ok(detailService.getDetail(projectId));
    }

    @PostMapping("/get-detail-by-id/{id}")
    public ResponseEntity<DetailDto> getProjectDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(detailService.getDetailById(id));
    }

    @PostMapping("/add-detail")
    public ResponseEntity<DetailDto> addProjectDetail(@RequestBody DetailDto dto) {
        return ResponseEntity.ok(detailService.addDetail(dto));
    }

    @PostMapping("/edit-detail")
    public ResponseEntity<DetailDto> editProjectDetail(@RequestBody DetailDto dto) {
        return ResponseEntity.ok(detailService.editDetail(dto));
    }

    @PostMapping("/delete-detail/{id}")
    public ResponseEntity<Boolean> deleteProjectDetail(@PathVariable Long id) {
        return ResponseEntity.ok(detailService.deleteDetail(id));
    }

    @PostMapping("/delete-report-file-detail/{id}")
    public ResponseEntity<DetailDto> deleteReportFile(@PathVariable Long id) {
        return ResponseEntity.ok(detailService.deleteFileDetail(id));
    }

    @PostMapping(value = "/add-report-file-detail", consumes = {"multipart/form-data"})
    public ResponseEntity<DetailDto> addReportFileDetail(@RequestParam("id") Long projectId,
                                                     @RequestParam("file") MultipartFile file,
                                                     @RequestParam("type") String type) {
        return ResponseEntity.ok(detailService.addFileDetail(projectId, file, type));
    }
}

package utc.edu.thesis.controller.assign;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utc.edu.thesis.service.AssignmentService;

@RestController
@RequestMapping("/assign")
@RequiredArgsConstructor
public class AssignController {
    private final AssignmentService assignmentService;


}

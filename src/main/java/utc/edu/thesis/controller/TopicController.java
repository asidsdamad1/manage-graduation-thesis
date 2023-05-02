package utc.edu.thesis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.dto.TopicDto;
import utc.edu.thesis.service.TopicService;

import java.util.List;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @PostMapping("/get-topic")
    public ResponseEntity<List<TopicDto>> getTopic(@RequestBody SearchDto searchDto) {
        return ResponseEntity.ok(topicService.getTopic(searchDto));
    }
}

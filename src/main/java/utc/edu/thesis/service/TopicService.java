package utc.edu.thesis.service;

import org.springframework.stereotype.Service;
import utc.edu.thesis.domain.dto.TopicDto;

@Service
public interface TopicService {
    TopicDto getById(Long id);
}

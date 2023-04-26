package utc.edu.thesis.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utc.edu.thesis.domain.dto.TopicDto;
import utc.edu.thesis.service.TopicService;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {


    @Override
    public TopicDto getById(Long id) {
        return null;
    }
}

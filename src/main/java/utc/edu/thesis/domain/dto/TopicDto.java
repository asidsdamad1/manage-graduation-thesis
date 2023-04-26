package utc.edu.thesis.domain.dto;

import lombok.Data;
import utc.edu.thesis.domain.entity.Topic;
import utc.edu.thesis.util.ObjectMapperUtil;

@Data
public class TopicDto {
    private Long id;
    private String name;


    public static TopicDto of(Topic entity) {
        return ObjectMapperUtil.OBJECT_MAPPER.convertValue(entity, TopicDto.class);
    }

    public static Topic toEntity(TopicDto dto) {
        return ObjectMapperUtil.OBJECT_MAPPER.convertValue(dto, Topic.class);
    }
}

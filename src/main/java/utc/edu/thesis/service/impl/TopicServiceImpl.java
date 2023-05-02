package utc.edu.thesis.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.dto.TopicDto;
import utc.edu.thesis.domain.entity.Topic;
import utc.edu.thesis.exception.request.NotFoundException;
import utc.edu.thesis.repository.TopicRepository;
import utc.edu.thesis.service.TopicService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final EntityManager entityManager;
    private final TopicRepository topicRepository;

    @Override
    public List<TopicDto> getTopic(SearchDto dto) {
        if (dto == null) {
            throw new NotFoundException("not found");
        }

        String whereClause = "";
        String orderBy = " ";
        String sql = "select e from Topic as e where(1=1) ";
        if (StringUtils.hasText(dto.getValueSearch())) {
            if ("ID".equals(dto.getConditionSearch())) {
                whereClause += " AND e.id = " + dto.getValueSearch();
            }
        }
        sql += whereClause + orderBy;
        Query q = entityManager.createQuery(sql, Topic.class);
        List<Topic> topics = q.getResultList();
        List<TopicDto> resultList = new ArrayList<>();
        topics.forEach(res -> resultList.add(TopicDto.of(res)));

        return resultList;
    }
}

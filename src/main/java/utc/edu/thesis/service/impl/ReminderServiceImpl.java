package utc.edu.thesis.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import utc.edu.thesis.domain.dto.*;
import utc.edu.thesis.domain.entity.Reminder;
import utc.edu.thesis.exception.request.NotFoundException;
import utc.edu.thesis.repository.ReminderRepository;
import utc.edu.thesis.service.ReminderService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {
    private final EntityManager entityManager;
    private final ReminderRepository reminderRepository;

    @Override
    public List<ReminderDto> getReminder(SearchDto dto) {
        if (dto == null) {
            throw new NotFoundException("not found");
        }

        String whereClause = "";
        String orderBy = " ";
        String sql = "select e from Reminder as e where(1=1) ";
        if (StringUtils.hasText(dto.getValueSearch())) {
            if ("TEACHER".equals(dto.getConditionSearch())) {
                String[] valueSearch = dto.getValueSearch().split(",");
                whereClause += " AND e.session.id = " + valueSearch[0]
                        + " AND e.teacher.id = " + valueSearch[1];
            } else if ("ID".equals(dto.getConditionSearch())) {
                whereClause += " AND e.id = " + dto.getValueSearch();
            }
        }
        sql += whereClause + orderBy;
        Query q = entityManager.createQuery(sql, Reminder.class);
        List<Reminder> resultQuery = q.getResultList();
        List<ReminderDto> res = new ArrayList<>();
        resultQuery.forEach(reminder -> res.add(ReminderDto.of(reminder)));

        return res;
    }

    @Override
    public ReminderDto addReminder(ReminderDto dto) {
        if (dto != null) {
            Reminder reminder = Reminder.builder()
                    .title(dto.getTitle())
                    .classNames(dto.getClassNames())
                    .start(dto.getStart())
                    .end(dto.getEnd())
                    .teacher(TeacherDto.toEntity(dto.getTeacher()))
                    .session(SessionDto.toEntity(dto.getSession()))
                    .build();

            reminderRepository.save(reminder);

            return ReminderDto.of(reminder);
        }
        return null;
    }
}

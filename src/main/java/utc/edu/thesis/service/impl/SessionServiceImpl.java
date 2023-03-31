package utc.edu.thesis.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.dto.SessionDto;
import utc.edu.thesis.domain.entity.Session;
import utc.edu.thesis.domain.entity.Student;
import utc.edu.thesis.exception.request.NotFoundException;
import utc.edu.thesis.repository.SessionRepository;
import utc.edu.thesis.service.SessionService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final EntityManager entityManager;

    @Override
    public Session addSession(Session request) {
        Session session = Session.builder()
                .year(request.getYear())
                .notes(request.getNotes())
                .build();

        return sessionRepository.save(session);
    }

    @Override
    public Session editSession(Session request) {
        if (request.getId() != null) {
            Session response = sessionRepository.findById(request.getId()).orElseThrow(() -> {
                throw new NotFoundException("not found id: %d".formatted(request.getId()));
            });

            Session session = Session.builder()
                    .id(response.getId())
                    .year(request.getYear())
                    .notes(request.getNotes())
                    .build();

            return sessionRepository.save(session);
        }
        return null;
    }

    @Override
    public Boolean deleteSession(Long id) {
        if (id != null) {
            Session res = sessionRepository.findById(id).orElseThrow(() -> {
                throw new NotFoundException("not found id: %d".formatted(id));
            });
            if (res != null) {
                sessionRepository.delete(res);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SessionDto> getSession(SearchDto dto) {
        if (dto == null) {
            throw new NotFoundException("not found");
        }

        String whereClause = "";
        String orderBy = " ";
        String sql = "select e from Session as e where(1=1) ";
        if (StringUtils.hasText(dto.getValueSearch())) {
            if ("YEAR".equals(dto.getConditionSearch())) {
                whereClause += " AND e.year = " + dto.getValueSearch();
            }
        }
        sql += whereClause + orderBy;
        Query q = entityManager.createQuery(sql, Student.class);

        return q.getResultList();
    }
}

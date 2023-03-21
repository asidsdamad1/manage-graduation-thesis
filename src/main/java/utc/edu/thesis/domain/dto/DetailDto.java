package utc.edu.thesis.domain.dto;

import lombok.Data;
import utc.edu.thesis.domain.entity.Project;

import java.time.LocalDate;

@Data
public class DetailDto {
    private Long id;
    private String title;
    private Integer status;
    private String comment;
    private LocalDate startDate;
    private LocalDate endDate;
    private Project project;
}

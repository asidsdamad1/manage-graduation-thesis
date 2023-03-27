package utc.edu.thesis.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchDto {
    private String valueSearch;
    private String conditionSearch;
}

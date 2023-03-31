package utc.edu.thesis.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SearchDto {
    private String valueSearch;
    private String conditionSearch;
}

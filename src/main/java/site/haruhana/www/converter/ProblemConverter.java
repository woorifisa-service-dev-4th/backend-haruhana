package site.haruhana.www.converter;

import org.springframework.stereotype.Component;
import site.haruhana.www.dto.ProblemDto;
import site.haruhana.www.entity.Problem;

@Component
public class ProblemConverter {

    public Problem toEntity(ProblemDto dto) {
        return Problem.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .level(dto.getLevel())
                .answer(dto.getAnswer())
                .problemCategory(dto.getProblemCategory())
                .build();
    }

    public ProblemDto toDto(Problem entity) {
        return ProblemDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .level(entity.getLevel())
                .answer(entity.getAnswer())
                .problemCategory(entity.getProblemCategory())
                .build();
    }
}

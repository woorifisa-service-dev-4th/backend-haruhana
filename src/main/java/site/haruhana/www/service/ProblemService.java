package site.haruhana.www.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.haruhana.www.dto.ProblemDto;
import site.haruhana.www.repository.ProblemRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    /**
     * 모든 문제 검색하여 반환하는 메소드
     *
     * @param pageable 페이지네이션 정보를 포함하는 pageable 객체
     * @return 페이지네이션된 문제 DTO 페이지
     */
    public Page<ProblemDto> getProblemList(Pageable pageable) {
        return problemRepository.findAll(pageable)
                .map(ProblemDto::new);
    }

}

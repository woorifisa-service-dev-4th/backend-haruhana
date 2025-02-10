package site.haruhana.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.haruhana.www.entity.Problem;
import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByCategoryId(Long categoryId);

//    @Query("")
}

package site.haruhana.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.haruhana.www.entity.Attempt;

/**
 * 문제 풀이 시도 정보를 관리하는 Repository
 * JpaRepository와 커스텀 Repository를 상속받아 기본 CRUD 및 추가 기능을 제공
 *
 * @see AttemptRepositoryCustom
 */
public interface AttemptRepository extends JpaRepository<Attempt, Long>, AttemptRepositoryCustom {

}


package site.haruhana.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.haruhana.www.domain.Users;

public interface UesrRepository extends JpaRepository<Users, Long> {
}

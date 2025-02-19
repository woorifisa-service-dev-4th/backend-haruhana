package site.haruhana.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.haruhana.www.entity.User;
import site.haruhana.www.entity.AuthProvider;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 인증 제공자와 이메일로 사용자가 존재하는지 확인합니다.
     *
     * @param provider 인증 제공자 (GOOGLE, KAKAO, NAVER 등)
     * @param email    조회할 사용자의 이메일
     * @return 사용자가 존재하는지 여부
     */
    boolean existsByProviderAndEmail(AuthProvider provider, String email);

    /**
     * 인증 제공자와 이메일로 사용자를 조회합니다.
     *
     * @param provider 인증 제공자 (GOOGLE, KAKAO, NAVER 등)
     * @param email    조회할 사용자의 이메일
     * @return 조회된 사용자 정보
     */
    Optional<User> findByProviderAndEmail(AuthProvider provider, String email);
}

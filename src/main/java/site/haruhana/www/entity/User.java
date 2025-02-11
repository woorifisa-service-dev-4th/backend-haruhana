package site.haruhana.www.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.haruhana.www.utils.EncryptUtil;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

    /**
     * 사용자 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자 이름
     */
    private String name;

    /**
     * 사용자 이메일
     */
    @Column(nullable = false)
    private String email;

    /**
     * 사용자 비밀번호
     */
    @Column(name = "password")
    private String encryptedPassword;

    /**
     * 사용자 인증 제공자 (로컬/구글/카카오/네이버)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    /**
     * 사용자 프로필 이미지 URL
     */
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    /**
     * 사용자 역할 (USER/ADMIN)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * 사용자 삭제 여부 (소프트 삭제 처리를 위함)
     */
    private boolean status;

    /**
     * 사용자 비밀번호를 업데이트하는 메소드
     *
     * @param rawPassword 사용자의 새로운 평문 비밀번호
     */
    public void updatePassword(String rawPassword) {
        this.encryptedPassword = EncryptUtil.encrypt(rawPassword);
    }

    /**
     * 사용자별 풀이 기록
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<Attempt> attempts = new ArrayList<>();
    /* Users 테이블에서 Attempts 기록들 가져올 수 있게 하기 위해 양방향 설정 */

    @Builder
    public User(String name, String email, String rawPassword, AuthProvider provider,
                String profileImageUrl, Role role) {
        this.name = name;
        this.email = email;
        this.encryptedPassword = rawPassword != null ? EncryptUtil.encrypt(rawPassword) : null;
        this.provider = provider;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

}

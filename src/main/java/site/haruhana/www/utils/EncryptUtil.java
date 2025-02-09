package site.haruhana.www.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 비밀번호 암호화를 위한 유틸리티 클래스
 * <p>
 * Spring Security의 BCryptPasswordEncoder를 사용하여
 * 비밀번호 암호화 및 검증을 수행합니다.
 */
@Component
public class EncryptUtil {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 평문 비밀번호를 BCrypt로 암호화합니다.
     *
     * @param rawPassword 암호화할 평문 비밀번호
     * @return 암호화된 비밀번호
     */
    public static String encrypt(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 평문 비밀번호와 암호화된 비밀번호가 일치하는지 검증합니다.
     *
     * @param rawPassword     검증할 평문 비밀번호
     * @param encodedPassword 암호화되어 저장된 비밀번호
     * @return 일치 여부
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
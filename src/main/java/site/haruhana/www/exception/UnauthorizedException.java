package site.haruhana.www.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("이메일 또는 비밀번호가 일치하지 않습니다.");
    }
}

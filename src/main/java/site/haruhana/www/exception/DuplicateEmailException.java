package site.haruhana.www.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("해당 이메일은 이미 사용중인 이메일입니다. (" + email + ")");
    }
}

package site.haruhana.www.utils;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {

    /**
     * 파일을 저장소에 업로드합니다.
     *
     * @param file 업로드할 파일 (MultipartFile)
     * @return 업로드된 파일의 URL
     * @throws IllegalArgumentException 파일이 비어있거나 초기화되지 않은 경우
     */
    String upload(MultipartFile file);

    /**
     * 저장소의 지정된 디렉토리에 파일을 업로드합니다.
     *
     * @param file 업로드할 파일 (MultipartFile)
     * @param directory 업로드할 디렉토리 경로
     * @return 업로드된 파일의 URL
     * @throws IllegalArgumentException 파일이 비어있거나 초기화되지 않은 경우
     */
    String upload(MultipartFile file, String directory);
}

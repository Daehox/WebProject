package com.yeonsung.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yeonsung.dao.BoardDao;
import com.yeonsung.dto.BoardDto;

@Service
public class BoardService {

    //@Value 를 사용해, fileDir을 설정 -> root-context에 경로 설정 
    @Value("${file.dir}")
    private String fileDir;

    @Autowired
    private BoardDao boardDao;

    // 모든 게시물을 날짜 역순으로 가져오기
    public List<BoardDto> getAllPosts() {
        return boardDao.getAllPosts();
    }

    // 새로운 게시물 추가하기
    public void insertPost(BoardDto post) {
        boardDao.insertPost(post);
    }

    // 게시물 상세 보기
    public BoardDto getPostById(int postId) {
        return boardDao.getPostById(postId);
    }

    // 게시물 수정하기
    public void updatePost(BoardDto post) {
        boardDao.updatePost(post);
    }

    // 게시물 삭제하기
    public void deletePost(int postId) {
        boardDao.deletePost(postId);
    }

    // 페이지네이션을 위한 코드
    public List<BoardDto> getPostsByPage(int pageNumber, int postsPerPage) {
        return boardDao.getPostsByPage(pageNumber, postsPerPage);
    }

    // 전체 게시물 수 가져오기
    public int getTotalPostCount() {
        return boardDao.getTotalPostCount();
    }

    // 제목으로 게시물 검색하기
    public List<BoardDto> searchPostsByTitle(String title) {
        return boardDao.searchPostsByTitle(title);
    }

    // 파일 저장 경로 생성
    // 지정한 파일 경로는 root-context.xml 에 선언
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    // 파일 저장 로직
    // MultipartFile을 사용해 HTTP 요청을 통해 업로드된 파일을 처리 함
    public String storeFile(MultipartFile multipartFile) throws IOException {
        //비어 있을 경우 NULL 반환
        if (multipartFile.isEmpty()) {
            return null;
        }

        //원본 파일의 이름을 그 대로 가져옴
        String originalFilename = multipartFile.getOriginalFilename();
        //서버에 저장할 이름을 생성
        String storeFileName = createStoreFileName(originalFilename);

        //파일을 지정된 경로에 저장
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        //저장된 파일 이름을 반환 
        return storeFileName;
    }

    // 서버에 저장하는 파일명 생성
    // 원본 파일의 이름을 기반으로 만들어짐
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        // 고유한 UUID를 만들어 사진 이름이 같더라도 중복되지 않게 함
        String uuid = UUID.randomUUID().toString();
        // UUID + . + ext 새로운 파일의 이름을 생성
        return uuid + "." + ext;
    }

    // 확장자 추출
    private String extractExt(String originalFilename) {
        //원본 파일에서 마지막 점의 위치를 찾아 확장자를 찾음
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}

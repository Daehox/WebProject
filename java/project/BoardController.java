package com.yeonsung.project;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yeonsung.dto.BoardDto;
import com.yeonsung.dto.CommentDto;
import com.yeonsung.service.BoardService;
import com.yeonsung.service.CommentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//컨트롤러 정의
@Controller
public class BoardController {

	//오류를 해결 하기 위한 logger 정의
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    //서비스 객체 선언, 한번 초기화 되면 변경 되지 않음
    private final BoardService boardService;

    @Value("${file.Dir}")
    private String uploadFolder;

    //생성자 주입
    //의존성을 생성자를 통해 주입
    //CommentService 보다 BoardService가 주요 의존성이기 때문에 생성자를 통해 주입 해줌
    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //생성자 주입
    //의존성을 필드에 직접 주입
    @Autowired
    private CommentService commentService;


    //메인 페이지를 보여주는 역할
    //@RequestParam(required = false) String query 를 사용함으로 검색어가 있던 없던, 게시글 표시
    @GetMapping("/main")
    public String showMainPage(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String query) {
        List<BoardDto> posts;
        // 검색어가 있다면 service.searchPostByTitle에 결과를 표시
        if (query != null && !query.isEmpty()) {
            posts = boardService.searchPostsByTitle(query);
            // 검색어가 없다면 모든 게시글 보여줌
        } else {
            posts = boardService.getAllPosts();
        }
        model.addAttribute("posts", posts);
        return "main"; 
    }
    
    
    // 게시물 작성 페이지 보기
    @GetMapping("/write")
    public String showWriteForm(Model model, HttpSession session) {
        //비어 있는 객체 생성
        BoardDto boardDto = new BoardDto();
        //boardDto에 객체 데이터 추가
        model.addAttribute("boardDto", boardDto); 
        return "write"; 
    }

    
    
    // 게시물 작성 처리
    // @ModelAttribute("boardDto") Board객체에 바인딩
    // @RequestParam("file") MultipartFile file 파일 업로드 처리를 위해 MultipartFile 바인딩
    // 세션과 뷰에 데이터 전달을 위한 매개변수 사용
    @PostMapping("/write")
    public String writePost(@ModelAttribute("boardDto") BoardDto boardDto, @RequestParam("file") MultipartFile file, HttpSession session, Model model) {
        // 세션에서 사용자 ID 가져오기
        String userIdStr = (String) session.getAttribute("loggedInUser"); 
        //ID가 존재 할 경우 authorID 필드 설정
        if (userIdStr != null) {
            boardDto.setAuthorId(userIdStr);

            // 파일명을 얻어냅니다.
            String fileRealName = file.getOriginalFilename(); 
            // 파일 사이즈를 얻어냅니다.
            long size = file.getSize(); 

            // 파일 확장자 구하기
            String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."), fileRealName.length());
            String uploadFolder = "/Users/imdaeho/uploads"; // 업로드 폴더 경로 설정

            // 특수 문자 제거 및 UUID 생성
            UUID uuid = UUID.randomUUID();
            String[] uuids = uuid.toString().split("-");
            String uniqueName = uuids[0]; // 고유한 문자열 생성

            // 파일명에서 특수 문자 제거
            String sanitizedFileName = fileRealName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

            // 최종 파일명 생성
            File saveFile = new File(uploadFolder + "/" + uniqueName + "_" + sanitizedFileName); // 고유한 이름으로 파일 저장

            try {
                file.transferTo(saveFile); // 실제 파일 저장
                boardDto.setImagePath(uniqueName + "_" + sanitizedFileName); // 상대 경로로 저장
            } catch (IllegalStateException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "파일 업로드 실패: " + e.getMessage());
                return "error-page";
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "파일 업로드 실패: " + e.getMessage());
                return "error-page";
            }

            //게시물 데이터를 데이터베이스의 삽입
            boardService.insertPost(boardDto);
            return "redirect:/main";
        } else {
            return "error-page"; // 로그인되지 않은 사용자의 경우 오류 페이지로 리다이렉트
        }
    }



    // 게시물 상세 페이지 보기
    @GetMapping("/view/{postId}")
    public String viewPost(@PathVariable("postId") int postId, Model model, @RequestParam(defaultValue = "1") int page) {
        BoardDto boardDto = boardService.getPostById(postId); // 게시물 ID로 게시물 정보 가져오기
        model.addAttribute("boardDto", boardDto); // 게시물 정보 모델에 추가

        // 해당 게시물의 페이징된 댓글 가져오기
        int pageSize = 5; // 한 페이지에 보여줄 댓글 수
        List<CommentDto> comments = commentService.getCommentsByPostIdPaged(postId, page, pageSize);
        model.addAttribute("comments", comments); // 댓글 정보 모델에 추가

        // 페이징 관련 정보 추가
        int totalComments = commentService.getCommentCountByPostId(postId); // 총 댓글 수
        int totalPages = (int) Math.ceil((double) totalComments / pageSize); // 총 페이지 수 계산
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "view"; 
    }


    // 댓글 등록 처리
    @PostMapping("/comment/{postId}")
    public String addComment(@PathVariable("postId") int postId, @RequestParam("comment") String commentContent, HttpSession session) {
        // 세션에서 사용자 아이디 가져오기
        String loggedInUserId = (String) session.getAttribute("loggedInUser");

        // 사용자 아이디가 null이면 로그인 페이지로 리다이렉트
        if (loggedInUserId == null) {
            return "redirect:/login";
        }

        // 댓글 등록 처리
        CommentDto comment = new CommentDto();
        comment.setPostId(postId);
        comment.setContent(commentContent);
        comment.setUserId(loggedInUserId); // 로그인 상태인 경우에만 userId 설정

        commentService.addComment(comment);

        return "redirect:/view/" + postId; // 댓글 등록 후 게시물 상세 페이지로 리다이렉트
    }

    // 게시물 수정 페이지 보기
    @GetMapping("/edit/{postId}")
    public String showEditForm(@PathVariable("postId") int postId, Model model, HttpSession session) {
        BoardDto boardDto = boardService.getPostById(postId); // 게시물 ID로 게시물 정보 가져오기
        model.addAttribute("boardDto", boardDto); // 게시물 정보 모델에 추가

         // 세션에서 로그인된 사용자 아이디 가져오기
         String loggedInUser = (String) session.getAttribute("loggedInUser");
         model.addAttribute("loggedInUser", loggedInUser);
 
        return "edit"; // 
    }

    // 게시물 수정 처리
    @PostMapping("/edit/{postId}")
    public String editPost(@ModelAttribute("boardDto") BoardDto boardDto, HttpSession session) {
        boardService.updatePost(boardDto); // 게시물 수정
        return "redirect:/main"; // 게시물 수정 후 목록 페이지로 리다이렉트
    }

    // 게시물 삭제 처리
    @GetMapping("/delete/{postId}")
    public String deletePost(@PathVariable("postId") int postId, HttpSession session) {
        boardService.deletePost(postId); // 게시물 삭제
        return "redirect:/main"; // 게시물 삭제 후 목록 페이지로 리다이렉트
    }

// 페이징 처리된 게시물 목록 보기
@GetMapping("/list")
public String listPostsByPage(Model model, @RequestParam(defaultValue = "1") int page) {
    int postsPerPage = 10; // 고정된 개수의 게시글 수

    // 총 게시물 수
    int totalPosts = boardService.getTotalPostCount();

    // 총 페이지 수 계산
    int totalPages = (int) Math.ceil((double) totalPosts / postsPerPage);

    // 페이지 번호 검증
    if (page < 1) {
        page = 1;
    }
    if (totalPages > 0 && page > totalPages) {
        page = totalPages;
    }

    // 해당 페이지에 표시할 게시물 가져오기
    List<BoardDto> posts = boardService.getPostsByPage(page, postsPerPage);

    // 페이지 번호 목록 생성
    List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                                         .boxed()
                                         .collect(Collectors.toList());

    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", totalPages);
    model.addAttribute("pageNumbers", pageNumbers);
    model.addAttribute("posts", posts);

    return "list";
}

    
    // 제목으로 게시물 검색
    @GetMapping("/search")
    public String searchPosts(@RequestParam("query") String query, Model model) {
        List<BoardDto> posts = boardService.searchPostsByTitle(query);
        model.addAttribute("posts", posts);
        return "posts"; 
    }
}

package com.yeonsung.project;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.yeonsung.dto.BoardDto;
import com.yeonsung.dto.CommentDto;
import com.yeonsung.service.BoardService;
import com.yeonsung.service.CommentService;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentService commentService;

    // 메인 페이지 보기
    @GetMapping("/main")
    public String showMainPage(Model model, @RequestParam(defaultValue = "1") int page) {
        // 게시물 목록 데이터를 모델에 추가
        listPostsByPage(model, page);
        return "main"; 
    }

    // 게시물 작성 페이지 보기
    @GetMapping("/write")
    public String showWriteForm(Model model, HttpSession session) {
        BoardDto boardDto = new BoardDto();
        model.addAttribute("boardDto", boardDto); // 빈 게시물 객체를 모델에 추가
        return "write"; 
    }

    // 게시물 작성 처리
    @PostMapping("/write")
    public String writePost(@ModelAttribute("boardDto") BoardDto boardDto, HttpSession session) {
        String userIdStr = (String) session.getAttribute("loggedInUser"); // 세션에서 사용자 ID 가져오기
        if (userIdStr != null) {
            boardDto.setAuthorId(userIdStr); 
            boardService.insertPost(boardDto); 
            return "redirect:/main"; // 메인 페이지로 리다이렉트
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
        int pageSize = 5; // 한 페이지에 보여줄 게시물 수 
        int fixedNumberOfPosts = 5; // 고정된 개수의 게시글 수

        // 총 게시물 수
        int totalPosts = boardService.getTotalPostCount();

        // 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalPosts / fixedNumberOfPosts);

        // 요청 페이지가 총 페이지보다 큰 경우, 마지막 페이지로 설정
        if (page > totalPages) {
            page = totalPages;
        }

        // 해당 페이지에 표시할 게시물 가져오기
        List<BoardDto> posts = boardService.getPostsByPage(page, fixedNumberOfPosts);

        // 페이지 번호 목록 생성
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                                             .boxed()
                                             .collect(Collectors.toList());

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("posts", posts);

        return "list"; //
    }
}

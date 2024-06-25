package com.yeonsung.project;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.yeonsung.dto.UserDto;
import com.yeonsung.service.LoginService;

@Controller
public class UserController {

    // LoginService를 자동으로 주입
    @Autowired
    private LoginService loginService;

    // 메인 페이지로 이동
    @RequestMapping("/main")
    public String MainPage() {
        return "main";  
    }

    // 로그인 페이지로 이동
    @RequestMapping("/login")
    public String LoginPage(HttpServletRequest request, Model model) {
        // 쿠키에서 저장된 아이디를 읽어옴
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("rememberedId")) {
                    model.addAttribute("rememberedId", cookie.getValue()); // 쿠키에서 아이디를 가져와 모델에 추가
                    break;
                }
            }
        }
        return "loginpage"; 
    }

    // 사용자 입력 폼 페이지로 이동
    @GetMapping("/insert")
    public String showInsertForm() {
        return "insert"; 
    }

    // 사용자 등록 처리
    @PostMapping("/insert")
    public String insertUser(@ModelAttribute UserDto user, HttpServletResponse response) {
        if (loginService.checkUserExists(user.getId())) {
            response.setContentType("text/html; charset=UTF-8");
            try {
                response.getWriter().println("<script>alert('아이디가 이미 존재합니다.'); history.back();</script>");
                response.getWriter().flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null; // 아이디가 존재할 경우 알림 후 이전 페이지로 이동
        }

        // 새로운 사용자 등록
        loginService.insert(user);
        response.setContentType("text/html; charset=UTF-8");
        try {
            response.getWriter().println("<script>alert('가입이 완료되었습니다.'); location.href='login';</script>");
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; 
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam("id") String id, @RequestParam("passwd") String passwd, 
                        @RequestParam(value = "remember-check", required = false) String rememberCheck,
                        Model model, HttpSession session, HttpServletResponse response) {
        // 사용자 아이디 존재 여부 확인
        if (loginService.checkUserExists(id)) {
            // 비밀번호 확인
            if (loginService.isPasswordCorrect(id, passwd)) {
                session.setAttribute("loggedInUser", id); // 세션에 사용자 아이디 저장

                // 아이디 저장 체크박스가 체크되어 있으면 쿠키에 저장
                if (rememberCheck != null && rememberCheck.equals("on")) {
                    Cookie cookie = new Cookie("rememberedId", id);
                    cookie.setMaxAge(60 * 60 * 24 * 30); // 쿠키 유효기간 30일 설정
                    response.addCookie(cookie);
                } else {
                    Cookie cookie = new Cookie("rememberedId", "");
                    cookie.setMaxAge(0); // 쿠키 삭제
                    response.addCookie(cookie);
                }

                return "redirect:/main"; // 로그인 성공 시 메인 페이지로 이동
            } else {
                model.addAttribute("error", "비밀번호가 옳지 않습니다.");
            }
        } else {
            model.addAttribute("error", "아이디가 옳지 않습니다.");
        }
        return "loginpage"; // 로그인 실패 시 로그인 페이지로 이동
    }

    // 로그아웃 처리
    @PostMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate(); // 세션 무효화
        // 로그아웃 시에도 쿠키를 삭제하지 않음
        return "redirect:/login"; // 로그아웃 후 로그인 페이지로 이동
    }
}

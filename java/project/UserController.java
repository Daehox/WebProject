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
	
	// 사용자 입력 폼 페이지로 이동
	@GetMapping("/insert")
	public String showInsertForm() {
		return "insert";
	}

	// 로그인 페이지로 이동
	// HttpServletRequest 을 사용해 loginpage에 rememberedID를 모델을 통해 넘겨줌
	@RequestMapping("/login")
	public String LoginPage(HttpServletRequest request, Model model) {
		// 쿠키에서 저장된 아이디를 읽어옴
		Cookie[] cookies = request.getCookies();
		//쿠기가 있다면 for문을 통해 값을 가져오고 
		//model.addAttribute를 통해서 쿠키 값 모델에 전달
		if (cookies != null) {
		    // 쿠키 배열을 첫 번째부터 하나씩 탐색
			for (Cookie cookie : cookies) {
				// 쿠키 이름이 remembredID인 쿠키를 찾으면 같으면 모델 값 전달
				if (cookie.getName().equals("rememberedId")) {
					model.addAttribute("rememberedId", cookie.getValue());
					break;
				}
			}
		}
		return "loginpage";
	}

	// 로그인 처리
	// remember-chceck 버튼을 만들어 ID를 세션에 저장
	// 세션에 저장된 ID를 통해 사용자가 로그인 중인지 아닌지 확인
	@PostMapping("/login")
    // 메소드 내에서 이 값을 사용하여 로그인 처리
	public String login(@RequestParam("id") String id, @RequestParam("passwd") String passwd,
			@RequestParam(value = "remember-check", required = false) String rememberCheck, Model model,
			HttpSession session, HttpServletResponse response) {
		// 사용자 아이디 존재 여부 확인
		if (loginService.checkUserExists(id)) {
			// 비밀번호 확인
			if (loginService.isPasswordCorrect(id, passwd)) {
				session.setAttribute("loggedInUser", id); // 세션에 사용자 아이디 저장

				// 아이디 저장 체크박스가 체크되어 있으면 쿠키에 저장
				if (rememberCheck != null && rememberCheck.equals("on")) {
					// ID를 rememberedID에 저장
					Cookie cookie = new Cookie("rememberedId", id);
					//30일로 지정 => 기간을 바꾸고 싶으면 맨 뒤에 숫자를 원하는 기간 만큼으로 변경
					cookie.setMaxAge(60 * 60 * 24 * 30);
					response.addCookie(cookie);
				} else {
					Cookie cookie = new Cookie("rememberedId", "");
					//체크 박스가 체크 되지 않았을시엔 유효기간을 0으로 설정하여 쿠키 삭제
					cookie.setMaxAge(0); 
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

	// ID가 중복 될 경 실행되는 메서드
	// user.getId를 통해 사용자가 입력 받은 ID를 가져와 checkUserExistsd메서드를 호출해 아이디가 데이터 베이스에 이미 존재하는지 확인
	// true 일 경우 try-catch 문 실행
	@PostMapping("/insert")
	public String insertUser(@ModelAttribute UserDto user, HttpServletResponse response) {
		if (loginService.checkUserExists(user.getId())) {
			//HTMl문서에서 UTF-8 인코딩을 사용해 문자 데이터를 전송 하도록 함
			response.setContentType("text/html; charset=UTF-8");
			try {
				//사용자에게 alert 경고 창을 띄우기 위한 getWriter 사용
				response.getWriter().println("<script>alert('아이디가 이미 존재합니다.'); history.back();</script>");
			} catch (Exception e) {
				//예외가 발생했을때 어떤 것이 문제인지 보여줌
				e.printStackTrace();
			}
			return null; // 아이디가 존재할 경우 알림 후 이전 페이지로 이동
		}

		// ID가 중복 되지 않았을경우 
		//데이터를 UserDao insert 메서드를 활용 해 정보를 데이터베이스에 삽임
		loginService.insert(user);
		response.setContentType("text/html; charset=UTF-8");
		try {
			//사용자에게 alert 경고 창을 띄우기 위한 getWriter 사용
			response.getWriter().println("<script>alert('가입이 완료되었습니다.'); location.href='login';</script>");
			//예외가 발생했을때 어떤 것이 문제인지 보여줌
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 로그아웃 처리
	// 세션을 사용해 로그인 되어있을 경우 세션 무효화 시킴
	@PostMapping("/logout")
	public String logout(HttpSession session, HttpServletResponse response) {
		//세션 무효화를 위해 invalidate 메서드 사용
		session.invalidate();
		 // 로그아웃 후 로그인 페이지로 이동
		return "redirect:/login"; 
	}
}

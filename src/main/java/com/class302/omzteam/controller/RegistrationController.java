package com.class302.omzteam.controller;

import com.class302.omzteam.model.MemberDto;
import com.class302.omzteam.mybatis.MybatisDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registration")
@Slf4j
public class RegistrationController {

    @Autowired
    private MybatisDao mybatisDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/form")
    public String registrationForm() {

        return "registration/registrationForm";
    }
    @Transactional
    @PostMapping("/process")
    public String processRegistration(@RequestParam Integer memNo, @RequestParam String memPw, @RequestParam String memName, @RequestParam String email) {
        try {
            // 입력한 비밀번호를 암호화
            String encodedPassword = passwordEncoder.encode(memPw);

            // MemberDto 객체 생성 및 데이터베이스에 저장
            MemberDto memberDto = MemberDto.builder()
                    .mem_no(memNo)
                    .mem_pw(encodedPassword)
                    .mem_name(memName)
                    .email(email)
                    .build();

            mybatisDao.insertMember(memberDto);
            log.info("----------------" + memberDto);
            System.out.println(memberDto);

            return "redirect:/login/loginForm"; // 회원가입 후 로그인 페이지로 이동
        } catch (Exception e) {
            e.printStackTrace(); // 예외 내용을 콘솔에 출력
            log.error("An error occurred during registration: " + e.getMessage()); // 로그에 예외 메시지 기록
            return "redirect:/errorPage"; // 오류 페이지로 리다이렉트 또는 다른 예외 처리 방식을 적용

        }
    }

    @GetMapping("/errorPage")
    public String showErrorPage(Model model) {
        model.addAttribute("errorMessage", "An error occurred during registration."); // 에러 메시지 추가
        return "errorPage";
    }


}

package com.class302.omzteam.controller;

import com.class302.omzteam.model.MemberDto;
import com.class302.omzteam.mybatis.MybatisDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private MybatisDao mybatisDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/loginForm")
    public String login() {
        return "/login/loginForm";
    }

    @PostMapping("/loginForm")
    public String processLogin(@RequestParam String memNo, @RequestParam String memPw, Model model) {
        MemberDto memberDto = mybatisDao.findByMemNo(memNo);
        System.out.println(memberDto);


        if (memberDto != null && passwordEncoder.matches(memPw, memberDto.getMem_pw())) {
            // 로그인 성공

            // 사용자의 역할 및 권한 설정 (빈 상태로 설정)
            UserDetails userDetails = new User(
                    memNo,
                    passwordEncoder.encode(memPw),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );

            // 스프링 시큐리티를 사용하여 사용자 정보를 세션에 저장
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), authentication.getAuthorities()));

            return "redirect:/main";
        } else {
            // 로그인 실패
            model.addAttribute("error", "Invalid credentials");
            return "/login/loginForm";
        }


    }
}

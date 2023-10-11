package com.class302.omzteam.controller;


import com.class302.omzteam.mybatis.DeptDao;
import com.class302.omzteam.mybatis.MemberDao;
import com.class302.omzteam.service.UserService;
import com.class302.omzteam.spring.Dept;
import com.class302.omzteam.spring.Member;
import com.class302.omzteam.spring.UserCommand;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberDao memberDao;

    @Autowired
    UserService userService;

    @Autowired
    DeptDao deptDao;

    @GetMapping({"/test"})
    public void test(HttpSession session, Model model) {
        List<UserCommand> users = userService.users();
        for (UserCommand u : users) {
            log.info("========================================" + u);
        }
        model.addAttribute("users",users);
    }

    @GetMapping({"/depttable"})
    public void depttest(Model model) {
        List<Dept> depts = deptDao.deptAll();
        List<Member> members = memberDao.memberAll();

        model.addAttribute("depts",depts);
        model.addAttribute("members",members);
    }

    @GetMapping({"/input"})
    @ResponseBody
    public UserCommand ax(@RequestParam int mem_no){
        System.out.println(mem_no);
        Member member = memberDao.memberByno(mem_no);
        log.info("======================================="+member);
        return userService.userSet(member);
    }


}
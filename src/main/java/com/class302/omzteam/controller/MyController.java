package com.class302.omzteam.controller;

import com.class302.omzteam.mybatis.Dept_boardDao;
import com.class302.omzteam.service.UserService;
import com.class302.omzteam.spring.Dept_board;
import com.class302.omzteam.spring.UserCommand;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Log4j2
public class MyController {

    @Autowired
    Dept_boardDao deptBoardDao;

    @Autowired
    UserService userService;
    @GetMapping({"/test"})
    public String rest(HttpSession session, Model model){
    UserCommand user = userService.user(100001);
        session.setAttribute("user",user);
    List<Dept_board> dd1 = deptBoardDao.dbpard1(user.getDept_no());
    List<Dept_board> dd2 = deptBoardDao.dbpard0(user.getDept_no());
        model.addAttribute("dd1",dd1);
        model.addAttribute("dd2",dd2);
        return "test";
    }
}

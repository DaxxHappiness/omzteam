package com.class302.omzteam.controller;

import com.class302.omzteam.model.ApprovalDto;
import com.class302.omzteam.mybatis.ApprovalDao;
import com.class302.omzteam.mybatis.MybatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/approval")
public class MyController {

    @Autowired
    MybatisDao mybatisDao;

    @Autowired
    ApprovalDao approvalDao;

    //작성 폼으로
    @GetMapping("/writeForm")
    public String getPostForm(Model model) {
        // 폼을 렌더링하기 위한 데이터를 모델에 추가
        model.addAttribute("approvalDto", new ApprovalDto());
        return "writeForm";
    }

    //작성 결과 폼으로
    @PostMapping("/resultForm")
    public String submitPost(ApprovalDto approvalDto) {
        approvalDao.insert1(approvalDto);
        return "resultForm";
    }

    //메인으로 이동(게시판)
    @GetMapping("/main")
    public String boardMain(Model model) {
        System.out.println("-------------");
        System.out.println(approvalDao.count());
        List<ApprovalDto> list = approvalDao.findAll();
        for (ApprovalDto dto : list) {
            System.out.println(dto);
        }
        model.addAttribute("list", list);
        return "main";
    }

    //목록 버튼을 누르면 메인창으로
    @GetMapping("/goMain")
    public String goMain() {
        return "redirect:/main";
    }

    //글 하나 골라서 읽기
    @GetMapping("/goRead")
    public String goRead(@RequestParam int board_id, Model model) {
        ApprovalDto dto = approvalDao.selectOne(board_id);
        System.out.println(dto);
        model.addAttribute("approvalDto", dto);
        return "resultForm";
    }

    //작성 폼으로 이동
    @GetMapping("/doWrite")
    public String doWrite() {
        return "redirect:/writeForm";
    }

    //결재 폼 작성
    @GetMapping("/approvalWriteForm")
    public String doApproval() {


        return "approvalWriteForm";
    }

    @PostMapping("/approvalResultForm")
    public String doneApproval(@RequestParam("pass") String pass, @RequestParam("comment") String comment) {
        System.out.println(pass);
        System.out.println(comment);
        return "approvalResultForm";
    }

}

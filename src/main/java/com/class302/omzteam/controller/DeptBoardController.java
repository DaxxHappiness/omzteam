package com.class302.omzteam.controller;



import com.class302.omzteam.service.PageService;
import com.class302.omzteam.spring.*;
import com.class302.omzteam.mybatis.CommentDao;
import com.class302.omzteam.mybatis.DeptDao;
import com.class302.omzteam.mybatis.Dept_boardDao;
import com.class302.omzteam.mybatis.MemberDao;
import com.class302.omzteam.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/deptboard")
public class DeptBoardController {

    @Autowired
    MemberDao memberDao;

    @Autowired
    UserService userService;

    @Autowired
    DeptDao deptDao;

    @Autowired
    Dept_boardDao deptBoardDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    PageService pageService;

    @GetMapping("/board")
    public  void board(@RequestParam(name = "bod_no")int bod_no, HttpSession session,Model model){
        UserCommand user = (UserCommand) session.getAttribute("user");
        Dept_board deptBoard = deptBoardDao.boardOne(bod_no);
        List<Comment>commentList = commentDao.commentAll(bod_no);
        for(Comment c : commentList){
            log.info("======================="+c);
        }
        boolean authority = false;
        if (user.getMem_no() == deptBoard.getMem_no()){
            authority = true;
        }else if (user.getJob_no()<=3 && user.getJob_no() < userService.user(deptBoard.getMem_no()).getJob_no()){
            authority =true;
        }
        model.addAttribute("board",deptBoard);//글
        model.addAttribute("user",user);//사용자
        model.addAttribute("comment",commentList);//코맨트
        model.addAttribute("authority",authority);//권한
    }
    @GetMapping("/boardupdate")
    public  void update(@RequestParam(name = "bod_no")int bod_no,Model model){
        Dept_board deptBoard = deptBoardDao.boardOne(bod_no);
        model.addAttribute("board",deptBoard);
    }
    @GetMapping("/update")
    public  String updatein(@RequestParam(value = "notification",defaultValue = "0")int notification,Dept_board deptBoard, Model model){
        deptBoard.setIs_notice(notification);

        log.info("======================================"+deptBoard);
        System.out.println(deptBoard);
        deptBoardDao.updateDb(deptBoard);
    return "redirect:/deptboard/list";
    }

    @GetMapping("/boardinsert")
    public  void insert(HttpSession session,Model model){
        UserCommand user = (UserCommand) session.getAttribute("user");
        model.addAttribute("user",user);
    }
    @GetMapping("/insert")
    public String insertin(@RequestParam(value = "notification",defaultValue = "0")int notification,
                          HttpSession session, Model model,
                          Dept_board deptBoard){
        UserCommand user = (UserCommand) session.getAttribute("user");
                deptBoard.setDept_no(user.getDept_no());
                deptBoard.setMem_no(user.getMem_no());
                deptBoard.setWriter(user.getMem_name());
                deptBoard.setIs_notice(notification);
                log.info("================="+deptBoard);
        deptBoardDao.insertDb(deptBoard);
        return "redirect:/deptboard/list";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam(value = "bod_no")int bod_no){
       deptBoardDao.deleteDb(bod_no);
        return "redirect:/deptboard/list";
    }
    @GetMapping ("/comment")
    public String comment(HttpSession session,Comment comment){
       UserCommand user = (UserCommand) session.getAttribute("user");
        comment.setMem_no(user.getMem_no());
        comment.setMem_name(user.getMem_name());
        comment.setJob(user.getJob());
        commentDao.commentinsert(comment);
        return "redirect:/deptboard/board?bod_no="+comment.getBod_no();
    }
    @GetMapping ("/commentdelete")
    public String commentdelete(@RequestParam(value = "comment_no")int comment_no,
                                @RequestParam(value = "bod_no")int bod_no){

        commentDao.deleteComment(comment_no);
        return "redirect:/deptboard/board?bod_no="+bod_no;
    }
    @RequestMapping("/list")
    public void list2(HttpSession session,
                    @RequestParam(required = false,defaultValue = "1") int pageNo,
//                    @RequestParam(name = "select" ,defaultValue = "0")int select,
//                    @RequestParam(name = "value",defaultValue = "")String value,
                    Model model){
        UserCommand user = (UserCommand) session.getAttribute("user");
        Page page = pageService.Pagein(pageNo,deptBoardDao.deptBoardCount(user.getDept_no()));
        model.addAttribute("list",deptBoardDao.lists(user.getDept_no(), page.getStartNo() - 1));
        model.addAttribute("page",page);
//        List<Dept_board>list = null;
//        if(value.equals("")) {
//            Page page = pageService.Pagein(pageNo,deptBoardDao.deptBoardCount(user.getDept_no()));
//            list = deptBoardDao.lists(user.getDept_no(), page.getStartNo() - 1);
//            model.addAttribute("page",page);
//        }else {
//            if(select == 1){
//                Page page = pageService.Pagein(pageNo,deptBoardDao.nameCount(user.getDept_no(), value));
//                list = deptBoardDao.nameselect(user.getDept_no(), value,page.getStartNo() - 1);
//                model.addAttribute("page",page);
//            } else if (select ==2) {
//                Page page = pageService.Pagein(pageNo,deptBoardDao.titleCount(user.getDept_no(), value));
//                list = deptBoardDao.titleselect(user.getDept_no(), value,page.getStartNo() - 1);
//                model.addAttribute("page",page);
//            }
//        }
//        model.addAttribute("list",list);
    }

    @PostMapping("/list")
    public  void search(HttpSession session, Model model,
                        @RequestParam(name = "select")int select,
                        @RequestParam(name = "value")String value,
                        @RequestParam(required = false,defaultValue = "1") int pageNo){
        UserCommand user = (UserCommand) session.getAttribute("user");
        List<Dept_board> list = new ArrayList<>();
        if(select == 1){
            Page page = pageService.Pagein(pageNo,deptBoardDao.nameCount(user.getDept_no(),value));
            list = deptBoardDao.nameselect(user.getDept_no(), value,page.getStartNo() - 1);
            model.addAttribute("page",page);
        } else if (select ==2) {
            Page page = pageService.Pagein(pageNo,deptBoardDao.titleCount(user.getDept_no(),value));
            list = deptBoardDao.titleselect(user.getDept_no(), value,page.getStartNo() - 1);
            model.addAttribute("page",page);
        }

        model.addAttribute("list",list);

    }

}
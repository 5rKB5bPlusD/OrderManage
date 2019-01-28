package com.graduationDesign.controller;

import com.graduationDesign.model.vo.ActionResult;
import com.graduationDesign.model.po.NoticePO;
import com.graduationDesign.model.vo.NoticeVO;
import com.graduationDesign.service.impl.NoticeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    private NoticeServiceImpl noticeService;

    @RequestMapping("/load")
    public ModelAndView load(HttpServletRequest request, HttpServletResponse response) {
        String src = request.getParameter("src");
        return new ModelAndView(src);
    }

    @RequestMapping("/addNotice")
    @ResponseBody
    public ActionResult addNotice(HttpServletRequest request, HttpServletResponse response) {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int num = noticeService.addNotice(title, content);
        if (num == 1) {
            return new ActionResult(true, num, "发布成功");
        } else {
            return new ActionResult(false, null, "未知异常");
        }
    }

    @RequestMapping("/allNotice")
    @ResponseBody
    public List<NoticeVO> allNotice(HttpServletRequest request, HttpServletResponse response) {
        return noticeService.getAllNotice();
    }

    @RequestMapping("/showNotice")
    public ModelAndView showNotice(HttpServletRequest request, HttpServletResponse response) {
        int noticeId = Integer.parseInt(request.getParameter("noticeId"));
        NoticeVO noticeVO = noticeService.getNotice(noticeId);
        return new ModelAndView("notice/noticeShow", "notice", noticeVO);
    }

    @RequestMapping("/getNotice")
    @ResponseBody
    public ActionResult getNotice(HttpServletRequest request, HttpServletResponse response) {
        int noticeId = Integer.parseInt(request.getParameter("noticeId"));
        NoticeVO noticeVO = noticeService.getNotice(noticeId);
        return new ActionResult(true, noticeVO, "");
    }

    @RequestMapping("/deleteNotice")
    @ResponseBody
    public ActionResult deleteNotice(HttpServletRequest request, HttpServletResponse response) {
        int noticeId = Integer.parseInt(request.getParameter("noticeId"));
        int num = noticeService.deleteNotice(noticeId);
        if (num == 1) {
            return new ActionResult(true, num, "删除成功");
        } else {
            return new ActionResult(false, null, "未知异常");
        }
    }

    @RequestMapping(value = "/updateNotice",method = RequestMethod.POST)
    @ResponseBody
    public ActionResult updateNotice(HttpServletRequest request, HttpServletResponse response) {
        int noticeId = Integer.parseInt(request.getParameter("noticeId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int num = noticeService.updateNotice(noticeId, title, content);
        if (num == 1) {
            return new ActionResult(true, num, "修改成功");
        } else {
            return new ActionResult(false, null, "未知异常");
        }
    }

}

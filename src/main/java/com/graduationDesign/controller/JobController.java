package com.graduationDesign.controller;

import com.graduationDesign.model.po.User;
import com.graduationDesign.model.vo.ActionResult;
import com.graduationDesign.model.vo.JobVO;
import com.graduationDesign.service.impl.JobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("job")
public class JobController {

    @Autowired
    private JobServiceImpl jobService;

    @RequestMapping("/load")
    public ModelAndView load(HttpServletRequest request, HttpServletResponse response) {
        String src = request.getParameter("src");
        return new ModelAndView(src);
    }

    @RequestMapping("/recordingJob")
    @ResponseBody
    public ActionResult recordingJob(HttpServletRequest request, HttpServletResponse response) {
        String jobId = request.getParameter("jobId");
        String jobName = null;
        String speed = null;
        try {
            jobName = URLDecoder.decode(request.getParameter("jobName"), "utf-8");
            speed = URLDecoder.decode(request.getParameter("speed"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ActionResult(false, null, "编码转换异常");
        }
        int num = jobService.recordingJob((User) request.getSession().getAttribute("user"), jobId, jobName, speed);
        if (num > 0) {
            return new ActionResult(true, num, "提交成功");
        } else {
            return new ActionResult(false, null, "未知异常");
        }
    }

    @RequestMapping("/showJob")
    @ResponseBody
    public ActionResult showJob(HttpServletRequest request, HttpServletResponse response) {
        String date = request.getParameter("date");
        User user = (User) request.getSession().getAttribute("user");
        List<JobVO> list = jobService.showJob(user.getUserId(), date);
        if (list.size() == 0) {
            return new ActionResult(false, null, "无记录");
        }
        return new ActionResult(true, list, "");
    }

    @RequestMapping("/showMemberJob")
    @ResponseBody
    public ActionResult showMemberJob(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String date = request.getParameter("date");
        List<JobVO> list = jobService.showJob(userId, date);
        if (list.size() == 0) {
            return new ActionResult(false, null, "无记录");
        }
        return new ActionResult(true, list, "");
    }

    @RequestMapping("/unfinished")
    @ResponseBody
    public ActionResult unfinished(HttpServletRequest request, HttpServletResponse response) {
        List<JobVO> list = jobService.unfinished((User) request.getSession().getAttribute("user"));
        return new ActionResult(true, list, "");
    }

    @RequestMapping("/searchJob")
    @ResponseBody
    public List<JobVO> searchJob(HttpServletRequest request, HttpServletResponse response) {
        String jobName = request.getParameter("jobName");
        int teamId = Integer.parseInt(request.getParameter("teamId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        String date = request.getParameter("date");
        return jobService.searchJob(jobName, teamId, userId, date);
    }

    @RequestMapping("/searchJobLeader")
    @ResponseBody
    public List<JobVO> searchJobLeader(HttpServletRequest request, HttpServletResponse response) {
        String jobName = request.getParameter("jobName");
        int teamId = Integer.parseInt(request.getParameter("teamId"));
        String date = request.getParameter("date");
        return jobService.searchJobLeader(jobName, teamId, date);
    }

    @RequestMapping("/deleteJob")
    @ResponseBody
    public ActionResult deleteJob(HttpServletRequest request, HttpServletResponse response) {
        String jobId = request.getParameter("jobId");
        int num = jobService.deleteJob(jobId);
        if (num > 0) {
            return new ActionResult(true, num, "成功删除" + num + "条记录");
        } else {
            return new ActionResult(false, null, "未知异常");
        }
    }
}

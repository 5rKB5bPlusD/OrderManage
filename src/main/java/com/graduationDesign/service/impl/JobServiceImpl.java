package com.graduationDesign.service.impl;

import com.graduationDesign.dao.IJobDao;
import com.graduationDesign.model.po.JobPO;
import com.graduationDesign.model.po.User;
import com.graduationDesign.model.po.UserTeamPO;
import com.graduationDesign.model.vo.JobVO;
import com.graduationDesign.model.vo.UserTeamVO;
import com.graduationDesign.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class JobServiceImpl implements IJobService {

    @Autowired
    private IJobDao jobDao;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public int recordingJob(User user, String jobId, String jobName, String speed) {
        String[] jobIds = jobId.substring(0, jobId.lastIndexOf(',')).split(",");
        String[] jobNames = jobName.substring(0, jobName.lastIndexOf(',')).split(",");
        String[] speeds = speed.substring(0, speed.lastIndexOf(',')).split(",");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int num = 0;
        for (int i = 0; i < jobNames.length; i++) {
//            if (jobIds[i].equals("-1")) {
            Map<String, java.io.Serializable> map = new HashMap<String, java.io.Serializable>();
            map.put("jobName", jobNames[i]);
            map.put("speed", Integer.parseInt(speeds[i]));
            map.put("date", sdf.format(date));
            map.put("userId", user.getUserId());
            map.put("teamId", user.getTeamId());
            jobDao.insertJob(map);
            num++;
//            } else {
//                Map<String, java.io.Serializable> map = new HashMap<String, java.io.Serializable>();
//                map.put("jobId", Integer.parseInt(jobIds[i]));
//                map.put("jobName", jobNames[i]);
//                map.put("speed", Integer.parseInt(speeds[i]));
//                jobDao.updateJobById(map);
//                num++;
//            }
        }
        return num;
    }

    @Override
    public List<JobVO> showJob(int userId, String date) {
        List<JobPO> jobPOS = jobDao.selectJobByDate(userId, date);
        List<JobVO> jobVOS = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (JobPO j : jobPOS) {
            JobVO jobVO = new JobVO();
            jobVO.setJobId(j.getJobId());
            jobVO.setJobName(j.getJobName());
            jobVO.setSpeed(j.getSpeed());
            jobVO.setDate(sdf.format(j.getDate()));
            jobVO.setUserId(j.getUserId());
            jobVO.setTeamId(j.getTeamId());
            jobVO.setUsername(userService.getUser(j.getUserId()).getUsername());
            UserTeamVO userTeamVO = userService.selectUserTeamByTeamId(j.getTeamId());
            jobVO.setTeamName(userTeamVO != null ? userTeamVO.getTeamName() : "");
            jobVOS.add(jobVO);
        }
        return jobVOS;
    }

    @Override
    public List<JobVO> unfinished(User user) {
        List<JobPO> jobPOS = jobDao.selectJobUnfinishedByUserId(user.getUserId());
        List<JobVO> jobVOS = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (JobPO j : jobPOS) {
            JobVO jobVO = new JobVO();
            jobVO.setJobId(j.getJobId());
            jobVO.setJobName(j.getJobName());
            jobVO.setSpeed(j.getSpeed());
            jobVO.setDate(sdf.format(j.getDate()));
            jobVO.setUserId(j.getUserId());
            jobVO.setTeamId(j.getTeamId());
            jobVO.setUsername(userService.getUser(j.getUserId()).getUsername());
            UserTeamVO userTeamVO = userService.selectUserTeamByTeamId(j.getTeamId());
            jobVO.setTeamName(userTeamVO != null ? userTeamVO.getTeamName() : "");
            jobVOS.add(jobVO);
        }
        return jobVOS;
    }

    @Override
    public List<JobVO> searchJob(String jobName, int teamId, int userId, String date) {
        Map<String, java.io.Serializable> map = new HashMap<String, java.io.Serializable>();
        map.put("jobName", jobName);
        map.put("teamId", teamId);
        map.put("userId", userId);
        map.put("date", date);
        List<JobPO> jobPOS = jobDao.selectJobBySearch(map);
        List<JobVO> jobVOS = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (JobPO j : jobPOS) {
            JobVO jobVO = new JobVO();
            jobVO.setJobId(j.getJobId());
            jobVO.setJobName(j.getJobName());
            jobVO.setSpeed(j.getSpeed());
            jobVO.setDate(sdf.format(j.getDate()));
            jobVO.setUserId(j.getUserId());
            jobVO.setTeamId(j.getTeamId());
            jobVO.setUsername(userService.getUser(j.getUserId()).getUsername());
            UserTeamVO userTeamVO = userService.selectUserTeamByTeamId(j.getTeamId());
            if (userTeamVO == null) {
                UserTeamPO userTeamPO = userService.selectUserTeamByLeaderId(j.getUserId());
                jobVO.setTeamName(userTeamPO != null ? userTeamPO.getTeamName() + "负责人" : "无");
            } else {
                jobVO.setTeamName(userTeamVO.getTeamName() + "成员");
            }
            jobVOS.add(jobVO);
        }
        return jobVOS;
    }

    @Override
    public List<JobVO> searchJobLeader(String jobName, int teamId, String date) {
        int leaderId = userService.selectUserTeamByTeamId(teamId).getLeaderId();
        return searchJob(jobName, -1, leaderId, date);
    }

    @Override
    public int deleteJob(String jobId) {
        String[] jobIds = jobId.substring(0, jobId.lastIndexOf(',')).split(",");
        int num = 0;
        for (String s : jobIds) {
            jobDao.deleteJobById(Integer.parseInt(s));
            num++;
        }
        return num;
    }
}

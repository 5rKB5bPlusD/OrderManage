package com.graduationDesign.service;

import com.graduationDesign.model.po.JobPO;
import com.graduationDesign.model.po.User;
import com.graduationDesign.model.vo.JobVO;

import java.util.List;

public interface IJobService {

    public int recordingJob(User user, String jobId, String jobName, String speed);

    public List<JobVO> showJob(int userId, String date);

    public List<JobVO> unfinished(User user);

    public List<JobVO> searchJob(String jobName, int teamId, int userId, String date);

    public List<JobVO> searchJobLeader(String jobName, int teamId, String date);

    public int deleteJob(String jobId);
}

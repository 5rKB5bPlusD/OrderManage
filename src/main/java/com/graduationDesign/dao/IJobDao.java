package com.graduationDesign.dao;

import com.graduationDesign.model.po.JobPO;
import com.graduationDesign.model.po.UserTeamPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IJobDao {

    public int insertJob(Map map);

    public List<JobPO> selectJobByDate(@Param("userId") int userId, @Param("date") String date);

    public List<JobPO> selectJobUnfinishedByUserId(int userId);

    public int updateJobById(Map map);

    public List<JobPO> selectJobBySearch(Map map);

    public int deleteJobById(int jobId);
}

package com.graduationDesign.dao;

import com.graduationDesign.model.po.NoticePO;

import java.util.List;
import java.util.Map;

public interface INoticeDao {

    public int addNotice(Map map);

    public List<NoticePO> getAllNotice();

    public NoticePO getNotice(int noticeId);

    public int deleteNotice(int noticeId);

    public int updateNotice(Map map);
}

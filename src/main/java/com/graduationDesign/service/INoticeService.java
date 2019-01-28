package com.graduationDesign.service;

import com.graduationDesign.model.vo.NoticeVO;

import java.util.List;

public interface INoticeService {

    public int addNotice(String title, String content);

    public List<NoticeVO> getAllNotice();

    public NoticeVO getNotice(int noticeId);

    public int deleteNotice(int noticeId);

    public int updateNotice(int noticeId, String title, String content);
}

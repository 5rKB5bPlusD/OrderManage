package com.graduationDesign.service.impl;

import com.graduationDesign.dao.INoticeDao;
import com.graduationDesign.model.po.NoticePO;
import com.graduationDesign.model.vo.NoticeVO;
import com.graduationDesign.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NoticeServiceImpl implements INoticeService {

    @Autowired
    private INoticeDao noticeDao;

    @Override
    public int addNotice(String title, String content) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> map = new HashMap<String, String>();
        map.put("title", title);
        map.put("date", sdf.format(new Date()));
        map.put("content", content);
        return noticeDao.addNotice(map);
    }

    @Override
    public List<NoticeVO> getAllNotice() {
        List<NoticePO> list = noticeDao.getAllNotice();
        List<NoticeVO> noticeVOList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (NoticePO n : list) {
            NoticeVO noticeVO = new NoticeVO();
            noticeVO.setNoticeId(n.getNoticeId());
            noticeVO.setTitle(n.getTitle());
            noticeVO.setDate(sdf.format(n.getDate()));
            noticeVO.setContent(n.getContent());
            noticeVOList.add(noticeVO);
        }
        return noticeVOList;
    }

    @Override
    public NoticeVO getNotice(int noticeId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        NoticePO noticePO = noticeDao.getNotice(noticeId);
        NoticeVO noticeVO = new NoticeVO();
        noticeVO.setNoticeId(noticePO.getNoticeId());
        noticeVO.setTitle(noticePO.getTitle());
        noticeVO.setDate(sdf.format(noticePO.getDate()));
        noticeVO.setContent(noticePO.getContent());
        return noticeVO;
    }

    @Override
    public int deleteNotice(int noticeId) {
        return noticeDao.deleteNotice(noticeId);
    }

    @Override
    public int updateNotice(int noticeId, String title, String content) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, java.io.Serializable> map = new HashMap<String, java.io.Serializable>();
        map.put("noticeId", noticeId);
        map.put("title", title);
        map.put("content", content);
        map.put("date", sdf.format(new Date()));
        return noticeDao.updateNotice(map);
    }
}

package com.somei.bbs.domain.repository;

import com.somei.bbs.domain.model.Message;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface BbsDao {

    // insert(投稿)
    public int insertOne(Message message) throws DataAccessException;

    // １件のデータを取得
    public Message selectOne(int auto_no) throws  DataAccessException;

    // 全データを取得
    public List<Message> selectMany() throws DataAccessException;

    // １件削除
    public int deleteOne(int auto_no) throws DataAccessException;

}

package com.somei.bbs.domain.service;

import com.somei.bbs.domain.model.Message;
import com.somei.bbs.domain.repository.BbsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BbsService {

    @Autowired
    BbsDao dao;

    // insert用メソッド
    public boolean insert(Message message) {

        // insert実行
        int rowNumber = dao.insertOne(message);

        // 判定用変数
        boolean result = false;

        if(rowNumber > 0) {
            // insert成功
            result = true;
        }

        return result;
    }

    // 全件取得メソッド
    public List<Message> selectMany() {
        // 全件取得
        return dao.selectMany();
    }

    // １件取得用メソッド
    public Message selectOne(int auto_no) {
        // selectOne 実行
        return dao.selectOne(auto_no);
    }

    // １件削除メソッド
    public boolean deleteOne(int auto_no) {

        // １件削除
        int rowNumber = dao.deleteOne(auto_no);

        // 判定用変数
        boolean result = false;

        if(rowNumber > 0) {
            // delete成功
            result = true;
        }

        return result;
    }
}

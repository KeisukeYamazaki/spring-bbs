package com.somei.bbs.domain.repository.jdbc;

import com.somei.bbs.domain.model.Message;
import com.somei.bbs.domain.repository.BbsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class BbsDaoJdbcImpl implements BbsDao {

    @Autowired
    JdbcTemplate jdbc;

    // insert(投稿)
    @Override
    public int insertOne(Message message) throws DataAccessException {

        // １件登録
        int rowNumber = jdbc.update("INSERT INTO message(name,"
                + " message,"
                + " date,"
                + " deletePassword)"
                + " VALUES (?, ?, current_timestamp AT TIME ZONE 'Asia/Tokyo', ?)"
                ,message.getName()
                ,message.getMessage()
                ,message.getDeletePassword());

        return rowNumber;
    }

    @Override
    public Message selectOne(int auto_no) throws DataAccessException {

        Map<String, Object> map = jdbc.queryForMap("SELECT * FROM message WHERE auto_no = ?" ,auto_no);

        // 結果返却用の変数
        Message message = new Message();

        // 取得したデータを結果返却用の変数にセットしていく
        message.setAuto_no((Integer)map.get("auto_no"));
        message.setName((String)map.get("name"));
        message.setMessage((String)map.get("message"));
        message.setDate((Date)map.get("date"));
        message.setDeletePassword((String)map.get("deletepassword"));

        return message;
    }

    // 全データを取得
    @Override
    public List<Message> selectMany() throws DataAccessException {

        // messageテーブルのデータを全件取得（新しい投稿順）
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM message ORDER BY date DESC");

        // 結果返却用の変数
        List<Message> messageList = new ArrayList<>();

        // 取得したデータを結果返却用のListに格納していく
        for(Map<String, Object> map : getList) {

            // Messageインスタンスの作成
            Message message = new Message();

            // Messageインスタンスに取得したデータをセットする
            message.setAuto_no((Integer)map.get("auto_no"));
            message.setName((String)map.get("name"));
            message.setMessage((String)map.get("message"));
            message.setDate((Date)map.get("date"));
            message.setDeletePassword((String)map.get("deletepassword"));

            // 結果返却用のListに追加
            messageList.add(message);
        }

        return messageList;
    }

    // 投稿を１件削除
    @Override
    public int deleteOne(int auto_no) throws DataAccessException {

        // １件削除
        int rowNumber = jdbc.update("DELETE FROM message WHERE auto_no = ?", auto_no);

        return  rowNumber;
    }
}

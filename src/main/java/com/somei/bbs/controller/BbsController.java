package com.somei.bbs.controller;

import com.somei.bbs.domain.model.Message;
import com.somei.bbs.domain.model.MessageForm;
import com.somei.bbs.domain.service.BbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class BbsController {

    @Autowired
    private BbsService bbsService;

    @GetMapping("/index")
    public String getIndex(@ModelAttribute MessageForm form, Model model) {

        // メッセージ一覧の生成
        List<Message> messageList = bbsService.selectMany();

        // Modelにメッセージリストを登録
        model.addAttribute("messageList", messageList);

        return "index";
    }

    @PostMapping("/index")
    public String postIndex(@ModelAttribute MessageForm form) {
        // message の中身をコンソールに出して確認
        System.out.println(form);

        // insert用変数
        Message message = new Message();

        message.setName(form.getName());
        message.setMessage(form.getMessage());
        // 現在時刻を取得してmessageにセット
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        message.setDate(timestamp);

        // メッセージ登録処理
        boolean result = bbsService.insert(message);

        // メッセージ登録結果の判定
        if(result == true) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");
        }

        // リダイレクト
        return "redirect:/index";
    }
}

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
import org.springframework.web.bind.annotation.RequestParam;

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
        message.setDeletePassword(form.getDeletePassword());
        
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

    @PostMapping(value ="/index", params = "delete")
    public String postDeleteIndex(@ModelAttribute MessageForm form,
                                  @RequestParam(name ="deletepass")String deletepass,
                                  @RequestParam(name ="number")int num,
                                  Model model) {

        System.out.println("削除ボタンの処理");

        // メッセージの情報を取得（selectOne）
        Message message = bbsService.selectOne(num);

        // 削除実行
        if(message.getDeletePassword().equals(deletepass)) {
            boolean result = bbsService.deleteOne(num);

            if(result == true) {
                model.addAttribute("result", "削除しました");
            } else {
                model.addAttribute("result","削除できませんでした");
            }
        } else {
            model.addAttribute("result","削除パスワードが違うため、削除できません");
        }

        return getIndex(form, model);
    }
}

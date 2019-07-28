package com.somei.bbs.domain.model;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private int auto_no;
    private String name;
    private String message;
    private Date date;
}

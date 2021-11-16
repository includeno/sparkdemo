package com.example.kafka;

import java.io.Serializable;
import java.util.Date;

public class KafkaMessage implements Serializable {

    String url;
    String content;
    Date time;

    public KafkaMessage(){

    }
    public KafkaMessage(String url, String content, Date time) {
        this.url = url;
        this.content = content;
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}

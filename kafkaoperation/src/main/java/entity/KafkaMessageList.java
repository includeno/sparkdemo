package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KafkaMessageList {
    List<KafkaMessage> list=new ArrayList<>();


    public KafkaMessageList(){

    }

    public KafkaMessageList(List<KafkaMessage> list){
        this.list=list;
        Collections.sort(this.list,(a,b)->{return a.time.compareTo(b.time);});//输入时排序
    }

    public List<KafkaMessage> getList() {
        return list;
    }

    public void setList(List<KafkaMessage> list) {
        this.list = list;
    }


}

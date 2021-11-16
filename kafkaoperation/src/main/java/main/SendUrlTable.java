package main;

import com.google.gson.Gson;
import config.Constants;
import config.KafkaConfig;
import entity.KafkaMessage;
import entity.KafkaMessageList;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import utils.KafkaUtil;
import utils.PropertiesUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class SendUrlTable {
    public static ZoneId zoneId = ZoneId.systemDefault();

    public static Date getNow(){
        return Date.from(LocalDateTime.now().atZone(zoneId).toInstant());
    }

    public static void main(String[] args) {
        Properties properties= KafkaConfig.getProperties();
        Gson gson=new Gson();
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        List<KafkaMessage> messages=new ArrayList<>();

        messages.add(new KafkaMessage("ssa","sadfghmnbvcd", getNow()));
        messages.add(new KafkaMessage("xsxsasd","sdfgbhnjmkhgfdsaxdcbvnjghgtrfdfvnjmnhgdfsd", getNow()));
        messages.add(new KafkaMessage("ssa","23e4rtgfdctmyrterfbnmu654efghmjkujy5t4wefdsfghmj", getNow()));
        messages.add(new KafkaMessage("tttwerfgytrwesdvbert","sadfghmnbvcd", getNow()));
        String keyword="象棋";
        KafkaMessageList list=new KafkaMessageList(messages);
        String topics=PropertiesUtil.getProperty(Constants.KafkaConfigFileName, Constants.KAFKA_TOPICS);
        sendByGson(producer,topics,keyword,gson.toJson(list,KafkaMessageList.class));

    }

    public static boolean sendByGson(KafkaProducer<String, String> producer,String topic,String key,String value){
        ProducerRecord<String,String> record=new ProducerRecord<>(topic,key,value);
        try{
            producer.send(record).get();
            System.out.println("succeed");
        }
        catch (Exception e){

        }
        return true;
    }
}

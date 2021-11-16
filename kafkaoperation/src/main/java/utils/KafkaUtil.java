package utils;

import config.Constants;
import config.KafkaConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//https://kafka.apache.org/documentation/#producerapi
//用于发送kafka消息
public class KafkaUtil {

    public static void main(String[] args) {
        Properties properties=KafkaConfig.getProperties();

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        //String topic, K key, V value
        String topic="mytopic";
        List<String> messages=new ArrayList<>();
        for (int i=0;i<5;i++){
            messages.add("1212");
        }
        for (int i=0;i<5;i++){
            messages.add("xxxxsdwsd");
        }
        for (int i=0;i<7;i++){
            messages.add("23323");
        }
        for(String st:messages){
            System.out.println("sdfdsdfs");
            send(producer,topic,st,"133243");
        }

    }


    public static boolean send(KafkaProducer<String, String> producer,String topic,String key,String value){
        ProducerRecord<String,String> record=new ProducerRecord<>(topic,key,value);
        try{
            producer.send(record).get();
            System.out.println("succeed");
        }
        catch (Exception e){

        }
        return true;
    }

    public static boolean sendByGson(KafkaProducer<String, Object> producer,String topic,String key,Object value){
        ProducerRecord<String,Object> record=new ProducerRecord<>(topic,key,value);
        try{
            producer.send(record).get();
            System.out.println("succeed");
        }
        catch (Exception e){

        }
        return true;
    }
}

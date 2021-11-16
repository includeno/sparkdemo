package com.example.kafka;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.sql.*;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

//每条消息到来的时候跑一次
public class WorkWithMessage {
    public static SparkSession spark;
    public static SparkConf conf;
    public static JavaStreamingContext jssc;

    public static ZoneId zoneId = ZoneId.systemDefault();

    public static Date getNow(){
        return Date.from(LocalDateTime.now().atZone(zoneId).toInstant());
    }

    public static void main(String[] args) {

        // 构建SparkStreaming上下文
        conf = new SparkConf().setAppName("BlazeDemo").setMaster("local[2]");

        // 每隔1秒钟，sparkStreaming作业就会收集最近1秒内的数据源接收过来的数据
        jssc = new JavaStreamingContext(conf, Durations.seconds(5));
        //checkpoint目录
        //jssc.checkpoint(ConfigurationManager.getProperty(Constants.STREAMING_CHECKPOINT_DIR));
        //jssc.checkpoint("/streaming_checkpoint");
        jssc.checkpoint("chekpoint_message");

        try {
            // 获取kafka的数据
            final JavaInputDStream<ConsumerRecord<String, String>> stream=KafkaConfig.getKafkaStream(jssc);
            spark = SparkSession
                    .builder()
                    .appName("Java Spark SQL basic example")
                    .getOrCreate();

            stream.foreachRDD(new VoidFunction<JavaRDD<ConsumerRecord<String, String>>>() {
                @Override
                public void call(JavaRDD<ConsumerRecord<String, String>> consumerRecordJavaRDD) throws Exception {
                    if(consumerRecordJavaRDD==null){
                        return;
                    }

                    System.out.println("VoidFunction");
                    List<KafkaMessage> messageList=new ArrayList<>();

                    messageList=consumerRecordJavaRDD.flatMap(new FlatMapFunction<ConsumerRecord<String, String>, KafkaMessage>() {
                        @Override
                        public Iterator<KafkaMessage> call(ConsumerRecord<String, String> stringStringConsumerRecord) throws Exception {
                            Gson gson = new Gson();

                            KafkaMessageList messageList2 = gson.fromJson(String.valueOf(stringStringConsumerRecord.value()), KafkaMessageList.class);

                            return messageList2.getList().iterator();
                        }
                    }).map(new Function<KafkaMessage, KafkaMessage>() {
                        @Override
                        public KafkaMessage call(KafkaMessage kafkaMessage) throws Exception {
                            return kafkaMessage;
                        }
                    }).collect();

                    Encoder<KafkaMessage> personEncoder = Encoders.bean(KafkaMessage.class);
                    Dataset<KafkaMessage> javaBeanDS = spark.createDataset(
                            messageList,
                            personEncoder
                    );
                    javaBeanDS.show(3);


                }
            });


            // Encoders are created for Java beans
            System.out.println("spark:"+spark==null);

            stream.foreachRDD(new VoidFunction2<JavaRDD<ConsumerRecord<String, String>>, Time>() {
                @Override
                public void call(JavaRDD<ConsumerRecord<String, String>> consumerRecordJavaRDD, Time time) throws Exception {
//                    SparkSession spark = SparkSession
//                            .builder()
//                            .appName("Java Spark SQL basic example")
//                            .config(conf)
//                            .getOrCreate();
//                    //System.out.println(consumerRecordJavaRDD.collect());;
//                    //String转换为KafkaMessage
//                    Gson gson=new Gson();
//                    List<ConsumerRecord<String, String>> msList = consumerRecordJavaRDD.collect();
//                    List<KafkaMessage> list=new ArrayList<>();
//                    for(ConsumerRecord<String, String> temp:msList){
//                        KafkaMessageList messageList = gson.fromJson(String.valueOf(temp.value()), KafkaMessageList.class);
//                        list.addAll(messageList.getList());
//                    }



//                    messageStream.foreachRDD(new VoidFunction2<JavaRDD<KafkaMessageList>, Time>() {
//                        @Override
//                        public void call(JavaRDD<KafkaMessageList> kafkaMessageListJavaRDD, Time time) throws Exception {
//                            for(KafkaMessageList temp: kafkaMessageListJavaRDD.collect()){
//                                list.addAll(temp.getList());
//                            }
//                        }
//                    });

                }
            });


            jssc.start();
            jssc.awaitTermination();
            jssc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

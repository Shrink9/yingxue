package com.example.yingxue.util.mq;

import com.example.yingxue.common.video.vo.VideoVo;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
//指定这个类里面负责监听消息队列的方法要监听的队列,可以监听多个队列.
@RabbitListener(queues=VideoVo.MQ_QUEUE_NAME)
public class VideoVoListener{
    @Autowired
    private Gson gson;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @RabbitHandler
    //RabbitHandler方法的参数不是固定的,可以视情况而定.
    public void listenVideoVo(VideoVo videoVo,Channel channel,Message message){
        //当前消息的deliveryTag
        long deliveryTag=message.getMessageProperties()
                                .getDeliveryTag();
        try{
            //将消息存放到ES中
            IndexRequest indexRequest=new IndexRequest(VideoVo.ES_INDEX_NAME);
            //一般用Gson将对象转为json
            indexRequest.source(gson.toJson(videoVo),XContentType.JSON).type("_doc");
            IndexResponse index=restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
            //向消息队列发送ack
            //参数1:要ack的消息的deliveryTag,参数2:是否ack当前消费者现有的所有未返回ack或者unack的消息.
            channel.basicAck(deliveryTag,false);
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}

package com.example.yingxue.video.mq;

import com.example.yingxue.common.user.entity.Played;
import com.example.yingxue.common.video.entity.Video;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

//指定这个类里面负责监听消息队列的方法要监听的队列,可以监听多个队列.
//RabbitListener和RabbitHandler的关系:每一个RabbitListener代表一个消费者,而RabbitHandler只是它的附属.
//RabbitListener持有多个消息时,会依次处理而非并行处理.
//RabbitListener对每一个消息,按消息数据类型把它分配给能处理的方法,如果没有能处理的方法就会报异常,且最终[这条]消息既不ack也不unacke,就一直拿着这个消息.
@Component
@RabbitListener(queues=Video.MQ_QUEUE_NAME)
public class PlayedListener{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //监听的队列是RabbitListener指定的那些,且只会处理消息数据类型为User的.
    @RabbitHandler
    //RabbitHandler方法的参数不是固定的,可以视情况而定.
    //User:当前消息存储的数据 Channel:一个连接可以产生多个通道,通道理解为生产者或者消费者. Message:当前消息的完整信息
    public void receivePlay(Integer videoId,Channel channel,Message message) throws Exception{
        //当前消息的deliveryTag
        long deliveryTag=message.getMessageProperties()
                                .getDeliveryTag();
        try{
            String playCount=(String)stringRedisTemplate.opsForHash()
                                                .get(Played.PLAY_COUNT_KEY,String.valueOf(videoId));
            if(playCount==null){
                playCount="1";
            }
            else{
                playCount=String.valueOf(NumberUtils.toInt(playCount)+1);
            }
            stringRedisTemplate.opsForHash()
                               .put(Played.PLAY_COUNT_KEY,String.valueOf(videoId),playCount);
        }
        catch(Exception e){
            channel.basicNack(deliveryTag,false,true);
            return;
        }
        //参数1:要ack的消息的deliveryTag,参数2:是否ack当前消费者现有的所有未返回ack或者unack的消息.
        channel.basicAck(deliveryTag,false);
    }
}

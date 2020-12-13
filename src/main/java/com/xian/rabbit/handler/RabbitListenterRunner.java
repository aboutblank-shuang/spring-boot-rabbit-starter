package com.xian.rabbit.handler;

import com.xian.rabbit.enums.RabbitMQEnums;
import com.xian.rabbit.service.ConsumerHandlerService;
import com.xian.rabbit.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 9:58 PM 2020/12/12
 */
@Slf4j
@Component
public class RabbitListenterRunner implements ApplicationRunner {

    public static final String CONTAINER_SUFFIX = "Container";


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Environment env = SpringContextHolder.getBean( Environment.class );
        ConnectionFactory connectionFactory = SpringContextHolder.getBean( ConnectionFactory.class );
        List<ConsumerHandlerService> consumerHandlerServices = SpringContextHolder.getBeansOfTypeList(ConsumerHandlerService.class);

//        String property = env.getProperty( "spring.application.name" );
//        List<RabbitMQEnums> list = new ArrayList<>(Arrays.asList( RabbitMQEnums.values() ));
//        for (RabbitMQEnums mqEnums : RabbitMQEnums.values()) {
//            // 死信队列 不创建消费者
//            if(property.equals( mqEnums.getOwnedApplication() ) && null == mqEnums.getDeclareEnums().getDeadQueue()){
//                for (ConsumerHandlerService handlerService : consumerHandlerServices) {
//                    if(handlerService.isMatch( mqEnums )){
//                        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer( connectionFactory );
//                        log.info( "listenQueue start {},currentProject {}",mqEnums.getQueueName(),property );
//                        container.setQueueNames(mqEnums.getQueueName());
//                        container.setPrefetchCount(mqEnums.getConsumerEnums().getPrefetchCount());
//                        container.setConcurrentConsumers(mqEnums.getConsumerEnums().getConcurrentConsumers());
//                        //设置确认模式为手工确认
//                        if(mqEnums.getConsumerEnums().getAcknowledgeMode()){
//                            container.setAcknowledgeMode( AcknowledgeMode.MANUAL);
//                        }else {
//                            container.setAcknowledgeMode( AcknowledgeMode.AUTO );
//                        }
//                        container.setExposeListenerChannel( true );
//                        //监听处理类
//                        container.setMessageListener( handlerService);
//                        container.start();
//                        SpringContextHolder.registerBean(mqEnums.getQueueName() + CONTAINER_SUFFIX,container);
//                        list.remove( mqEnums );
//                    }
//                }
//            }
//        }
//        if(!list.isEmpty()){
//            log.warn("No consumers in the current queues {}", list );
//        }
    }

}

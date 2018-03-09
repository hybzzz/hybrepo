package com.hyb.activemq;

import com.hyb.util.Utils;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import javax.jms.*;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by admin on 2017/12/22.
 */
public class AMQSender implements MessageListener{

    private ApplicationContext context;
    //开启接收消息线程
    public void start(){
        ConnectionFactory factory ;
        Connection conn ;
        Destination replyDest ;
        Session session ;
        MessageConsumer consumer ;
        try {
            replyDest = (Destination) context.getBean("replyDestination");
            factory = (ActiveMQConnectionFactory) context.getBean("targetConnectionFactory");
            conn = factory.createConnection();
            conn.start();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            consumer = session.createConsumer(replyDest);
            consumer.setMessageListener(this);
        } catch (Exception e) {
            String errorMessage = "JMSException while queueing HTTP JMS Message";
            System.out.println(errorMessage);
            e.printStackTrace();
        }
    }
    public void send() {
        context = new ClassPathXmlApplicationContext("classpath:activemq.xml");
        ActiveMQConnectionFactory factory ;
        Connection conn = null;
        Destination destination ;
        Session session = null;
        MessageProducer producer = null;
        MessageConsumer consumer ;
        Destination replyDest ;
        try {
            destination = (Destination) context.getBean("destination");
            factory = (ActiveMQConnectionFactory) context.getBean("targetConnectionFactory");
            conn = factory.createConnection();
            conn.start();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //通过队列创建生产者
            producer = session.createProducer(destination);
            Message message = session.createTextMessage("PING");
            ObjectMessage message1 = session.createObjectMessage();
            message1.setObject(new Date());
            String id = Utils.getUniqueNo();
            replyDest = (Destination) context.getBean("replyDestination");
            //通过队列创建消费者
            message.setJMSReplyTo(replyDest);
            message.setJMSCorrelationID(id);
            consumer = session.createConsumer(replyDest);
            consumer.setMessageListener(this);
            //在这个destination队列中是生产者 在replyDestination队列 是消费者
            producer.send(message);
            message1.setJMSReplyTo(replyDest);
            message1.setJMSCorrelationID(Utils.getUniqueNo());
            producer.send(message1);
            System.out.println("send......" + Thread.currentThread().getId());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                session.rollback();
            } catch (Exception ex) {
            }
        } finally {
            try {
                producer.close();
            } catch (Exception e) {
            }
//            try {
//                session.close();
//            } catch (Exception e) {
//            }
//            try {
//                conn.stop();
//            } catch (Exception e) {
//            }
//            try {
//                conn.close();
//            } catch (Exception e) {
//            }
        }
    }

    public static void main(String[] args) {
        AMQSender aMQSender = new AMQSender();
        aMQSender.send();
//        aMQSender.start();
    }

    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            System.out.println("发送者收到消息：" + tm.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

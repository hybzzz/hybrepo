package com.hyb.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2017/12/22.
 */
public class TranQConsumer  implements MessageListener {

    private Connection conn = null;
    private Destination destination = null;
    private Session session = null;
    private Destination replyDest = null;
    private MessageProducer producer = null;
    private MessageConsumer consumer = null;
    public void receive() {
        ConnectionFactory factory ;
        Connection conn ;
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:activemq.xml");
            factory = (ActiveMQConnectionFactory) context.getBean("targetConnectionFactory");
            conn = factory.createConnection();
            conn.start();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = (Destination) context.getBean("destination");
            replyDest = (Destination) context.getBean("replyDestination");
            consumer = session.createConsumer(destination);
            producer=session.createProducer(replyDest);
            consumer.setMessageListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onMessage(Message message) {
        System.out.println("on message");
        //作为消费者接受第一个队列的消息
        try {
            TextMessage response = this.session.createTextMessage();
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage) message;
                if(tm.getText().equals("PING")){
                    response.setText("PONG");
                }else{
                    response.setText("服务器收到消息:" + tm.getText());
                }
                System.out.println("TranQConsumer receive message: " + tm.getText());
            }else if(message instanceof ObjectMessage){
                ObjectMessage objectMessage = (ObjectMessage) message;
                Date date = (Date) objectMessage.getObject();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String s = sdf.format(date);
                System.out.println(s);
                System.out.println("--------------------");
            }
            response.setJMSCorrelationID(message.getJMSCorrelationID());
            //发送响应消息 这时候 以第二个队列 作为生产者发送
            producer.send(message.getJMSReplyTo(), response);
//            throw new Exception("error");
//            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                session.rollback();
            } catch (Exception ex) {
            }
        }

    }
    public static void main(String[] args) {
        TranQConsumer tranConsumer = new TranQConsumer();
        tranConsumer.receive();
    }
}

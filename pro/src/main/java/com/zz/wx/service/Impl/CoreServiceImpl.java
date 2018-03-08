package com.zz.wx.service.Impl;

import com.zz.wx.bean.Article;
import com.zz.wx.message.resp.NewsMessage;
import com.zz.wx.message.resp.TextMessage;
import com.zz.wx.service.CoreService;
import com.zz.wx.util.MessageUtil;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/10/11.
 */
@Service("coreService")
public class CoreServiceImpl implements CoreService {
    public static Logger log = Logger.getLogger(CoreServiceImpl.class);


    @Override
    public String processRequest(HttpServletRequest req) {
        String respMsg=null;
        try {
            Map<String, String> map = MessageUtil.parseXml(req);
            System.out.println(map);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            if("text".equals(msgType)){
                String content = map.get("Content");
                if("1".equals(content)){//响应文本消息
                    TextMessage text = new TextMessage();
                    text.setFromUserName(toUserName);         // 发送和回复是反向的
                    text.setToUserName(fromUserName);
                    text.setMsgType("text");
                    text.setCreateTime(new Date().getTime());
                    text.setContent("hello 你好 mmp");
                    respMsg = MessageUtil.textMessageToXml(text);
                    return respMsg;
                }else if ("2".equals(content)){//响应图文消息
                    NewsMessage newsMessage = new NewsMessage();
                    newsMessage.setToUserName(fromUserName);
                    newsMessage.setFromUserName(toUserName);
                    newsMessage.setCreateTime(new Date().getTime());
                    newsMessage.setMsgType("news");
                    List<Article> articleList = new ArrayList<Article>();
                    Article article = new Article();
                    article.setTitle("我是一条单图文消息");
                    article.setDescription("我是描述信息，哈哈哈哈哈哈哈。。。");
                    article.setPicUrl("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1558820315,12085182&fm=11&gp=0.jpg");
                    article.setUrl("http://47.95.202.173/imgs/upload/1.jpg");
                    articleList.add(article);
                    // 设置图文消息个数
                    newsMessage.setArticleCount(articleList.size());
                    // 设置图文消息包含的图文集合
                    newsMessage.setArticles(articleList);
                    // 将图文消息对象转换成xml字符串
                    respMsg = MessageUtil.newsMessageToXml(newsMessage);
                    return respMsg;
                }else if ("3".equals(content)){//响应多图文消息
                    NewsMessage newsMessage = new NewsMessage();
                    newsMessage.setToUserName(fromUserName);
                    newsMessage.setFromUserName(toUserName);
                    newsMessage.setCreateTime(new Date().getTime());
                    newsMessage.setMsgType("news");
                    List<Article> articleList = new ArrayList<Article>();
                    Article article = new Article();
                    article.setTitle("我是一条多图文消息");
                    article.setDescription("我是多图文描述信息，哈哈哈哈哈哈哈。。。");
                    article.setPicUrl("http://47.95.202.173/imgs/upload/header1.jpg");
                    articleList.add(article);
                    Article article2 = new Article();
                    article2.setTitle("我是一条单图文消息");
                    article2.setDescription("我是多图文描述信息，哈哈哈哈哈哈哈。。。");
                    article2.setPicUrl("http://47.95.202.173/imgs/upload/51.png");
                    articleList.add(article2);
                    Article article3= new Article();
                    article3.setTitle("我是一条单图文消息");
                    article3.setDescription("我是多图文描述信息，哈哈哈哈哈哈哈。。。");
                    article3.setPicUrl("http://47.95.202.173/imgs/upload/header0.jpg");
                    articleList.add(article3);
                    Article article4= new Article();
                    article4.setTitle("我是一条单图文消息");
                    article4.setDescription("我是多图文描述信息，哈哈哈哈哈哈哈。。。");
                    article4.setPicUrl("http://47.95.202.173/imgs/upload/header2.jpg");
                    articleList.add(article4);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    respMsg = MessageUtil.newsMessageToXml(newsMessage);
                    return respMsg;
                }else{
                    TextMessage text = new TextMessage();
                    text.setFromUserName(toUserName);         // 发送和回复是反向的
                    text.setToUserName(fromUserName);
                    text.setMsgType("text");
                    text.setCreateTime(new Date().getTime());
                    text.setContent(content);
                    respMsg = MessageUtil.textMessageToXml(text);
                    return respMsg;
                }
            }else if ("image".equals(msgType)){

            }else if ("voice".equals(msgType)){

            }else if ("shortvideo".equals(msgType)){

            }else if ("video".equals(msgType)){

            }else if ("location".equals(msgType)){

            }else if ("link".equals(msgType)){

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMsg;
    }
}

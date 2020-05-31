package com.hhu.bbs.service.impl;

import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.util.format.FormatResultGenerator;
import com.hhu.bbs.dao.MessageDao;
import com.hhu.bbs.entity.Message;
import com.hhu.bbs.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *  私信 Service
 * @name  MessageServiceImpl
 * @author  nlby
 * @date  2020/5/14
 */
@Service
public class MessageServiceImpl implements MessageService {
    private MessageDao messageDao;
    @Autowired
    public MessageServiceImpl( MessageDao messageDao){
        this.messageDao = messageDao;
    }

    /**
     * 查看用户所有私信 按接收方分组
     * @param id
     * @return
     */
    @Override
    public FormatResult<  List<Map<String, Object>>> findAllMessageByUserId(BigInteger id) {
        List<Map<String, Object>> list = messageDao.findAllMessageByUserId(id);
        return FormatResultGenerator.genSuccessResult(list);
    }

    /**
     * 发送私信 （添加私信)
     * @param
     * @return
     */
    @Override
    public FormatResult<BigInteger> addMessage(BigInteger sender_id, BigInteger receiver_id, String content) {
        Message message = new Message();
        message.setSenderId(sender_id);
        message.setReceiverId(receiver_id);
        message.setContent(content);
        messageDao.addMessage(message);
        if(messageDao.isContact(sender_id, receiver_id) != null){
            messageDao.updateContact(sender_id, receiver_id, message.getId());
            messageDao.updateContact(receiver_id, sender_id, message.getId());
        }else{
            messageDao.addContact(sender_id,receiver_id, message.getId());
            messageDao.addContact(receiver_id, sender_id, message.getId());
        }
        return FormatResultGenerator.genSuccessResult(message.getId());
    }

    /**
     * 根据两个人的id查找所有的聊天记录
     * @param user_one
     * @param user_two
     * @return
     */
    @Override
    public FormatResult<List<Map<String, Object>>> findAllMessageByTwoId(BigInteger user_one, BigInteger user_two) {
        List<Map<String, Object>> list = messageDao.findAllMessageByTwoId(user_one, user_two);
        if(list != null){
            return FormatResultGenerator.genSuccessResult(list);
        }else{
            return FormatResultGenerator.genErrorResult("暂无消息");
        }
    }
}

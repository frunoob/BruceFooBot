package com.frunoob.BruceFooBot;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @Author: Qiqi Fu
 * @Date: 3/26/2023
 * @Description:
 */
@Slf4j
public class Bot extends TelegramLongPollingBot {
    protected Bot(DefaultBotOptions botOptions, String botToken){
        super(botOptions,botToken);
    }
    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();

        log.info(JSONObject.toJSONString(msg));

        if (msg.isCommand()){

            String text = msg.getText();
            String [] texts = text.split(" ",2);
            String command,arg=null;
            command= texts[0];
            if (texts.length ==2 )arg = texts[1];

            if (command.equals("/scream")){
                log.info(command);
                if (arg!=null) sendMessage(id,arg);
                else sendMessage(id,"");
            } else if (command.equals("/whisper")) {
                log.info(command);
                if (arg != null) log.info(arg);
            }

        } else {

            copyMessage(user.getId(), msg.getMessageId());

        }
    }
    public void copyMessage(Long who, Integer msgId){
        CopyMessage cm = CopyMessage.builder()
                .fromChatId(who.toString())  //We copy from the user
                .chatId(who.toString())      //And send it back to him
                .messageId(msgId)            //Specifying what message
                .build();
        try {
            execute(cm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(Long who,String text){
        SendMessage message = new SendMessage();
        message.setChatId(who);
        message.setText("ahhhhhhhh!" + text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getBotUsername() {
        return System.getenv("telegram_username");
    }
}

package ru.grishenko.ticketing.telegram;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.grishenko.ticketing.telegram.entities.Customer;
import ru.grishenko.ticketing.telegram.sevices.CustomerService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Bot extends TelegramLongPollingBot {

    private static final String BOT_NAME = "TicketingHelperBot";
    private static final String TOKEN = "1712085601:AAGzuSF1JCrWC70OCALO3znbTYLkEm8u_PI";

//    private final Logger logger = LoggerFactory.getLogger(Bot.class);

    private CustomerService customerService;

    public Bot() {
        this.customerService = new CustomerService();
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.enableMarkdown(true);
        sendMessage.enableHtml(true);
        sendMessage.setChatId(message.getChatId().toString());

//        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);


        try {
            setKeyBoardButton(sendMessage);
//            setInlineKeyboard(sendMessage);
            execute(sendMessage);
            log.info("Message sended");
        } catch (TelegramApiException e) {
            log.error("ERROR", e);
        }

    }

    public void setKeyBoardButton(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);         // клавиатура для всех или для определенных пользователей
        replyKeyboardMarkup.setResizeKeyboard(true);    // изменение размера под размер экрана
        replyKeyboardMarkup.setOneTimeKeyboard(true);   // что бы клавиатура не исчезала после нажатия

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(/*new KeyboardButton(*/"/stopList")/*)*/;
        firstRow.add(/*new KeyboardButton(*/"/noClient")/*)*/;

        keyboardRowList.add(firstRow);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);


//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//
//        InlineKeyboardButton stopListButton = new InlineKeyboardButton();
//        stopListButton.setText("StopList");
//        stopListButton.setCallbackData("Список заблокированных");
//
//        List<InlineKeyboardButton> rowButton1 = new ArrayList<>();
//
//        rowButton1.add(stopListButton);
//
//        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//        rowList.add(rowButton1);
//
//        inlineKeyboardMarkup.setKeyboard(rowList);


    }

    public void setInlineKeyboard(SendMessage sendMessage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        InlineKeyboardButton stopListButtonINV = new InlineKeyboardButton();
        stopListButtonINV.setText("Б/Н");
        stopListButtonINV.setCallbackData("stopListINV");
        InlineKeyboardButton stopListButtonCASH = new InlineKeyboardButton();
        stopListButtonCASH.setText("Нал");
        stopListButtonCASH.setCallbackData("stopListCASH");

        List<InlineKeyboardButton> rowButton1 = new ArrayList<>();

        rowButton1.add(stopListButtonINV);
        rowButton1.add(stopListButtonCASH);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(rowButton1);

        inlineKeyboardMarkup.setKeyboard(rowList);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        StringBuilder info = new StringBuilder();
        StringBuilder cust = new StringBuilder();
        if (message != null && message.hasText()) {
            switch (message.getText().trim()) {
                case "/stopList" :
                    info.append("Ask StopList from: ")
                            .append(update.getMessage().getFrom().getLastName())
                            .append(" ")
                            .append(update.getMessage().getFrom().getFirstName());

                    log.info(info.toString());
                    cust.append("===<b>Занесены в стоп лист</b>===").append("\n");
                    cust.append("=========================").append("\n");
                    for (Customer c : customerService.getCustomerStopList()) {
                        cust.append(c.getFullNameR()).append("\n");
                    }
                    sendMsg(message, cust.toString());
 //                    sendMsg(message, "<i>test</i> <b>message</b>");
                    break;
                case "/noClient" :
                    info.append("Ask NoClient list from: ")
                            .append(update.getMessage().getFrom().getLastName())
                            .append(" ")
                            .append(update.getMessage().getFrom().getFirstName());
                    log.info(info.toString());
                    cust.append("===<b>Не являются клиентами</b>===").append("\n");
                    cust.append("============================").append("\n");
                    for (Customer c : customerService.getCustomerNoOne()) {
                        cust.append(c.getFullNameR()).append("\n");
                    }
                    sendMsg(message, cust.toString());

//                    sendMsg(message, "<i>test</i> <b>message</b>");
                    break;

                default:
                    break;
            }
        } else if (update.hasCallbackQuery()){
            String call_data = update.getCallbackQuery().getData();
            int message_id = update.getCallbackQuery().getMessage().getMessageId();
            String chat_id = update.getCallbackQuery().getMessage().getChatId().toString();

            if (call_data.equals("stopListINV")) {
                EditMessageText new_message = new EditMessageText();
                new_message.setChatId(chat_id);
                new_message.setMessageId(message_id);
                new_message.setText("testtest");
                try {
                    execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }


}

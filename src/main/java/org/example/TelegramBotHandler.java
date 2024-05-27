package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Random;

public class TelegramBotHandler extends TelegramLongPollingBot {
    private String botUsername = "guessgametgbotmti_smyslov_bot";
    private String botToken = "7232637328:AAGobZ9Gwcm7HKB1U7TBbtyC0n7fCom5bZE";

    private Random random = new Random();
    private int compNumber, userNumber;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() == true) {
            Message messageFromUser = update.getMessage();

            if (messageFromUser.hasText() == true) {
                String textFromUser = messageFromUser.getText();
                long chatId = messageFromUser.getChatId();

                String textToUser = "";

                if (textFromUser.equals("/start") == true) {
                    compNumber = random.nextInt(1, 100 + 1);
                    textToUser = "Я загадал число от 1 до 100. Попробуй отгадай. Введи свой вариант:";
                } else {
                    userNumber = Integer.parseInt(textFromUser);

                    if (userNumber < compNumber) {
                        textToUser = "Введи побольше";
                    } else if (userNumber > compNumber) {
                        textToUser = "Введи поменьше";
                    } else {
                        textToUser = "Ураа ты угадал. Перезапустите игру командой /start";
                    }
                }

                SendMessage messageToUser = new SendMessage();
                messageToUser.setChatId(chatId);
                messageToUser.setText(textToUser);

                try {
                    execute(messageToUser);
                } catch (TelegramApiException e) {
                    System.out.println("Ошибка отправки сообщения пользователю.  " + e);
                }
            }
        }
    }
}

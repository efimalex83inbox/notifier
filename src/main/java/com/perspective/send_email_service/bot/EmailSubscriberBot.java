package com.perspective.send_email_service.bot;

import com.perspective.send_email_service.bot.parser.MessageParser;
import com.perspective.send_email_service.bot.strategy.BotCommandExecuteStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
public class EmailSubscriberBot extends TelegramLongPollingBot {

    private static String PROXY_HOST = "45.76.135.34" /* proxy host */;
    private static Integer PROXY_PORT = 10080 /* proxy port */;
    private static String PROXY_USER = "teleForever_user" /* proxy user */;
    private static String PROXY_PASSWORD = "frg45yFgg4L" /* proxy password */;


    private final Map<String, BotCommandExecuteStrategy> strategyMap;
    private final BotCommandExecuteStrategy defaultStrategy;

    private final MessageParser botCmdNameParser;
    private final MessageParser botCmdValueParser;

    public EmailSubscriberBot(Map<String, BotCommandExecuteStrategy> strategyMap,
                              BotCommandExecuteStrategy defaultStrategy,
                              MessageParser botCmdNameParser,
                              MessageParser botCmdValueParser) {
        this.strategyMap = strategyMap;
        this.defaultStrategy = defaultStrategy;
        this.botCmdNameParser = botCmdNameParser;
        this.botCmdValueParser = botCmdValueParser;
    }


    @Override
    public String getBotToken() {
        return "673739546:AAGwl_Hpu0py6olPk_-P6TIbld-iBse-UBM";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String cmdName = this.botCmdNameParser.parse(message);
        if (StringUtils.isNotBlank(cmdName)) {
            String cmdValue = this.botCmdValueParser.parse(message);
            BotCommandExecuteStrategy strategy = this.strategyMap.getOrDefault(cmdName, defaultStrategy);
            strategy.execute(cmdValue, message);
        }
    }

    @Override
    public String getBotUsername() {
        return "EmailSubscriber007Bot";
    }

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
//            log.log(Level.SEVERE, "Exception: ", e.toString());
        }
    }

    @PostConstruct
    public void init() {
        try {
//            EmailSubscriberBot bot = new EmailSubscriberBot();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            // Set up Http proxy
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(PROXY_HOST, PROXY_PORT),
                    new UsernamePasswordCredentials(PROXY_USER, PROXY_PASSWORD));

            RequestConfig requestConfig = RequestConfig.custom().setAuthenticationEnabled(true).build();
            botOptions.setRequestConfig(requestConfig);
            botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
            botOptions.setProxyHost(PROXY_HOST);
            botOptions.setProxyPort(PROXY_PORT);


            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            throw new RuntimeException(String.format("Cannot construct a bean [%s].",
                    EmailSubscriberBot.class.getCanonicalName()));
        }
    }
}

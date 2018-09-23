package com.perspective.send_email_service.bot;

import com.perspective.send_email_service.bot.parser.MessageParser;
import com.perspective.send_email_service.bot.strategy.BotCommandExecuteStrategy;
import com.perspective.send_email_service.configuration.property.BotProperties;
import com.perspective.send_email_service.configuration.property.BotProxyProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class EmailSubscriberBot extends TelegramLongPollingBot {
    private Logger logger = LoggerFactory.getLogger(EmailSubscriberBot.class);

    private final Map<String, BotCommandExecuteStrategy> strategyMap;
    private final BotCommandExecuteStrategy defaultStrategy;
    private final BotProxyProperties botProxyProperties;
    private final BotProperties botProperties;

    private final MessageParser botCmdNameParser;
    private final MessageParser botCmdValueParser;

    public EmailSubscriberBot(Map<String, BotCommandExecuteStrategy> strategyMap,
                              BotCommandExecuteStrategy defaultStrategy,
                              BotProxyProperties botProxyProperties,
                              BotProperties botProperties,
                              MessageParser botCmdNameParser,
                              MessageParser botCmdValueParser) {
        this.strategyMap = strategyMap;
        this.defaultStrategy = defaultStrategy;
        this.botProxyProperties = botProxyProperties;
        this.botProperties = botProperties;
        this.botCmdNameParser = botCmdNameParser;
        this.botCmdValueParser = botCmdValueParser;
    }


    @Override
    public String getBotToken() {
        return this.botProperties.getToken();
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
        return botProperties.getName();
    }

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("Exception: ", e);
        }
    }

    @PostConstruct
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            // Set up Http proxy
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

            if (this.botProxyProperties.getEnabled()) {
                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(
                        new AuthScope(botProxyProperties.getHost(), botProxyProperties.getPort()),
                        new UsernamePasswordCredentials(botProxyProperties.getUser(), botProxyProperties.getPassword()));

                RequestConfig requestConfig = RequestConfig.custom().setAuthenticationEnabled(true).build();
                botOptions.setRequestConfig(requestConfig);
                botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
                botOptions.setProxyHost(botProxyProperties.getHost());
                botOptions.setProxyPort(botProxyProperties.getPort());
            }


            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            throw new RuntimeException(String.format("Cannot construct a bean [%s].",
                    EmailSubscriberBot.class.getCanonicalName()));
        }
    }
}

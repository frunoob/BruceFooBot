package com.frunoob.BruceFooBot;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

@SpringBootApplication
public class BruceFooBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(BruceFooBotApplication.class, args);
	}

	/**
	 * start BruceFooBot
	 */
	@PostConstruct
	private void startBot(){
		String BOT_TOKEN = System.getenv("telegram_token") /* your bot's token here */;

		String PROXY_HOST = "127.0.0.1" /* proxy host */;
		Integer PROXY_PORT = 7890 /* proxy port */;
		String PROXY_USER = System.getenv("PROXY_NAME") /* proxy user */;
		String PROXY_PASSWORD = System.getenv("PROXY_PASSWORD") /* proxy password */;

		try {

			// Create the Authenticator that will return auth's parameters for proxy authentication
//			Authenticator.setDefault(new Authenticator() {
//				@Override
//				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication(PROXY_USER, PROXY_PASSWORD.toCharArray());
//				}
//			});

			// Create the TelegramBotsApi object to register your bots
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

			// Set up Http proxy
			DefaultBotOptions botOptions = new DefaultBotOptions();

			botOptions.setProxyHost(PROXY_HOST);
			botOptions.setProxyPort(PROXY_PORT);
			// Select proxy type: [HTTP|SOCKS4|SOCKS5] (default: NO_PROXY)
			botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

			// Register your newly created AbilityBot
			Bot bot = new Bot(botOptions, BOT_TOKEN);

			botsApi.registerBot(bot);

			// Actively send messages to somebody
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					bot.sendMessage(<Long Id>,"hello");
//				}
//			}).start();

		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}

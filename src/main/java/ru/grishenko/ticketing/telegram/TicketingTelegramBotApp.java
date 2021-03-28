package ru.grishenko.ticketing.telegram;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.grishenko.ticketing.telegram.dao.CustomerDAO;
import ru.grishenko.ticketing.telegram.entities.Customer;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class TicketingTelegramBotApp {

    public static void main(String[] args) {
//        Session session = DBFactory.getInstance().getSession();
//
//        session.beginTransaction();
//        Buyer buyer = session.get(Buyer.class, buyerId);
//        List<Product> result = new ArrayList<>(buyer.getProducts());
//        List<Product> result = buyer.getProducts();                       // такой запрос вызывает org.hibernate.LazyInitializationException:
        //        failed to lazily initialize a collection of role: ru.grishchenko.models.Buyer.products,
        //        could not initialize proxy - no Session
        // Видимо что-то связано с контекстом постоянства, в чем может быть пролема?
//        session.getTransaction().commit();

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
            log.info("Bot Start");
        } catch (TelegramApiException e) {
            log.error("ERROR", e);
        }

//        CustomerDAO cd = new CustomerDAO();
//        for (Customer c : cd.getStopListCustomers()) {
//            System.out.println(c.getFullNameR());
//        }


    }
}

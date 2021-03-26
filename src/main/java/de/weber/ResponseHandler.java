package de.weber;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Map;

public class ResponseHandler {

    private final MessageSender sender;
    private final Map<Long, State> chatStates;

    public ResponseHandler(MessageSender sender, DBContext db) {
        this.sender = sender;
        this.chatStates = db.getMap("CHAT_STATES");
    }

    public void replyToStart(long chatID) {
        try {
            SendMessage message = new SendMessage();
            message.setText("Hallo, ich bin der Corona-Bot. Welche Region interessiert dich?");
            message.setChatId(String.valueOf(chatID));
            message.setReplyMarkup(KeyboardFactory.selectRegion());
            chatStates.put(chatID, State.AWAITING_REGION);
            sender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void showKeyboard(long chatID) {
        try {
            SendMessage message = new SendMessage();
            message.setText("Interessiert dich noch eine weitere Region?");
            message.setChatId(String.valueOf(chatID));
            message.setReplyMarkup(KeyboardFactory.selectRegion());
            chatStates.put(chatID, State.AWAITING_REGION);
            sender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToInlineKeyboard(long chatID, String buttonID) {
        try {
            switch (buttonID) {
                case "Freiburg":
                    replyToFreiburg(chatID);

                case "Rhein-Pfalz-Kreis":
                    replyToRP(chatID);

                case "Worms":
                    replyToWorms(chatID);
            }
            showKeyboard(chatID);
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }

    private void replyToFreiburg(long chatID) throws TelegramApiException, IOException {
        if (chatStates.get(chatID).equals(State.AWAITING_REGION)) {
            chatStates.put(chatID, State.REGION_IS_FREIBURG);
            processFreiburg(chatID);
        }
    }

    private void replyToRP(long chatID) throws TelegramApiException, IOException {
        if (chatStates.get(chatID).equals(State.AWAITING_REGION)) {
            chatStates.put(chatID, State.REGION_IS_RP);
            processRP(chatID);
        }
    }

    private void replyToWorms(long chatID) throws IOException, TelegramApiException {
        if (chatStates.get(chatID).equals(State.AWAITING_REGION)) {
            chatStates.put(chatID, State.REGION_IS_WORMS);
            processWorms(chatID);
        }
    }

    private void processFreiburg(long chatID) throws IOException, TelegramApiException {
        SendMessage message = new SendMessage();
        Webpage webpage = new Webpage("https://www.baden-wuerttemberg.de/de/service/presse/pressemitteilung/pid/infektionen-und-todesfaelle-in-baden-wuerttemberg/");
        message.setText("Inzidenz:" + webpage.getFreiburgCases());
        message.setChatId(String.valueOf(chatID));
        sender.execute(message);
        chatStates.put(chatID, State.REGION_IS_FREIBURG);
    }

    private void processRP(long chatID) throws IOException, TelegramApiException {
        SendMessage message = new SendMessage();
        Webpage webpage = new Webpage("https://msagd.rlp.de/de/unsere-themen/gesundheit-und-pflege/gesundheitliche-versorgung/oeffentlicher-gesundheitsdienst-hygiene-und-infektionsschutz/infektionsschutz/informationen-zum-coronavirus-sars-cov-2/");
        message.setText("Inzidenz: " + webpage.getRPCases());
        message.setChatId(String.valueOf(chatID));
        sender.execute(message);
        chatStates.put(chatID, State.REGION_IS_RP);
    }

    private void processWorms(long chatID) throws IOException, TelegramApiException {
        SendMessage message = new SendMessage();
        Webpage webpage = new Webpage("https://msagd.rlp.de/de/unsere-themen/gesundheit-und-pflege/gesundheitliche-versorgung/oeffentlicher-gesundheitsdienst-hygiene-und-infektionsschutz/infektionsschutz/informationen-zum-coronavirus-sars-cov-2/");
        message.setText("Inzidenz: " + webpage.getWormsCases());
        message.setChatId(String.valueOf(chatID));
        sender.execute(message);
        chatStates.put(chatID, State.REGION_IS_WORMS);
    }
}

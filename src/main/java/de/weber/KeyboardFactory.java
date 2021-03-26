package de.weber;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardFactory {
    public static ReplyKeyboard selectRegion() {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton freiburgButton = new InlineKeyboardButton();
        freiburgButton.setText("Freiburg");
        freiburgButton.setCallbackData("Freiburg");
        InlineKeyboardButton rlpButton = new InlineKeyboardButton();
        rlpButton.setText("Rhein-Pfalz-Kreis");
        rlpButton.setCallbackData("Rhein-Pfalz-Kreis");
        InlineKeyboardButton wormsButton = new InlineKeyboardButton();
        wormsButton.setText("Worms");
        wormsButton.setCallbackData("Worms");
        row.add(freiburgButton);
        row.add(rlpButton);
        row.add(wormsButton);
        rows.add(row);
        keyboard.setKeyboard(rows);
        return keyboard;
    }
}

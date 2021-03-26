package de.weber;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Consumer;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class CoronaBot extends AbilityBot {
    private final ResponseHandler responseHandler;

    public CoronaBot(String token, String username) {
        super(token, username);
        responseHandler = new ResponseHandler(sender, db);
    }

    @Override
    public long creatorId() {
        return 63389474;
    }

    public Ability defaultAnswer() {
        return Ability.builder()
                .name(DEFAULT)
                .info("default action")
                .privacy(PUBLIC)
                .locality(ALL)
                .input(0)
                .action(ctx -> silent.send("Bitte verwende den Befehl /start", ctx.chatId()))
                .build();
    }

    public Ability replyToStart() {
        return Ability.builder()
                .name("start")
                .info("Starts the bot")
                .privacy(PUBLIC)
                .locality(ALL)
                .input(0)
                .action(ctx -> {
                    responseHandler.replyToStart(ctx.chatId());
                }).build();
    }

    public Reply replyToInlineKeyboard() {
        Consumer<Update> action = upd -> responseHandler.replyToInlineKeyboard(getChatId(upd), upd.getCallbackQuery().getData());
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }
}

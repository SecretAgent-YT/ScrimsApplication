package me.secretagent.machine.commands.impl;

import me.secretagent.machine.commands.Command;
import me.secretagent.machine.commands.CommandContext;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.components.Button;

public class ItemsCommand implements Command {

    @Override
    public CommandData getData() {
        return new CommandData("viewitems", "View all the items");
    }

    @Override
    public void onCalled(CommandContext context) {
        context.reply("", false)
                .addActionRow(Button.primary("button", "TestButton"))
                .queue();
    }

}

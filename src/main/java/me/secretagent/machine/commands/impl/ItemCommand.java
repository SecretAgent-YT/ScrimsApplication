package me.secretagent.machine.commands.impl;

import me.secretagent.machine.Machine;
import me.secretagent.machine.commands.Command;
import me.secretagent.machine.commands.CommandContext;
import me.secretagent.machine.items.Item;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.io.IOException;

public class ItemCommand implements Command {

    @Override
    public CommandData getData() {
        return new CommandData("item", "Command for viewing an item")
                .addOption(OptionType.STRING, "name", "The name of the item", true);
    }

    @Override
    public void onCalled(CommandContext context) {
        String name = context.getOption("name").getAsString();
        try {
            Item item = Machine.getMachine().fromName(name);
            String string = item.getName() + "\n";
            string += "ID: " + item.getId() + "\n";
            string += "Price: " + item.getPrice() + "\n";
            context.reply(string, false);
        } catch (NullPointerException ex) {
            context.reply("Item does not exist!", false).queue();
        }
    }
}

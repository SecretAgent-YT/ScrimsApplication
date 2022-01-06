package me.secretagent.machine.commands.impl;

import me.secretagent.machine.Machine;
import me.secretagent.machine.commands.Command;
import me.secretagent.machine.commands.CommandContext;
import me.secretagent.machine.items.Item;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.utils.AttachmentOption;

import java.io.File;
import java.util.ArrayList;

public class BuyCommand implements Command {

    @Override
    public CommandData getData() {
        return new CommandData("buy", "Command for purchasing items")
                .addOption(OptionType.STRING, "item", "Item that you want to purchase", true)
                .addOption(OptionType.INTEGER, "cash", "The amount of cash you have", true);
    }

    @Override
    public void onCalled(CommandContext context) {
        try {
            Item item = Machine.getMachine().fromName(context.getOption("item").getAsString());
            int price = item.getPrice();
            int input = Integer.parseInt(String.valueOf(context.getOption("cash").getAsDouble()).replace(".0", ""));
            if (input < price) {
                context.reply("You don't have enough cash for that item!", false).queue();
                return;
            }
            int changeback = input - price;
            int changerecord = 0;
            if (changeback == 0) {
                String reply = "You payed the exact amount needed!\n";
                reply += item.getImage();
                context.reply(reply, false).queue();
                //sendpick
                return;
            }
            ArrayList<String> coins = new ArrayList<>();
            while (changeback >= 10) {
                System.out.println(changeback);
                if (500 <= changeback) {
                    changerecord += 500;
                    changeback -= 500;
                    coins.add("5 USD");
                    continue;
                }
                if (200 <= changeback) {
                    changerecord += 200;
                    changeback -= 200;
                    coins.add("2 USD");
                    continue;
                }
                if (100 <= changeback) {
                    changerecord += 100;
                    changeback -= 100;
                    coins.add("1 USD");
                    continue;
                }
                if (50 <= changeback) {
                    changerecord += 50;
                    changeback -= 50;
                    coins.add("50 CENTS USD");
                    continue;
                }
                if (20 <= changeback) {
                    changerecord += 20;
                    changeback -= 20;
                    coins.add("20 CENTS USD");
                    continue;
                }
                if (10 <= changeback) {
                    changerecord += 10;
                    changeback -= 10;
                    coins.add("10 CENTS USD");
                    continue;
                }
            }
            if (changeback > 0 && changeback < 10) {
                changerecord += 10;
                changeback -= 10;
                coins.add("10 CENTS USD");
            }
            String response = "Your change is: " + changerecord + " cents!\n" + "Bills/Coins:\n";
            for (String string : coins) {
                response += string + "\n";
            }
            context.reply(response, false).addFile(new File(getClass().getClassLoader().getResource(item.getName() + ".jpg").getFile())).queue();
        } catch (Exception ex) {
            context.reply("Item does not exist!", false);
            ex.printStackTrace();
        }
    }

}

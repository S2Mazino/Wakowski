package Command.Commands;

import java.awt.Color;

import Command.CommandContext;
import Command.CommandManager;
import Command.ICommand;
import DiscordBot.Config;
import net.dv8tion.jda.api.EmbedBuilder;

import net.dv8tion.jda.api.entities.MessageEmbed.Field;

public class HelpCommand implements ICommand {
	
	private final CommandManager manager;
	
	public HelpCommand(CommandManager manager) {
		this.manager = manager;
	}

	@Override
	public void handle(CommandContext ctx) {
		
		//build embed
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Overview")
				.setColor(Color.GREEN);
		for(ICommand commands : manager.getCommandList()) {
			if(commands.getName().equals("help")) continue; //skip the help
			Field f = new Field(Config.get("prefix") + commands.getName(), commands.getHelp(), false);
			embed.addField(f);
		}
		
		//display embed
		ctx.getEvent().getChannel().sendMessageEmbeds(embed.build()).queue();
		
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getHelp() {
		return "";
	}



}

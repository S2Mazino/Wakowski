package Command.Commands;

import java.awt.Color;
import java.util.List;

import Command.CommandContext;
import Command.ICommand;
import DiscordBot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;

/**
 * Simple ping command.
 * @author Nordine
 *
 */
public class PingCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		List<String> args = ctx.getArgs();
		//regular relay ping
		if(args.size() == 0) {
			//get the server relay ping
			String ping = String.valueOf(ctx.getEvent().getJDA().getGatewayPing());
			//delete the command on discord
			ctx.getEvent().getMessage().delete();
			//send output
			ctx.getEvent().getChannel()
			.sendMessage(ping)
			.queue();
			
			//pinging a user x amount of times
		}else if(args.size() == 2 && isNumeric(args.get(1))) {
			int xTimes = Integer.parseInt(args.get(1));
			final Member memberCalled = ctx.getMessage().getMentionedMembers().get(0);
			
			//delete message
			ctx.getEvent().getMessage().delete();
			//ping the user
			for(int i = 0; i < xTimes; i++) {
				ctx.getEvent().getChannel()
				.sendMessage(memberCalled.getAsMention())
				.queue();
			}
			
		}else {
			EmbedBuilder embed = new EmbedBuilder()
					.setTitle("Ping")
					.setColor(Color.GREEN);
			Field f = new Field(Config.get("prefix") + "ping", getHelp(), false);
			embed.addField(f);
			ctx.getEvent().getChannel().sendMessageEmbeds(embed.build()).queue();
		}
		
	
	}
	
	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	

	@Override
	public String getName() {
		return "ping";
	}


	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Returns ping from user to discord servers\n" +
				"Usage:\n" + 
				"!ping //gets the relay ping\n" +
				"!ping <user> <number of times> //pings a certain user x amount of times";
	}
}

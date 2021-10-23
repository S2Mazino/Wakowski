package Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import Command.Commands.HelpCommand;
import Command.Commands.KickCommand;
import Command.Commands.MemeCommand;
import Command.Commands.PingCommand;
import DiscordBot.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class CommandManager {
	private final List<ICommand> commands = new ArrayList<>();
	
	public CommandManager() {
		addCommand(new HelpCommand(this));
		addCommand(new PingCommand());
		addCommand(new MemeCommand());
		addCommand(new KickCommand());
	}
	
	private void addCommand(ICommand cmd) {
		boolean cmdExist = false;
		
		for(ICommand ic : commands) {
			if(ic.getName().equalsIgnoreCase(cmd.getName())) {
				cmdExist = true;
			}
		}
		
		if(cmdExist) {
			throw new IllegalArgumentException("Command with this name already exist");
		}
		
		commands.add(cmd);
	}
	
	public List<ICommand> getCommandList(){
		return commands;
	}
	
	@Nullable
	public ICommand getCommand(String cmd) {
		
		for(ICommand ic: this.commands) {
			if(ic.getName().equalsIgnoreCase(cmd) || ic.getAliases().contains(cmd)) {
				return ic;
			}
		}
		return null;
		
	}
	
	public void handle(GuildMessageReceivedEvent event) {
		String[] split = event.getMessage().getContentRaw().replaceFirst(Config.get("prefix"), "").split("\\s+");
		
		String invoke = split[0].toLowerCase();
		ICommand cmd = this.getCommand(invoke);
		
		if(cmd != null) {
			event.getChannel().sendTyping().queue();
			List<String> args = Arrays.asList(split).subList(1, split.length);
			
			CommandContext ctx = new CommandContext(event, args);
			
			
			cmd.handle(ctx);
			
			
		}else{
			//when command does not exist, delete message and notify user
			event.getMessage().delete();
			event.getChannel().sendMessage("Command does not exist").queue();
		}
	}

}

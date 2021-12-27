package Command;

import java.util.List;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandContext implements ICommandContext {
	
	private final MessageReceivedEvent event;
	private final List<String> args;
	
	public CommandContext(MessageReceivedEvent event, List<String> args) {
		this.event = event;
		this.args = args;
	}

	@Override
	public MessageReceivedEvent getEvent() {
		return this.event;
	}
	
	@Override
	public Guild getGuild() {
		return this.getEvent().getGuild();
	}
	
	public List<String> getArgs(){
		return this.args;
	}
	
}

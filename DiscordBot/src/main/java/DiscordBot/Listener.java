package DiscordBot;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Command.CommandManager;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
	private final CommandManager manager = new CommandManager();
	
	public void onReady(@Nonnull ReadyEvent event) {
		LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
	}
	
	public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
		User user = event.getAuthor();
		
		if(user.isBot()) return;
		
		String prefix = Config.get("prefix");
		String message = event.getMessage().getContentRaw();
		
		if(message.equalsIgnoreCase(prefix + "shutdown") 
				&& user.getId().equals(Config.get("owner_id"))) { //check if user is owner
			LOGGER.info("Shutting down");
			event.getJDA().shutdown();
			return;
		}
		
		if(message.startsWith(prefix)) {
			manager.handle(event);
		}
	}
}

package DiscordBot;

import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Command.CommandManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
	private final CommandManager manager = new CommandManager();
	
	public void onReady(@Nonnull ReadyEvent event) {
		LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
	}
	
	/**
	 * Handles message received with prefixes.
	 */
	@Override
	public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
		User user = event.getAuthor();
		
		if(user.isBot()) return;
		
		String prefix = Config.get("prefix");
		String message = event.getMessage().getContentRaw();
		
		if(message.equalsIgnoreCase(prefix + "shutdown")) { 
			if(user.getId().equals(Config.get("owner_id"))) {//check if user is owner
				LOGGER.info("Shutting down");
				event.getJDA().shutdown();
				return;
			}else {
				event.getChannel().sendMessage("Only server owner can use this command").queue();
				return;
			}	
		}
		
		if(message.startsWith(prefix)) {
			manager.handle(event);
		}
	}
	
	@Override
	public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
		handleJoinLeave(event.getGuild(), event);
		
		
	}
	
	@Override
	public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
		handleJoinLeave(event.getGuild(), event);
	}
	
	/**
	 * Handles the join/leave of a user on the discord server.
	 * @param guild
	 * @param event
	 */
	private void handleJoinLeave(Guild guild, Object event) {
		
		boolean joined = event instanceof GuildMemberJoinEvent;
		
		if(joined) {
			//welcome new user in general chat
			TextChannel generalChannel = guild.getDefaultChannel();
			GuildMemberJoinEvent e = (GuildMemberJoinEvent) event;
			String welcome = String.format("%s has joined the server.", e.getMember().getAsMention());
			
			generalChannel.sendMessage(welcome).queue();
		}else {
			//get all the bot channels
			final List<TextChannel> botChannels = guild.getTextChannelsByName("bot-spam", true);
			
			//user has left the server, send that information to bot-spam
			GuildMemberRemoveEvent e = (GuildMemberRemoveEvent) event;
			//get user's tag to keep track rather than their mention name
			String leave = String.format("%s has left the server.", e.getMember().getUser().getAsTag());
			
			//notify the default channel that their is not a bot-spam channel (to dedicate all bot commands into a single channel)
			if(botChannels.isEmpty()) {
				guild.getDefaultChannel().sendMessage("Add a bot-spam channel to clean chat from bot spam").queue();
				guild.getDefaultChannel().sendMessage(leave).queue();
			}else {
				botChannels.get(0).sendMessage(leave).queue();
			}
			
		}
	}
	
}

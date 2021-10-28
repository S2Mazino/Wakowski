package DiscordBot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;

import javax.security.auth.login.LoginException;

import com.jagrosh.jdautilities.command.CommandClientBuilder;

public class Bot {
	private Bot() throws LoginException{
		
		//initialize client
		CommandClientBuilder client = new CommandClientBuilder();
		
		//set client settings
		client.setPrefix(Config.get("prefix"));
		
		
		//register JDA
		JDA jda = JDABuilder
				.createDefault(Config.get("token")
						,GatewayIntent.GUILD_MEMBERS
						,GatewayIntent.GUILD_BANS
						,GatewayIntent.GUILD_EMOJIS
						,GatewayIntent.GUILD_WEBHOOKS
						,GatewayIntent.GUILD_INVITES
						,GatewayIntent.GUILD_VOICE_STATES
						,GatewayIntent.GUILD_PRESENCES
						,GatewayIntent.GUILD_MESSAGES
						,GatewayIntent.GUILD_MESSAGE_REACTIONS
						,GatewayIntent.GUILD_MESSAGE_TYPING
						,GatewayIntent.DIRECT_MESSAGES
						,GatewayIntent.DIRECT_MESSAGE_REACTIONS
						,GatewayIntent.DIRECT_MESSAGE_TYPING)
				.setChunkingFilter(ChunkingFilter.ALL)
				.setStatus(OnlineStatus.ONLINE)
				.setActivity(Activity.watching("you nooby"))
				.addEventListeners(
						new Listener()
				).build();
	}
	public static void main(String[] args) throws LoginException{
		
		new Bot();
		
	}
}

package Command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.sharding.ShardManager;

public interface ICommandContext {
	MessageReceivedEvent getEvent();
	
	default Guild getGuild() {
		return this.getEvent().getGuild();
	}
	
	default MessageChannel getChannel() {
		return this.getEvent().getChannel();
	}
	
	default Message getMessage() {
		return this.getEvent().getMessage();
	}
	
	default User getAuthor() {
		return this.getEvent().getAuthor();
	}
	
	default Member getMember() {
		return this.getEvent().getMember();
	}
	
	default JDA getJDA() {
		return this.getEvent().getJDA();
	}
	
	default ShardManager getShardManager() {
		return this.getJDA().getShardManager();
	}
	
	default User getSelfUser() {
		return this.getJDA().getSelfUser();
	}
	
	default Member getSelfMember() {
		return this.getGuild().getSelfMember();
	}
	
	
}

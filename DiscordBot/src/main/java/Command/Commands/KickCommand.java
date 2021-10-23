package Command.Commands;


import java.util.List;

import Command.CommandContext;
import Command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class KickCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		final Message message = ctx.getMessage();
		final Member memberCaller = ctx.getMember();
		final List<String> args = ctx.getArgs();
		
		
		//in the case that there is not enough arguments, tell user
		if(args.size() < 2 || message.getMentionedMembers().isEmpty()) {
			ctx.getChannel().sendMessage("Missing additional arguments").queue();
			return;
		}
		
		//check if member who called the command has permission to kick
		final Member memberCalled = message.getMentionedMembers().get(0);
		
		if(!memberCaller.canInteract(memberCalled) || !memberCaller.hasPermission(Permission.KICK_MEMBERS)) {
			ctx.getChannel().sendMessage("You do not have required permissions").queue();
			return;
		}
		
		//check if bot has permission to kick
		final Member bot = ctx.getSelfMember();
		
		if(!bot.canInteract(memberCalled) || !bot.hasPermission(Permission.KICK_MEMBERS)) {
			ctx.getChannel().sendMessage("Bot does not have permission to kick").queue();
			return;
		}
		
		//kick member now
		String reason = String.join(" ", args.subList(1, args.size()));
		
		ctx.getGuild()
		.kick(memberCalled, reason)
		.reason(reason) //for audit log
		.queue(
				(__) -> ctx.getChannel().sendMessage(memberCalled.getEffectiveName() + " has been kicked.").queue(),
				(error) -> ctx.getChannel().sendMessageFormat("Could not kick %s", error.getMessage()).queue()
		); 
		
	}

	@Override
	public String getName() {
		return "kick";
	}

	@Override
	public String getHelp() {
		return "Kicks specified user off the server.\n" +
				"Usage: !kick <user> <reason>";
	}

}

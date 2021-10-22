package Command;

import java.util.Arrays;
import java.util.List;

public interface ICommand {
	void handle(CommandContext ctx);
	String getName();
	String getHelp();
	default List<Object> getAliases(){
		return Arrays.asList();
	};
	
}

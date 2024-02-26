package view.commands;

public class ExitSubMenuCommand extends Command {
	public ExitSubMenuCommand(String key, String desc) {
		super(key, desc);
	}
	
	@Override
	public void execute(){
		throw new RuntimeException("Closing submenu\n");
	}

}

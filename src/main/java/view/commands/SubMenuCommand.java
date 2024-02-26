package view.commands;
import view.TextMenu;

public class SubMenuCommand extends Command {
	private TextMenu menu;
	
	public SubMenuCommand(String key, String desc, TextMenu sub_menu) {
		super(key, desc);
		menu = sub_menu;
	}
	
	@Override
	public void execute() {
		menu.run();
	}

}

package view;
import java.util.Map;
import java.util.HashMap;
import view.commands.Command;

public class SubMenu extends TextMenu {
	private Map<String, Command> commands;

	public SubMenu() {
		commands = new HashMap<>();
	}
	
	public void addCommand(Command c) {
		commands.put(c.getKey(), c);
	}
	
	public void printMenu() {
		for(Command c : commands.values()) {
			System.out.println(c.getKey()+". "+c.getDescription());
		}
	}
	
	public void run() {
		boolean keep_running = true;
		
		while(keep_running) {
			this.printMenu();
			System.out.print(" > ");
			String key = scanner.nextLine();
			Command c = commands.get(key);
			
			if(c == null) {
				System.out.println("Invalid option");
				continue;
			}
			
			try {
				c.execute();
			}catch(Exception e) {
				System.out.println(e.getMessage());
				keep_running = false;
			}
		}
	}
}

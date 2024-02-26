package view;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import view.commands.Command;

public class TextMenu {
	private Map<String, Command> commands;
	protected static final Scanner scanner = new Scanner(System.in);
	
	public TextMenu() {
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
			
			c.execute();
		}
		scanner.close();
		
	}
}

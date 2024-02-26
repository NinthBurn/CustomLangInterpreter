package view.commands;

public abstract class Command {	
	protected String key;
	protected String description;
	
	public Command(String k, String desc) {
		key = k;
		description = desc;
	}
	
	public abstract void execute();
	
	public String getKey() {
		return key;
	}
	
	public String getDescription() {
		return description;
	}
}

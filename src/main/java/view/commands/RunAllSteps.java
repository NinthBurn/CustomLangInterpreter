package view.commands;

import controller.ProgramStateController;

public class RunAllSteps extends Command {
	private ProgramStateController controller;

	public RunAllSteps(String key, String desc, ProgramStateController ctr) {
		super(key, desc);
		controller = ctr;
	}

	@Override
	public void execute() {
		try {
			controller.allStepExecutionForAll();
			System.out.println("\nProgram successfully finished execution\n");
		} catch (Exception e) {
			System.out.println("An error occured: ");
			System.out.println(e.getMessage() + "\n");
//			throw new RuntimeException(e);
		}
	}

}

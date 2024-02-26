package repository;
import model.ProgramState;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class MemoryRepository implements IRepository{
	private List<ProgramState> programs;
	private String log_file_path;
	
	public MemoryRepository() {
		programs = new ArrayList<ProgramState>();
		log_file_path = "programlog.txt";
	}
	
	public MemoryRepository(String filename) {
		programs = new ArrayList<ProgramState>();
		log_file_path = filename;
	}
	
	
	public void add(ProgramState program) {
		programs.add(program);
	}
	
	public void remove(ProgramState program) throws RepositoryException {
		if(!programs.remove(program))
			throw new RepositoryException("Program is not in the repository");
	}
	
	public List<ProgramState> getAll(){
		return this.programs;
	}
	
	public void setProgramList(List<ProgramState> programs) {
		this.programs = programs;
	}
	
	public void logProgramStateExecution(ProgramState state) throws RepositoryException{
		try {
			PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(log_file_path, true)));
			logFile.print(state.toStringFile());
			logFile.close();
		}catch(Exception e) {
			throw new RepositoryException("[REPO]Couldn't write to log file\n" + e.getMessage());
		}
	}
}

package repository;
import model.ProgramState;
import java.util.List;

@SuppressWarnings("serial")
class RepositoryException extends RuntimeException{
	public RepositoryException(){
		super();
	}
	
	public RepositoryException(String message){
		super(message);
	}
}

public interface IRepository {
	void add(ProgramState new_program);
	void remove(ProgramState program) throws RepositoryException;
	List<ProgramState> getAll();
	void setProgramList(List<ProgramState> programs);
	void logProgramStateExecution(ProgramState state) throws RepositoryException;
}

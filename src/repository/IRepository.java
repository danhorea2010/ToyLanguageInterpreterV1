package repository;

import model.ProgramState;

public interface IRepository {
    ProgramState getCurrentProgram();

    void add(ProgramState state);
    void remove(ProgramState state);
    void logProgramState(ProgramState state) throws Exception;

}

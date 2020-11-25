package repository;

import model.ProgramState;
import model.adt.MyList;

import java.io.IOException;

public interface IRepository {

    void add(ProgramState state);
    void remove(ProgramState state);

    void logProgramState(ProgramState state) throws RuntimeException, IOException;

    MyList<ProgramState> getProgramList();
    void setProgramList(MyList<ProgramState> programList);

}

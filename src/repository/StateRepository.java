package repository;

import model.MyList;
import model.ProgramState;

public class StateRepository implements IRepository{
    MyList<ProgramState> stateList;

    public StateRepository(){
        stateList = new MyList<>();
    }

    @Override
    public ProgramState getCurrentProgram() {
        return null;
    }

    @Override
    public void add(ProgramState state) {
        stateList.add(state);
    }

    @Override
    public void remove(ProgramState state) {
        stateList.remove(state);
    }
}

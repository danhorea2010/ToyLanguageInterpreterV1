package repository;

import model.adt.MyList;
import model.ProgramState;

public class StateRepository implements IRepository{
    MyList<ProgramState> stateList;

    public StateRepository(){
        stateList = new MyList<>();
    }

    @Override
    public ProgramState getCurrentProgram() {
        ProgramState currentState = this.stateList.get(0);
        this.stateList.remove(currentState);

        return currentState;
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

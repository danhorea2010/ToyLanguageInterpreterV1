package controller;

import exceptions.EmptyCollectionException;
import model.ProgramState;
import model.adt.MyStack;
import model.statement.IStatement;
import repository.IRepository;

public class Controller {

    private final IRepository repository;
    private boolean displayTag;

    public Controller(IRepository repository){
        this.repository = repository;
        this.displayTag = false;
    }

    public void setDisplayTag(boolean displayTag){
        this.displayTag = displayTag;
    }


    public void add(ProgramState state){
        this.repository.add(state);
    }

    public ProgramState oneStep(ProgramState state) throws Exception {

        MyStack<IStatement> stack = (MyStack<IStatement>) state.getStack();
        if(stack.isEmpty()){
            // Throw empty stack
            throw new EmptyCollectionException("Execution stack is empty\n");

        }

        //IStatement lastState = state.deepCopy(state.getOriginalProgram());

        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void allStep() throws Exception {
        ProgramState program = repository.getCurrentProgram();
        this.repository.logProgramState(program);
        if(this.displayTag) {
            System.out.println(program);

        }

        while(!program.getStack().isEmpty()){
            program = oneStep(program);
            if(this.displayTag) {
                System.out.println(program);
            }
            this.repository.logProgramState(program);

        }

    }

}

package controller;

import model.statement.IStatement;
import model.MyStack;
import model.ProgramState;
import repository.IRepository;

public class Controller {

    IRepository repository;

    public Controller(IRepository repository){
        this.repository = repository;
    }

    private ProgramState oneStep(ProgramState state) throws Exception {

        MyStack<IStatement> stack = state.getStack();
        if(stack.isEmpty()){
            // Throw empty stack

        }

        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    private void allStep() throws Exception {
        ProgramState program = repository.getCurrentProgram();
        while(!program.getStack().isEmpty()){
            oneStep(program);
            // Display
        }


    }

}

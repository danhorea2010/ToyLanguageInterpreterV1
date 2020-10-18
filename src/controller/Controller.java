package controller;

import model.MyStack;
import model.ProgramState;
import model.statement.IStatement;
import repository.IRepository;

public class Controller {

    IRepository repository;

    public Controller(IRepository repository){
        this.repository = repository;
    }

    public void add(ProgramState state){
        this.repository.add(state);
    }

    public ProgramState oneStep(ProgramState state) throws Exception {

        MyStack<IStatement> stack = state.getStack();
        if(stack.isEmpty()){
            // Throw empty stack

        }

        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void allStep() throws Exception {
        ProgramState program = repository.getCurrentProgram();
        while(!program.getStack().isEmpty()){
            oneStep(program);
            // Display
/*            MyStack<IStatement> stack = program.getStack();
            if(!stack.isEmpty()) {
                System.out.println(stack.peek());
            }*/

        }

    }

}

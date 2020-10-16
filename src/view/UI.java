package view;

import controller.Controller;
import model.*;
import model.expression.ValueExpression;
import model.expression.VarExpression;
import model.statement.*;
import model.types.IntType;
import model.values.IntValue;
import model.values.Value;

public class UI {

    Controller controller;

    public UI(Controller controller)
    {
        this.controller = controller;
    }

    public void run()
    {
        MyStack<IStatement> executionStack      = new MyStack<>();
        MyDictionary<String, Value> symbolTable = new MyDictionary<>();
        MyList<Value> output                    = new MyList<>();

        /*MyStack<IStatement> executionStack, MyDictionary<String , Value > symbolTable
            , MyList<Value> output ,IStatement program*/


        IStatement ex1 = new CompareStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompareStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new
                                VarExpression("v"))));

        ProgramState initialState = new ProgramState(executionStack, symbolTable, output, ex1);
        this.controller.add(initialState);

        try {
            this.controller.allStep();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}

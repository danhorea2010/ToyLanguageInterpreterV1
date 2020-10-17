package view;

import controller.Controller;
import model.MyDictionary;
import model.MyList;
import model.MyStack;
import model.ProgramState;
import model.expression.ArithmeticExpression;
import model.expression.ValueExpression;
import model.expression.VarExpression;

import model.statement.*;
import model.types.BoolType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class UI {

    Controller controller;

    public UI(Controller controller)
    {
        this.controller = controller;
    }

    // TODO: Custom exceptions...

    public void run()
    {
        MyStack<IStatement> executionStack      = new MyStack<>();
        MyDictionary<String, Value> symbolTable = new MyDictionary<>();
        MyList<Value> output                    = new MyList<>();

        /*MyStack<IStatement> executionStack, MyDictionary<String , Value > symbolTable
            , MyList<Value> output ,IStatement program*/

        // int v; v=2; Print(v)
        IStatement ex1 = new CompStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new
                                VarExpression("v"))));

        IStatement ex2 = new CompStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VarExpression("a"), new
                                        ValueExpression(new IntValue(1)))), new PrintStatement(new VarExpression("b"))))));

       /* IStatement ex3 = new CompStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new IfStatement(new VarExpression("a"),new AssignmentStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VarExpression("v"))))));*/

        ProgramState initialState = new ProgramState(executionStack, symbolTable, output, ex1);
        ProgramState program2     = new ProgramState(executionStack, symbolTable, output, ex2);
        //ProgramState program3     = new ProgramState(executionStack, symbolTable, output, ex3);


        this.controller.add(initialState);
        this.controller.add(program2);
        //this.controller.add(program3);

        try {
            this.controller.allStep();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MyList<Value> programOutput = initialState.getOutput();
        for(int i =0;i<programOutput.size();++i){
            Value currentValue = programOutput.get(i);
            System.out.println("Output " + (i + 1));
            if( currentValue.getType().equals(new IntType())){
                System.out.println(((IntValue)currentValue).getValue());
            }
            else if(currentValue.getType().equals(new BoolType())){
                System.out.println(((BoolValue)currentValue).getValue());
            }

        }
    }



}

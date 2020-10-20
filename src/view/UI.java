package view;

import controller.Controller;
import model.adt.MyDictionary;
import model.adt.MyList;
import model.adt.MyStack;
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

        // int v; v=2; Print(v)
        IStatement ex1 = new CompStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new
                                VarExpression("v"))));

        // int a; int b; a=2+3*5; b=a+1; Print(b);
        IStatement ex2 = new CompStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VarExpression("a"), new
                                        ValueExpression(new IntValue(1)))), new PrintStatement(new VarExpression("b"))))));

        // bool x; int y; a=true; (If a Then y=2 ELSE y=3); Print(y);
        IStatement ex3 = new CompStatement(new VariableDeclarationStatement("x",new BoolType()),
                new CompStatement(new VariableDeclarationStatement("y", new IntType()),
                        new CompStatement(new AssignmentStatement("x", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new IfStatement(new VarExpression("x"),new AssignmentStatement("y",new ValueExpression(new
                                        IntValue(2))), new AssignmentStatement("y", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VarExpression("y"))))));


        // All programs use the same stack and symbol table right now...
        ProgramState initialState = new ProgramState(executionStack, symbolTable, output, ex2);
        //ProgramState program2     = new ProgramState(executionStack, symbolTable, output, ex2);
        //ProgramState program3     = new ProgramState(executionStack, symbolTable, output, ex3);


        this.controller.add(initialState);
        //this.controller.add(program2);
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

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

    public void run()
    {
        MyStack<IStatement> executionStack      = new MyStack<>();
        MyDictionary<String, Value> symbolTable = new MyDictionary<>();
        MyList<Value> output                    = new MyList<>();

        // int v; v=2; Print(v)
        IStatement ex1 = new Composite(new VariableDeclaration("v",new IntType()),
                new Composite(new Assignment("v",new ValueExpression(new IntValue(2))),
                        new Print(new
                                VarExpression("v"))));

        // int a; int b; a=2+3*5; b=a+1; Print(b);
        IStatement ex2 = new Composite( new VariableDeclaration("a",new IntType()),
                new Composite(new VariableDeclaration("b",new IntType()),
                        new Composite(new Assignment("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new Composite(new Assignment("b",new ArithmeticExpression('+',new VarExpression("a"), new
                                        ValueExpression(new IntValue(1)))), new Print(new VarExpression("b"))))));

        // bool x; int y; a=true; (If x Then y=2 ELSE y=3); Print(y);
        IStatement ex3 = new Composite(new VariableDeclaration("x",new BoolType()),
                new Composite(new VariableDeclaration("y", new IntType()),
                        new Composite(new Assignment("x", new ValueExpression(new BoolValue(true))),
                                new Composite(new If(new VarExpression("x"),new Assignment("y",new ValueExpression(new
                                        IntValue(2))), new Assignment("y", new ValueExpression(new IntValue(3)))), new Print(new
                                        VarExpression("y"))))));

        // bool x; int y; x=true; (If x Then y=2 ELSE y=3); Print(y); Print(x);
        IStatement ex4 = new Composite(new VariableDeclaration("x",new BoolType()),
                new Composite(new VariableDeclaration("y", new IntType()),
                        new Composite(new Assignment("x", new ValueExpression(new BoolValue(true))),
                                new Composite(new If(new VarExpression("x"),new Assignment("y",new ValueExpression(new
                                        IntValue(2))), new Assignment("y", new ValueExpression(new IntValue(3)))), new Composite(new
                                        Print(new VarExpression("y")), new Print(new VarExpression("x"))
                                        )))));


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

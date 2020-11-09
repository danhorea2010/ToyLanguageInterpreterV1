package view;

import controller.Controller;
import model.ProgramState;
import model.adt.MyDictionary;
import model.adt.MyList;
import model.adt.MyStack;
import model.expression.ArithmeticExpression;
import model.expression.LogicExpression;
import model.expression.ValueExpression;
import model.expression.VarExpression;
import model.statement.*;
import model.types.BoolType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class UI {

    private final Controller controller;
    private boolean running;
    private final MyStack<IStatement> executionStack;
    private final MyDictionary<String, Value> symbolTable;
    private final MyDictionary<StringValue, BufferedReader> fileTable;

    private final MyList<Value> output;
    private final MyList<IStatement> programs;
    private ProgramState initialState;

    public UI(Controller controller)
    {
        running = true;
        executionStack = new MyStack<>();
        symbolTable =    new MyDictionary<>();
        fileTable      =new MyDictionary<>();
        output =         new MyList<>();
        programs =       new MyList<>();
        initialState = null;
        this.controller = controller;
    }

    static void printValue(Value value){

        if( value.getType().equals(new IntType())){
            System.out.println(((IntValue)value).getValue());
        }
        else if(value.getType().equals(new BoolType())){
            System.out.println(((BoolValue)value).getValue());
        }

    }

    static void printAllOutput(ProgramState initialState){
        initialState.getOutput().stream().forEach(UI::printValue);

        //
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void handleSelection(int selection)
    {
        selection = Math.abs(selection) - 1;

        if( selection == -1) {
            running = false;
            return;
        }

        if( selection < programs.size()) {
            IStatement statement = programs.get(selection);

            if (statement == null) {
                System.out.println("Invalid selection...");
            } else {
                initialState = new ProgramState(executionStack, symbolTable, output, fileTable,statement);
                this.controller.add(initialState);

                try {
                    this.controller.allStep();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                printAllOutput(initialState);
            }
        }else{
            System.out.println("Program index not in program list\n");
        }
    }

    void preLoadPrograms(MyList<IStatement> programs){

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
                                        ValueExpression(new IntValue(1)))), new Composite(new Print(new VarExpression("b")), new Print(new VarExpression("a")))))));

        // bool x; int y; x=true; (If x Then y=2 ELSE y=3); Print(y);
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

        // bool x; bool y; x = true; y = false; x = x || y; Print(x);
        IStatement ex5 = new Composite(new VariableDeclaration("x", new BoolType()),
                new Composite(new VariableDeclaration("y", new BoolType()),
                        new Composite(new Assignment("x", new ValueExpression(new BoolValue(true))),
                                new Composite(new Assignment("y", new ValueExpression(new BoolValue(false))),
                                     new Composite( new Assignment("x",new LogicExpression("||", new VarExpression("x"), new VarExpression("y"))), new Print(new VarExpression("x"))  ))
                                )));

        // bool x; bool y; x = true; y = false; x = x && y; Print(x);
        IStatement ex6 = new Composite(new VariableDeclaration("x", new BoolType()),
                new Composite(new VariableDeclaration("y", new BoolType()),
                        new Composite(new Assignment("x", new ValueExpression(new BoolValue(true))),
                                new Composite(new Assignment("y", new ValueExpression(new BoolValue(false))),
                                        new Composite( new Assignment("x",new LogicExpression("&&", new VarExpression("x"), new VarExpression("y"))), new Print(new VarExpression("x"))  ))
                        )));

        programs.add(ex1);
        programs.add(ex2);
        programs.add(ex3);
        programs.add(ex4);
        programs.add(ex5);
        programs.add(ex6);

    }


    void reset(){
        this.output.clear();
        this.symbolTable.clear();
    }

    public void run()
    {

        // List of pre-made programs.
        preLoadPrograms(programs);
        BufferedReader buffered = new BufferedReader(new InputStreamReader(System.in));


        while(running) {

            System.out.println("1. int v; v=2; Print(v)");
            System.out.println("2. int a; int b; a=2+3*5; b=a+1; Print(b); Print(a);");
            System.out.println("3. bool x; int y; x=true; (If x Then y=2 ELSE y=3); Print(y);");
            System.out.println("4. bool x; int y; x=true; (If x Then y=2 ELSE y=3); Print(y); Print(x);");
            System.out.println("5. bool x; bool y; x = true; y = false; x = x || y; Print(x);");
            System.out.println("6. bool x; bool y; x = true; y = false; x = x && y; Print(x);");
            System.out.println("0. exit");

            try {
                try {
                    int selection = Integer.parseInt(buffered.readLine());
                    handleSelection(selection);

                }catch (NumberFormatException ex){
                    System.out.println("Please input a number\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            reset();

        }

    }



}

package view;

import controller.Controller;
import model.ProgramState;
import model.adt.*;
import model.expression.*;
import model.statement.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;
import view.commands.Command;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commands;
    private final Controller controller;
    private boolean running;
    private final IStack<IStatement> executionStack;
    private final IDictionary<String, Value> symbolTable;
    private final IDictionary<StringValue, BufferedReader> fileTable;
    private final Heap heapTable;

    private final IList<Value> output;

    private final MyList<IStatement> programs;
    private ProgramState initialState;

    public TextMenu(Controller controller){
        this.controller = controller;
        this.executionStack = new MyStack<>();
        this.symbolTable    = new MyDictionary<>();
        this.fileTable      = new MyDictionary<>();
        this.output         = new MyList<>();
        this.programs       = new MyList<>();
        this.heapTable      = new Heap();
        running  = true;
        commands = new HashMap<>();
        initialState = null;

        init();
    }

    private void init()
    {
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

        // string varf; varf = "test.in"; openReadFile(varf); int varc; ReadFile(varf,varc); Print(varc) ;ReadFile(varf, varc); Print(varc); closeReadFile(varf);
        IStatement A3Test = new Composite(new VariableDeclaration("varf", new StringType()),
                new Composite(new Assignment("varf", new ValueExpression(new StringValue("test.in"))),
                        new Composite(new OpenReadFile(new VarExpression("varf")),
                                new Composite(new VariableDeclaration("varc", new IntType()),
                                        new Composite(new ReadFile(new VarExpression("varf"),"varc"),
                                                new Composite(new Print(new VarExpression("varc")),
                                                        new Composite(new ReadFile(new VarExpression("varf"),"varc"),
                                                                new Composite(new Print(new VarExpression("varc")) , new CloseReadFile(new VarExpression("varf"))
                                                                ))))))));

        // int x; int y; x = 32; y = 42; Print(x <= y);
        IStatement RelationTest = new Composite(
                new VariableDeclaration("x", new IntType()),
                new Composite(
                        new VariableDeclaration("y", new IntType()),
                        new Composite(new Assignment("x", new ValueExpression(new IntValue(32))),
                                new Composite(
                                        new Assignment("y", new ValueExpression(new IntValue(42))),
                                        new Print(new RelationalExpression("<=", new VarExpression("x"), new VarExpression("y")))
                                )
                        )
                )
        );



        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStatement newTest = new Composite(
                new VariableDeclaration("v", new RefType(new IntType())),
                new Composite(
                        new New("v", new ValueExpression(new IntValue(20))),
                        new Composite(
                                new VariableDeclaration("a", new RefType(new RefType(new IntType()))),
                                new Composite(
                                        new New("a", new VarExpression("v")),
                                        new Composite(
                                                new Print(new VarExpression("v")),
                                                new Print(new VarExpression("a"))
                                        )
                                )
                        )
                )
        );

        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStatement whileTest = new Composite(
                new VariableDeclaration("v", new IntType()),
                new Composite(
                        new Assignment("v", new ValueExpression(new IntValue(4))),
                        new Composite(
                                new While(
                                        new RelationalExpression(">", new VarExpression("v"), new ValueExpression(new IntValue(0))),
                                        new Composite(
                                                new Print(new VarExpression("v")),
                                                new Assignment("v", new ArithmeticExpression('-', new VarExpression("v"), new ValueExpression(new IntValue(1))))
                                        )
                                ),

                                new Print(new VarExpression("v"))

                        )
                )
        );

        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStatement readHeapTest = new Composite(
                new VariableDeclaration("v",new RefType(new IntType())),
                new Composite(
                        new New("v", new ValueExpression(new IntValue(20))),
                        new Composite(
                                new VariableDeclaration("a",new RefType(new RefType(new IntType()))),
                                new Composite(
                                        new New("a", new VarExpression("v")),
                                        new Composite(
                                                new Print(new ReadHeap(new VarExpression("v"))),
                                                new Print(new ArithmeticExpression('+', new ReadHeap(new ReadHeap(new VarExpression("a"))), new ValueExpression(new IntValue(5))))
                                        )
                                )
                        )
                )
        );

        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStatement writeHeapTest = new Composite(
                new VariableDeclaration("v", new RefType(new IntType())),
                new Composite(
                        new New("v", new ValueExpression(new IntValue(20))),
                        new Composite(
                                new Print(new ReadHeap(new VarExpression("v"))),
                                new Composite(
                                        new WriteHeap("v", new ValueExpression(new IntValue(30))),
                                        new Print(new ArithmeticExpression('+', new ValueExpression(new IntValue(5)), new ReadHeap(new VarExpression("v"))))
                                )
                        )
                )
        );

        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a))) - should fail
        IStatement heapShouldFail = new Composite(
                new VariableDeclaration("v",new RefType(new IntType())),
                new Composite(
                        new New("v", new ValueExpression(new IntValue(20))),
                        new Composite(
                                new VariableDeclaration("a",new RefType(new RefType(new IntType()))),
                                new Composite(
                                        new New("a", new VarExpression("v")),
                                        new Composite(
                                                new New("v", new ValueExpression(new IntValue(30))),
                                                new Print(new ReadHeap(new ReadHeap(new VarExpression("a"))))
                                        )
                                )
                        )
                )
        );


        programs.add(ex1);
        programs.add(ex2);
        programs.add(ex3);
        programs.add(ex4);
        programs.add(ex5);
        programs.add(ex6);
        programs.add(A3Test);
        programs.add(RelationTest);
        programs.add(newTest);
        programs.add(whileTest);
        programs.add(readHeapTest);
        programs.add(writeHeapTest);
        programs.add(heapShouldFail);

        this.addCommand(new RunExample( "1", "int v; v=2; Print(v);",controller));
        this.addCommand(new RunExample( "2", "int a; int b; a=2+3*5; b=a+1; Print(b); Print(a);",controller));
        this.addCommand(new RunExample( "3", "bool x; int y; x=true; (If x Then y=2 ELSE y=3); Print(y);",controller));
        this.addCommand(new RunExample( "4", "bool x; int y; x=true; (If x Then y=2 ELSE y=3); Print(y); Print(x);",controller));
        this.addCommand(new RunExample( "5", "bool x; bool y; x = true; y = false; x = x || y; Print(x);",controller));
        this.addCommand(new RunExample( "6", "bool x; bool y; x = true; y = false; x = x && y; Print(x);",controller));
        this.addCommand(new RunExample( "7", "string varf; varf = \"test.in\"; openReadFile(varf); int varc; ReadFile(varf,varc); Print(varc) ;ReadFile(varf, varc); Print(varc); closeReadFile(varf); ",controller));
        this.addCommand(new RunExample( "8", "int x; int y; x = 32; y = 42; Print(x <= y);",controller));
        this.addCommand(new RunExample("9", "Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)",controller));
        this.addCommand(new RunExample("10","int v; v=4; (while (v>0) print(v);v=v-1);print(v)",controller));
        this.addCommand(new RunExample("11", "Ref int v;new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5)",controller));
        this.addCommand(new RunExample("12", "Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);",controller));
        this.addCommand(new RunExample("13", "Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)));",controller));

        this.addCommand(new ExitCommand("0", "Exit"));


    }

    void reset(){
        this.initialState.clearProgram();
        //this.output.clear();
        //this.symbolTable.clear();
        //this.fileTable.clear();
    }

    private void addCommand(Command c){
        commands.put(c.getKey(), c);
    }

    private void printMenu(){
        for(Command com : commands.values()){
            if(Integer.parseInt(com.getKey()) != 0) {
                String line = String.format("%4s: %s", com.getKey(), com.getDescription());
                System.out.println(line);
            }
        }

        // There should always be an exit command
        Command exitCommand = commands.get("0");
        String line = String.format("%4s: %s", exitCommand.getKey(), exitCommand.getDescription());
        System.out.println(line);


    }

    public void show(){
        Scanner scanner = new Scanner(System.in);

        while(running){
            printMenu();
            System.out.println("Input option:");
            String key = scanner.nextLine();
            Command com = commands.get(key);
            if(com == null){
                System.out.println("Invalid option...");
                continue;
            }

            int integerKey = Integer.parseInt(key);
            if( integerKey > 0) {
                IStatement statement = programs.get(integerKey - 1);
                if (statement != null) {
                    initialState = new ProgramState(executionStack, symbolTable, output, fileTable, heapTable, statement);
                    this.controller.add(initialState);
                }
            }

            com.execute();
            reset();

        }

    }



}

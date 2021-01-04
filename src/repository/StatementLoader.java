package repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.expression.*;
import model.statement.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;

public class StatementLoader {

    public static ObservableList<IStatement> getStatements() {

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
        IStatement heapShouldNotFail = new Composite(
                new VariableDeclaration("v",new RefType(  new IntType())),
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

        IStatement heapShouldFailV2 = new Composite(
                new VariableDeclaration("v",new RefType(  new RefType(new IntType()))),
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

        // Broken: int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStatement whileTestBroken = new Composite(
                new VariableDeclaration("v", new IntType()),
                new Composite(
                        new Assignment("v", new ValueExpression(new IntValue(4))),
                        new Composite(
                                new While(
                                        new ValueExpression(new IntValue(32)),
                                        new Composite(
                                                new Print(new VarExpression("v")),
                                                new Assignment("v", new ArithmeticExpression('-', new VarExpression("v"), new ValueExpression(new IntValue(1))))
                                        )
                                ),

                                new Print(new VarExpression("v"))

                        )
                )
        );

//        int v; Ref int a; v=10;new(a,22);
//        fork(wH(a,30);v=32;print(v);print(rH(a)));
//        print(v);print(rH(a))

        IStatement forkTest1 = new Composite(
                new VariableDeclaration("v", new IntType()),
                new Composite(
                        new VariableDeclaration("a", new RefType(new IntType())),
                        new Composite(
                                new Assignment("v", new ValueExpression(new IntValue(10))),
                                new Composite(
                                        new New("a", new ValueExpression(new IntValue(22))),
                                        new Composite(
                                                new Fork( new Composite(
                                                        new WriteHeap("a", new ValueExpression(new IntValue(30))),
                                                        new Composite(
                                                                new Assignment("v", new ValueExpression(new IntValue(32))),
                                                                new Composite(
                                                                        new Print(new VarExpression("v")),
                                                                        new Print(new ReadHeap(new VarExpression("a")))
                                                                )
                                                        )
                                                )),
                                                new Composite(
                                                        new Print(new VarExpression("v")),
                                                        new Print(new ReadHeap(new VarExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        //        int v; Ref int a; v=10;new(a,22);
        //        fork(wH(a,30);v=32;print(v);print(rH(a)));
        //        fork(wH(a,30);v=32;print(v);print(rH(a)));
        //        print(v);print(rH(a))

        IStatement forkTest2 = new Composite(
                new VariableDeclaration("v", new IntType()),
                new Composite(
                        new VariableDeclaration("a", new RefType(new IntType())),
                        new Composite(
                                new Assignment("v", new ValueExpression(new IntValue(10))),
                                new Composite(
                                        new New("a", new ValueExpression(new IntValue(22))),
                                        new Composite(
                                                new Fork( new Composite(
                                                        new WriteHeap("a", new ValueExpression(new IntValue(30))),
                                                        new Composite(
                                                                new Assignment("v", new ValueExpression(new IntValue(32))),
                                                                new Composite(
                                                                        new Print(new VarExpression("v")),
                                                                        new Print(new ReadHeap(new VarExpression("a")))
                                                                )
                                                        )
                                                )),
                                                new Composite(
                                                        new Composite(
                                                                new Fork(
                                                                        new Composite(
                                                                                new WriteHeap("a", new ValueExpression(new IntValue(30))),
                                                                                new Composite(
                                                                                        new Assignment("v", new ValueExpression(new IntValue(32))),
                                                                                        new Composite(
                                                                                                new Print(new VarExpression("v")),
                                                                                                new Print(new ReadHeap(new VarExpression("a")))
                                                                                        )
                                                                                )
                                                                        )),
                                                                new Print(new VarExpression("v"))
                                                        ),
                                                        new Print(new ReadHeap(new VarExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        // Typechecker : bool v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStatement typeCheckerTest1 = new Composite(
                new VariableDeclaration("v", new BoolType()),
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

        // To Creating a Observable List
        ObservableList<IStatement> statements = FXCollections.observableArrayList(
                ex1
                ,ex2, ex3,
                ex4,ex5,ex6, A3Test,
                whileTest,
                typeCheckerTest1,
                forkTest1,
                forkTest2,
                newTest,
                readHeapTest,
                writeHeapTest,
                heapShouldNotFail,
                heapShouldFailV2);

        return statements;
    }

}

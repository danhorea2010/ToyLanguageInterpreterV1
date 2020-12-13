package model.statement;


import model.ProgramState;
import model.adt.MyDictionary;
import model.types.Type;

public interface IStatement {
    ProgramState execute(ProgramState state) throws Exception;
    MyDictionary<String, Type> typeCheck(MyDictionary<String,Type> typeEnvironment) throws Exception;


}

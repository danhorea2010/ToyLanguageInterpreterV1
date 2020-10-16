package model.statement;


import model.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState state) throws Exception;

}

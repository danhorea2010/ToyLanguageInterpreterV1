package model.values;

import model.types.IntType;
import model.types.Type;

public class IntValue implements Value {
    private final int value;

    public IntValue(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    @Override
    public Type getType() {
        return new IntType();
    }




}

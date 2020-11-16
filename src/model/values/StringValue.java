package model.values;

import model.types.StringType;
import model.types.Type;

public class StringValue implements Value {
    private final String value;

    public StringValue(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return ""+this.value;
    }

}

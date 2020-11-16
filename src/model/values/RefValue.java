package model.values;

import model.types.RefType;
import model.types.Type;

public class RefValue implements Value{

    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }


}
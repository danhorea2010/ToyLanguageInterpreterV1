package view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HeapWrapper {
    IntegerProperty address;
    StringProperty  value;

    public void setAddress(Integer value){
        addressProperty().set(value);
    }
    public IntegerProperty addressProperty() {
        if (address == null) address = new SimpleIntegerProperty(this, "address");
        return address;
    }
    public Integer getAddress(){
        return addressProperty().get();
    }

    public void setValue(String value){
        valueProperty().set(value);
    }
    public StringProperty valueProperty() {
        if (value == null) value = new SimpleStringProperty(this, "value");
        return value;
    }

    public String getValue(){
        return valueProperty().get();
    }




}

package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SymWrapper {

    StringProperty variableName;
    StringProperty value;

    public void setVariableName(String value){
        variableProperty().set(value);
    }

    public StringProperty variableProperty() {
        if (variableName == null) variableName = new SimpleStringProperty(this, "variableName");
        return variableName;
    }
    public String getVariableName(){
        return variableProperty().get();
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

package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import model.ProgramState;
import model.adt.*;
import model.statement.IStatement;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;
import repository.IRepository;
import repository.StateRepository;
import repository.StatementLoader;

import java.io.BufferedReader;
import java.util.concurrent.ExecutorService;

public class GuiController {

    private final IRepository repository;
    private boolean displayTag;
    private ExecutorService executor;

    // FXML things
    @FXML
    private ListView<IStatement> listView;
    @FXML
    private TextField noProgramStatesTextField;
    @FXML
    private Button loadSelectedButton;

    // Selected statement
    private final Controller controller;
    private final IStack<IStatement> executionStack;
    private final IDictionary<String, Value> symbolTable;
    private final IDictionary<StringValue, BufferedReader> fileTable;

    private final Heap heapTable;
    private final IList<Value> output;
    private ProgramState initialState;

    private final IDictionary<String, Type> typeEnvironment;
    private IStatement currentStatement;

    private int numberOfStatements;

    public GuiController(){

        this.executionStack = new MyStack<>();
        this.symbolTable    = new MyDictionary<>();
        this.fileTable      = new MyDictionary<>();
        this.output         = new MyList<>();
        this.heapTable      = new Heap();
        this.typeEnvironment = new MyDictionary<>();
        initialState = null;

        currentStatement = null;

        this.repository = new StateRepository("Guilog.txt");
        this.controller = new Controller(this.repository);

        numberOfStatements = 0;

        // For debug only
        this.controller.setDisplayTag(true);
    }

    @FXML
    void initialize() {
        var statements = StatementLoader.getStatements();
        listView.setItems(statements);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //listView.getSelectionModel().selectIndices(0);

        listView.autosize();

        // Set textField text

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IStatement>() {
            @Override
            public void changed(ObservableValue<? extends IStatement> observableValue, IStatement oldStatement, IStatement newStatement) {
                currentStatement = newStatement;
            }
        });

    }

    public void loadSelectedStatement(){
        System.out.println("Button pressed");

        if (currentStatement != null) {
            // Only run program if the typechecker works
            try {
                currentStatement.typeCheck((MyDictionary<String, Type>) typeEnvironment);
                initialState = new ProgramState(executionStack, symbolTable, output, fileTable, heapTable, currentStatement);
                this.controller.add(initialState);


                // Execute statement
                try {
                    this.controller.allStepAll();
                }catch (Exception e){
                    e.printStackTrace();
                }


                reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // Resets program state
    void reset(){
        this.initialState.clearProgram();
        this.typeEnvironment.clear();
        //this.output.clear();
        //this.symbolTable.clear();
        //this.fileTable.clear();
    }



}

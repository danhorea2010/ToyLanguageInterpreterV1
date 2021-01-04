package view;

import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import java.util.ArrayList;
import java.util.List;
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
    @FXML
    private TableView<String> heapTableView;
    @FXML
    TableColumn<String, Value> address = new TableColumn<>("Address");
    @FXML
    TableColumn<String, Value> value   = new TableColumn<>("Value");
    @FXML
    private Button oneStepButton;
    @FXML
    private ListView<Value> outputListView;
    @FXML
    private ListView<StringValue> fileTableListView;
    @FXML
    private ListView<Integer> programStateListView;
    @FXML
    private ListView<IStatement> executionStackListView;


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
    private ProgramState currentProgramState;

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
        this.currentProgramState = null;

        this.repository = new StateRepository("Guilog.txt");
        this.controller = new Controller(this.repository);

        numberOfStatements = 0;

        // For debug only
        this.controller.setDisplayTag(true);
    }

    @FXML
    void initialize() {
        // Init statement list
        var statements = StatementLoader.getStatements();
        listView.setItems(statements);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //listView.getSelectionModel().selectIndices(0);
        listView.autosize();

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IStatement>() {
            @Override
            public void changed(ObservableValue<? extends IStatement> observableValue, IStatement oldStatement, IStatement newStatement) {
                currentStatement = newStatement;
            }
        });

        // Init output list
        outputListView.setItems(FXCollections.observableList(output.getList()));

        // Convert set to list
        List<StringValue> newList = new ArrayList<>(fileTable.keyset());
        fileTableListView.setItems(FXCollections.observableList(newList));

        // Set textField text

        // Init heap Table

        // Id list
        List<Integer> idList = new ArrayList<>();
        var programStates    = this.controller.getProgramStates().getList();
        for(var state : programStates){
            idList.add(state.getID());
        }
        programStateListView.setItems(FXCollections.observableList(idList));

        programStateListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer oldInteger, Integer newInteger) {
                if (newInteger != null) {
                    var programStates = controller.getProgramStates().getList();
                    for (var state : programStates) {
                        if (state.getID() == newInteger) {
                            currentProgramState = state;

                            // Sym table


                            // Exec stack
                            var statementList = new ArrayList<IStatement>();
                            var stackCopy = new MyStack<>((MyStack<IStatement>) currentProgramState.getStack());
                            while (!stackCopy.isEmpty()) {
                                statementList.add(stackCopy.pop());
                            }
                            executionStackListView.setItems(FXCollections.observableList(statementList));


                            break;
                        }
                    }

                }
            }
        });

        noProgramStatesTextField.setText(String.valueOf(this.controller.getProgramStates().size()));

    }



    public void oneStepButtonCallback(){
        System.out.println("One step button pressed");

        if (currentStatement != null ) {

            try {
                if( initialState == null || controller.getProgramStates().size() == 0) {
                    initialState = new ProgramState(executionStack, symbolTable, output, fileTable, heapTable, currentStatement);
                    this.controller.add(initialState);
                }


                try {
                    this.controller.oneStepForAllProgramsCallBack();
                }catch (Exception e){
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        outputListView.setItems(FXCollections.observableList(output.getList()));
        List<StringValue> newList = new ArrayList<>(fileTable.keyset());
        fileTableListView.setItems(FXCollections.observableList(newList));

        // Program state id list
        List<Integer> idList = new ArrayList<>();
        var programStates    = this.controller.getProgramStates().getList();
        for(var state : programStates){
            idList.add(state.getID());
        }
        programStateListView.setItems(FXCollections.observableList(idList));

        // Statement stack
        if(currentProgramState != null) {
            var statementList = new ArrayList<IStatement>();
            var stackCopy = new MyStack<>((MyStack<IStatement>) currentProgramState.getStack());
            while (!stackCopy.isEmpty()) {
                statementList.add(stackCopy.pop());
            }
            executionStackListView.setItems(FXCollections.observableList(statementList));
        }

        noProgramStatesTextField.setText(String.valueOf(this.controller.getProgramStates().size()));


        if( controller.getProgramStates().size() == 0){
            reset();
        }

    }

    public void loadSelectedStatement(){
        System.out.println("All step button pressed");

        if (currentStatement != null) {
            // Only run program if the typechecker works
            if(initialState != null) {
                reset();
            }

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


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        outputListView.setItems(FXCollections.observableList(output.getList()));
        List<StringValue> newList = new ArrayList<>(fileTable.keyset());
        fileTableListView.setItems(FXCollections.observableList(newList));

        // Id list
        List<Integer> idList = new ArrayList<>();
        var programStates    = this.controller.getProgramStates().getList();
        for(var state : programStates){
            idList.add(state.getID());
        }
        noProgramStatesTextField.setText(String.valueOf(this.controller.getProgramStates().size()));

        programStateListView.setItems(FXCollections.observableList(idList));



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

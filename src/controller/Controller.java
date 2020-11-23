package controller;

import exceptions.EmptyCollectionException;
import model.ProgramState;
import model.adt.MyStack;
import model.statement.IStatement;
import model.values.RefValue;
import model.values.Value;
import repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {

    private final IRepository repository;
    private boolean displayTag;

    public Controller(IRepository repository){
        this.repository = repository;
        this.displayTag = false;
    }

    public void setDisplayTag(boolean displayTag){
        this.displayTag = displayTag;
    }

    public void add(ProgramState state){
        this.repository.add(state);
    }

    // Garbage collector
    Map<Integer, Value> garbageCollector(List<Integer> symbolTableAddresses, Map<Integer, Value> heap){
        Map<Integer, Value> heapMap = heap.entrySet().stream()
                .filter(e -> symbolTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Search in symbol table if there are any references to heap
        for(var value: symbolTableAddresses )
        {
            if(heap.get(value) instanceof RefValue)
            {
                RefValue refValue = (RefValue)heap.get(value) ;
                Integer address = refValue.getAddress();

                Value value1 = heap.get(refValue.getAddress());
                boolean keepGoing = true;
                while(keepGoing){
                    if(!(value1 instanceof RefValue)){
                        keepGoing = false;
                    }
                    else{
                        // Should this also be added to heapMap?
                        Integer nextAddress = ((RefValue) value1).getAddress();
//                        if(!heapMap.containsKey(nextAddress)){
//                            heapMap.put(nextAddress, value1);
//                        }

                        value1 = heap.get(nextAddress);
                    }
                }

                if(!heapMap.containsKey(address)){
                    heapMap.put(address, value1);
                }

            }
        }

        return heapMap;
    }

    List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValues){
        return symbolTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map   (v -> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public ProgramState oneStep(ProgramState state) throws Exception {

        MyStack<IStatement> stack = (MyStack<IStatement>) state.getStack();
        if(stack.isEmpty()){
            // Throw empty stack
            throw new EmptyCollectionException("Execution stack is empty\n");

        }

        //IStatement lastState = state.deepCopy(state.getOriginalProgram());

        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void allStep() throws Exception {
        ProgramState program = repository.getCurrentProgram();
        this.repository.logProgramState(program);
        if(this.displayTag) {
            System.out.println(program);

        }

        while(!program.getStack().isEmpty()){
            program = oneStep(program);
            if(this.displayTag) {
                System.out.println(program);
            }

            this.repository.logProgramState(program);

            program.getHeapTable().setContent(garbageCollector(
                    getAddressesFromSymbolTable(program.getSymbolTable().values()),
                    program.getHeapTable().getContent()
            ));

            this.repository.logProgramState(program);

        }

    }

}

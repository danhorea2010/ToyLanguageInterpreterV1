package controller;

import model.ProgramState;
import model.adt.MyList;
import model.values.RefValue;
import model.values.Value;
import repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    private final IRepository repository;
    private boolean displayTag;

    // ?
    private ExecutorService executor;

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
                        if(!heapMap.containsKey(nextAddress)){
                            heapMap.put(nextAddress, value1);
                        }

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


//    public void allStep() throws Exception {
//        ProgramState program = repository.getCurrentProgram();
//        this.repository.logProgramState(program);
//        if(this.displayTag) {
//            System.out.println(program);
//
//        }
//
//        while(!program.getStack().isEmpty()){
//            program = oneStep(program);
//            if(this.displayTag) {
//                System.out.println(program);
//            }
//
//            this.repository.logProgramState(program);
//
//            program.getHeapTable().setContent(garbageCollector(
//                    getAddressesFromSymbolTable(program.getSymbolTable().values()),
//                    program.getHeapTable().getContent()
//            ));
//
//            this.repository.logProgramState(program);
//
//        }
//
//    }

    // FIXME: Change List<> to MyList<> ?
    private List<ProgramState> removeCompletedPrograms(List<ProgramState> programList){
        return programList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    // New oneStep and allStep
    private void oneStepForAllPrograms(List<ProgramState> programs) throws Exception {

        programs.forEach(v -> {
            try {
                this.repository.logProgramState(v);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        List<Callable<ProgramState>> callList = programs.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::oneStep))
                .collect(Collectors.toList());

        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        programs.addAll(newProgramList);

        programs.forEach(program -> {
            try {
                repository.logProgramState(program);

                if( displayTag) {
                    System.out.println(program);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        this.repository.setProgramList(new MyList<>(programs));
    }

    public void allStepAll() throws Exception {

        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programList = removeCompletedPrograms(this.repository.getProgramList().getList());
        while(programList.size() > 0 ){
            programList.forEach(v -> v.getHeapTable().setContent(garbageCollector(getAddressesFromSymbolTable(v.getSymbolTable().values()),
                    v.getHeapTable().getContent())));

            oneStepForAllPrograms(programList);
            programList = removeCompletedPrograms(this.repository.getProgramList().getList());
        }
        executor.shutdownNow();

        this.repository.setProgramList(new MyList<>(programList));

    }



}

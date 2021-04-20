package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccidentMem {

    private Map<Integer, Accident> accidents = new HashMap<>();

    public AccidentMem() {
        init();
    }

    public List<Accident> getAccidents() {
        List<Accident> result = new ArrayList<>();
        for (Integer key : accidents.keySet()) {
            result.add(accidents.get(key));
        }
        return result;
    }

    private void init() {
        accidents.put(0, new Accident("Accident1", "Some accident 1", "Some Street 4"));
        accidents.put(1, new Accident("Accident2", "Some accident 2", "Some Street 8"));
        accidents.put(2, new Accident("Accident3", "Some accident 3", "Some Street 16"));
    }
}

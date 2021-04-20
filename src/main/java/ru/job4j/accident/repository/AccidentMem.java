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


    public List<Accident> getAccidents() {
        List<Accident> result = new ArrayList<>();
        for (Integer key : accidents.keySet()) {
            result.add(accidents.get(key));
        }
        return result;
    }

    public void create(Accident accident) {
        accidents.put(accident.getId(), accident);
    }
}

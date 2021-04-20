package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.*;

@Repository
public class AccidentMem {

    private Map<Integer, Accident> accidents = new HashMap<>();
    private int count = 1;

    public List<Accident> getAccidents() {
        List<Accident> result = new ArrayList<>();
        for (Integer key : accidents.keySet()) {
            result.add(accidents.get(key));
        }
        return result;
    }

    public void create(Accident accident) {
         if (accident.getId() != 0) {
            accidents.put(accident.getId(), accident);
        } else {
            accident.setId(count);
            accidents.put(count, accident);
            count++;
        }
    }

    public Optional<Accident> findById(int id) {
        for (Accident accident : getAccidents()) {
            if (accident.getId() == id) {
                return Optional.of(accidents.get(id));
            }
        }
        return Optional.empty();
    }
}

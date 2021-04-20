package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.*;

@Repository
public class AccidentMem {
    private Map<Integer, Accident> accidents = new HashMap<>();

    private Map<Integer, AccidentType> types = new HashMap<>();

    private int count = 1;

    public AccidentMem() {
        types.put(0, AccidentType.of(0, "Две машины"));
        types.put(1, AccidentType.of(1, "Машина и человек"));
        types.put(2, AccidentType.of(2, "Машина и велосипед"));
    }

    public List<Accident> getAccidents() {
        List<Accident> result = new ArrayList<>();
        for (Integer key : accidents.keySet()) {
            result.add(accidents.get(key));
        }
        return result;
    }

    public List<AccidentType> getTypes() {
        List<AccidentType> result = new ArrayList<>();
        for (Integer key : types.keySet()) {
            result.add(types.get(key));
        }
        return result;
    }

    public void create(Accident accident) {
         if (accident.getId() != 0) {
             accident.setType(accidents.get(accident.getId()).getType());
             accidents.put(accident.getId(), accident);
        } else {
            accident.setId(count);
            accident.setType(types.get(accident.getType().getId()));
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

package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private Map<Integer, AccidentType> types = new ConcurrentHashMap<>();

    private Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    private AtomicInteger count = new AtomicInteger(1);

    public AccidentMem() {
        types.put(0, AccidentType.of(0, "Две машины"));
        types.put(1, AccidentType.of(1, "Машина и человек"));
        types.put(2, AccidentType.of(2, "Машина и велосипед"));

        rules.put(0, Rule.of(0, "Статья 1"));
        rules.put(1, Rule.of(1, "Статья 2"));
        rules.put(2, Rule.of(2, "Статья 3"));
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }

    public Collection<AccidentType> getTypes() {
        return types.values();
    }

    public Collection<Rule> getRules() {
        return rules.values();
    }

    public void create(Accident accident, String[] ids) {
        if (accident.getId() != 0) {
            accident.setRules(accidents.get(accident.getId()).getRules());
            accident.setType(accidents.get(accident.getId()).getType());
            accidents.put(accident.getId(), accident);
        } else {
            Set<Rule> rules = new HashSet<>();
            for (String id : ids) {
                rules.add(this.rules.get(Integer.parseInt(id)));
            }
            accident.setRules(rules);
            accident.setId(count.intValue());
            accident.setType(types.get(accident.getType().getId()));
            accidents.put(count.intValue(), accident);
            count.incrementAndGet();
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

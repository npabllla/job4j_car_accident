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

    private Map<Integer, AccidentType> types = new HashMap<>();

    private Set<Rule> rules = new HashSet<>();

    private AtomicInteger count = new AtomicInteger(1);

    public AccidentMem() {
        types.put(0, AccidentType.of(0, "Две машины"));
        types.put(1, AccidentType.of(1, "Машина и человек"));
        types.put(2, AccidentType.of(2, "Машина и велосипед"));

        rules.add(Rule.of(0, "Статья 1"));
        rules.add(Rule.of(1, "Статья 2"));
        rules.add(Rule.of(2, "Статья 3"));
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }

    public List<AccidentType> getTypes() {
        List<AccidentType> result = new ArrayList<>();
        for (Integer key : types.keySet()) {
            result.add(types.get(key));
        }
        return result;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public void create(Accident accident, String[] ids) {
        Set<Rule> rules = setRules(ids);
        if (accident.getId() != 0) {
            accident.setRules(rules);
            accident.setType(accidents.get(accident.getId()).getType());
            accidents.put(accident.getId(), accident);
        } else {
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

    private Set<Rule> setRules(String[] ids) {
        Set<Rule> rules = new HashSet<>();
        for (Rule rule : this.rules) {
            for (String id : ids) {
                if (rule.getId() == Integer.parseInt(id)) {
                    rules.add(rule);
                }
            }
        }
        return rules;
    }
}

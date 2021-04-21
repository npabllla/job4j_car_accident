package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class AccidentHibernate {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    public void saveOrUpdate(Accident accident, String[] ruleIds) {
        if (findById(accident.getId()).isPresent()) {
            update(accident, ruleIds);
        } else {
            save(accident, ruleIds);
        }
    }

    public void save(Accident accident, String[] ruleIds) {
        transaction(session -> {
            for (String id : ruleIds) {
                Rule rule = session.find(Rule.class, Integer.parseInt(id));
                accident.addRule(rule);
            }
            session.save(accident);
            return null;
        });
    }

    public void update(Accident accident, String[] ruleIds) {
        transaction(session -> {
            for (String id : ruleIds) {
                Rule rule = session.find(Rule.class, Integer.parseInt(id));
                accident.addRule(rule);
            }
            session.update(accident);
            return null;
        });
    }

    public Optional<Accident> findById(int id) {
        return transaction(session -> {
            final Query query = session.createQuery("from Accident where id=:id");
            query.setParameter("id", id);
            return query.getResultList().size() > 0 ? Optional.of((Accident) query.getResultList().get(0))
                    : Optional.empty();
        });
    }

    public List<Accident> getAccidents() {
        return (List<Accident>) transaction(session -> session.createQuery("from Accident").list());
    }

    public List<AccidentType> getTypes() {
        return (List<AccidentType>) transaction(session -> session.createQuery("from AccidentType").list());
    }

    public List<Rule> getRules() {
        return (List<Rule>) transaction(session -> session.createQuery("from Rule").list());
    }

    private <T> T transaction(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
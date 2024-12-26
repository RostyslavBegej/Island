package org.application.island;

import lombok.Getter;
import lombok.Setter;
import org.application.config.database.Record;
import org.application.config.factory.Factory;
import org.application.global.GlobalVariables;
import org.application.objects.Organism;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Getter
@Setter
public class Location {

    private final Factory factory = Factory.getInstance();
    private ThreadLocalRandom random = GlobalVariables.random;
    private ConcurrentMap<Class<? extends Organism>, Set<Organism>> organisms = new ConcurrentHashMap<>();
    @Getter
    private static ConcurrentMap<Class<? extends Organism>, Statistic> statistic = new ConcurrentHashMap<>();

    private final int x;
    private final int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void fillLocation(Class<? extends Organism> value, Record record, int countOrganisms) {
        Set<Organism> set = Collections.synchronizedSet(new HashSet<>());
        for (int i = 0; i < countOrganisms; i++) {
            set.add(factory.create(value, record, this));
        }
        organisms.put(value, set);
    }

    public int countOfAnimals(int maxCountOnCell) {
        return random.nextInt(maxCountOnCell + 1);
    }

    public Set<Organism> getSetOrganismsOnLocation() {
        return organisms.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public void removeOrganism(Organism organism) {
        organisms.get(organism.getClass()).remove(organism);
    }

    public Set<Organism> getSpeciesAnimalsOnLocation(Organism organism) {
        Map<Class<? extends Organism>, Set<Organism>> organismOnLocation = getOrganisms();
        if (!organismOnLocation.containsKey(organism.getClass())) return new HashSet<>();
        return organismOnLocation.get(organism.getClass());
    }

    public boolean checkMaxCountOrganismOnLocation(Organism organism) {
        int countOnLocation = getSpeciesAnimalsOnLocation(organism).size();
        int maxCountOnCell = organism.getMaxCountOnCell();
        return countOnLocation >= maxCountOnCell;
    }

    public static Statistic getOrganismStatistic(Class<? extends Organism> clazz) {
        if (!statistic.containsKey(clazz)) {
            statistic.put(clazz, new Statistic());
        }
        return statistic.get(clazz);
    }

    @Getter
    public static class Statistic implements Cloneable {

        private AtomicInteger all = new AtomicInteger();
        private AtomicInteger alive = new AtomicInteger();
        private AtomicInteger born = new AtomicInteger();
        private AtomicInteger ate = new AtomicInteger();
        private AtomicInteger starving = new AtomicInteger();
        private AtomicInteger killed = new AtomicInteger();
        private AtomicInteger dead = new AtomicInteger();

        private Statistic() {
        }

        public void logAllOrganisms() {
            all.incrementAndGet();
        }

        public void logAliveOrganisms() {
            alive.incrementAndGet();
        }

        public void logBornOrganisms() {
            born.incrementAndGet();
        }

        public void logAteOrganisms() {
            ate.incrementAndGet();
        }

        public void logStarvingOrganisms() {
            starving.incrementAndGet();
        }

        public void logKilledOrganisms() {
            killed.incrementAndGet();
        }

        public void logDeadOrganisms() {
            dead.incrementAndGet();
            alive.decrementAndGet();
        }

        @Override
        public Statistic clone() {
            try {
                Statistic clone = (Statistic) super.clone();
                clone.all = new AtomicInteger(all.get());
                clone.alive = new AtomicInteger(alive.get());
                clone.born = new AtomicInteger(born.get());
                clone.ate = new AtomicInteger(ate.get());
                clone.starving = new AtomicInteger(starving.get());
                clone.killed = new AtomicInteger(killed.get());
                clone.dead = new AtomicInteger(dead.get());
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }
}
package org.application.console;

import lombok.SneakyThrows;
import org.application.island.Island;
import org.application.island.Location;
import org.application.objects.Organism;
import org.application.objects.animals.Animal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Console implements Runnable {

    private Map<Class<? extends Organism>, Location.Statistic> statistic;
    private final Island island;
    private final long start = System.currentTimeMillis();

    public Console(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        displayInfo();
    }

    public void displayInfo() {
        statistic = cloneMap(Location.getStatistic());
        AllStatistic allStatistic = new AllStatistic(statistic);

        System.out.println("_".repeat(98));
        System.out.printf("|%-12s| %-10s| %-10s| %-10s| %-10s| %-10s| %-10s| %-10s|%n", "Organism", "All", "Alive", "Born", "Ate", "Starving", "Killed", "Dead");
        System.out.printf("|%-12s|%-10s|%-10s|%-10s|%-10s|%-10s|%-10s|%-10s|%n", "_".repeat(12), "_".repeat(11), "_".repeat(11), "_".repeat(11), "_".repeat(11), "_".repeat(11), "_".repeat(11), "_".repeat(11));
        statistic.forEach((key, value) -> System.out.printf("|%-12s| %-10d| %-10d| %-10d| %-10d| %-10d| %-10d| %-10d|%n", key.getSimpleName(), value.getAll().get(), value.getAlive().get(), value.getBorn().get(), value.getAte().get(), value.getStarving().get(), value.getKilled().get(), value.getDead().get()));
        System.out.println("|" + "_".repeat(96) + "|");
        System.out.printf("|%-12s| %-10d| %-10d| %-10d| %-10d| %-10d| %-10d| %-10d|%n", "All animals", allStatistic.all, allStatistic.alive, allStatistic.born, allStatistic.ate, allStatistic.starving, allStatistic.killed, allStatistic.dead);
        System.out.println("|" + "_".repeat(96) + "|");

        time();
        System.out.println("\n".repeat(1));
    }

    private Map<Class<? extends Organism>, Location.Statistic> cloneMap(Map<Class<? extends Organism>, Location.Statistic> original) {
        Map<Class<? extends Organism>, Location.Statistic> copy = new HashMap<>();
        synchronized (Collections.unmodifiableMap(original)) {
            original.forEach((k, v) -> copy.put(k, v.clone()));
        }
        return copy;
    }

    private void time() {
        long end = System.currentTimeMillis();
        long millisDiff = end - start;
        int second = (int) millisDiff / 1000;
        int min = second / 60;
        if (second < 60) System.out.printf("Life time: %d sec.%n", second);
        if (second >= 60) System.out.printf("Life time: %d min. %d sec.%n", min, second - min * 60);
    }

    private static class AllStatistic {

        private final int all;
        private final int alive;
        private final int born;
        private final int ate;
        private final int starving;
        private final int killed;
        private final int dead;

        private AllStatistic(Map<Class<? extends Organism>, Location.Statistic> statistic) {
            all = load(statistic, organismStatistic -> organismStatistic.getAll().get());
            alive = load(statistic, organismStatistic -> organismStatistic.getAlive().get());
            born = load(statistic, organismStatistic -> organismStatistic.getBorn().get());
            ate = load(statistic, organismStatistic -> organismStatistic.getAte().get());
            starving = load(statistic, organismStatistic -> organismStatistic.getStarving().get());
            killed = load(statistic, organismStatistic -> organismStatistic.getKilled().get());
            dead = load(statistic, organismStatistic -> organismStatistic.getDead().get());
        }


        // TODO: implement Callable
        @SneakyThrows
        private Integer load(Map<Class<? extends Organism>, Location.Statistic> statistic, Function<Location.Statistic, Integer> function) {
            return statistic.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().getSuperclass().getSuperclass() == Animal.class)
                    .map(Map.Entry::getValue)
                    .reduce(0, (total, val) -> total + function.apply(val), Integer::sum);
        }
    }
}
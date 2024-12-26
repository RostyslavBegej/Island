package org.application;

import org.application.global.GlobalVariables;
import org.application.island.Island;
import org.application.island.Location;
import org.application.objects.Organism;
import org.application.objects.plants.Plant;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlantGrowth implements Runnable {

    protected final Island island;
    protected final Location[][] locations;

    private final ThreadLocalRandom random = GlobalVariables.random;

    public PlantGrowth(Island island) {
        this.island = island;
        this.locations = island.getLocations();
    }

    protected void doAction(Location location) {
        location.getSetOrganismsOnLocation()
                .stream()
                .filter(organism -> organism instanceof Plant)
                .map(Plant.class::cast)
                .map(Plant::getClass)
                .distinct()
                .forEach(clazz -> growthPlant(clazz, location));
    }

    private void growthPlant(Class<? extends Plant> clazz, Location location) {
        Plant plant = (Plant) location.getOrganisms().get(clazz).stream().findFirst().get();
        int randomCount = random.nextInt(plant.getMaxCountOnCell() + 1);
        int newPlants = randomCount * plant.getChanceToReproduce() / 100;

        Set<Organism> plants = Stream.generate(plant::multiply)
                .limit(newPlants)
                .collect(Collectors.toSet());

        location.getOrganisms().merge(plant.getClass(), plants, (set1, set2) -> {
            set1.addAll(set2);
            return set1;
        });
    }

    @Override
    public void run() {
        GlobalVariables.lock.lock();
        Arrays.stream(locations)
                .flatMap(Arrays::stream)
                .forEach(this::doAction);
        GlobalVariables.lock.unlock();
    }
}
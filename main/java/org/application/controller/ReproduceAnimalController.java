package org.application.controller;

import org.application.global.GlobalVariables;
import org.application.interfaces.Reproducible;
import org.application.island.Island;
import org.application.island.Location;
import org.application.objects.Organism;
import org.application.objects.animals.Animal;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReproduceAnimalController extends Controller {

    ThreadLocalRandom random = GlobalVariables.random;

    public ReproduceAnimalController(Island island) {
        super(island);
    }

    @Override
    public void start() {
        GlobalVariables.lock.lock();
        Arrays.stream(locations)
                .flatMap(Arrays::stream)
                .peek(this::doAction)
                .flatMap(location -> location.getSetOrganismsOnLocation().stream())
                .filter(organism -> organism instanceof Animal animal && !animal.isCanReproduce())
                .forEach(organism -> organism.setCanReproduce(true));
        GlobalVariables.lock.unlock();
    }

    @Override
    protected void doAction(Location location) {
        location.getSetOrganismsOnLocation()
                .stream()
                .filter(organism -> organism instanceof Animal animal && animal.isCanReproduce())
                .forEach(organism -> reproduce(organism, location));
    }

    private void reproduce(Reproducible reproducible, Location location) {
        Organism organism = (Organism) reproducible;

        int countOnLocation = location.getSpeciesAnimalsOnLocation(organism).size();
        int maxCountOnCell = organism.getMaxCountOnCell();
        if (countOnLocation >= maxCountOnCell) return;

        Set<Organism> reproducibleAnimalSameSpeciesOnLocation = reproducibleAnimalSameSpeciesOnLocation(organism, location);
        long countReproduceAnimalOnLocation = reproducibleAnimalSameSpeciesOnLocation.size();

        double chance = random.nextInt(100) + 1;
        if (chance > organism.getChanceToReproduce() || countReproduceAnimalOnLocation < 1) return;

        Organism organismToReproduce = reproducibleAnimalSameSpeciesOnLocation.stream().findAny().get();

        int countChild = random.nextInt(((Animal) organism).getMaxChild()) + 1;
        countChild = Math.min(countChild, (maxCountOnCell - countOnLocation));

        Set<Organism> child = Collections.synchronizedSet(Stream.generate(organism::multiply)
                .limit(countChild)
                .collect(Collectors.toSet()));

        location.getOrganisms().merge(organism.getClass(), child, (set1, set2) -> {
            set1.addAll(set2);
            return set1;
        });
        organism.setCanReproduce(false);
        organismToReproduce.setCanReproduce(false);
    }

    private Set<Organism> reproducibleAnimalSameSpeciesOnLocation(Organism organism, Location location) {
        return location.getSpeciesAnimalsOnLocation(organism)
                .stream()
                .filter(rec -> rec instanceof Animal animal && animal.isCanReproduce() && rec != organism)
                .collect(Collectors.toSet());
    }
}
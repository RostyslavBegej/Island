package org.application.controller;

import org.application.global.GlobalVariables;
import org.application.island.Island;
import org.application.island.Location;
import org.application.objects.Organism;
import org.application.objects.animals.Animal;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EatController extends Controller {

    public EatController(Island island) {
        super(island);
    }

    @Override
    protected void doAction(Location location) {
        location.getSetOrganismsOnLocation()
                .stream()
                .filter(organism -> organism instanceof Animal animal)
                .map(Animal.class::cast)
                .filter(Animal::canEat)
                .forEach(animal -> eat(animal, location));
    }

    private void eat(Animal animal, Location location) {
        List<Organism> targets = location.getSetOrganismsOnLocation()
                .stream()
                .filter(organism -> animal.getTargetMatrix().containsKey(organism.getObjectType()))
                .collect(Collectors.toList());
        if (targets.isEmpty()) return;
        Collections.shuffle(targets);
        Organism organism = targets.get(GlobalVariables.random.nextInt(targets.size()));
        animal.eat(organism);
    }
}
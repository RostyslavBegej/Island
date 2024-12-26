package org.application.controller;

import org.application.global.GlobalVariables;
import org.application.island.Island;
import org.application.island.Location;

import java.util.Arrays;

public abstract class Controller {

    protected final Island island;
    protected final Location[][] locations;

    public Controller(Island island) {
        this.island = island;
        this.locations = island.getLocations();
    }

    protected abstract void doAction(Location location);

    public void start() {
        GlobalVariables.lock.lock();
        Arrays.stream(locations)
                .flatMap(Arrays::stream)
                .forEach(this::doAction);
        GlobalVariables.lock.unlock();
    }
}
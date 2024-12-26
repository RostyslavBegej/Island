package org.application;

import org.application.controller.*;
import org.application.island.Island;

public class Simulation implements Runnable {

    private final Island island;

    private final Controller move;
    private final Controller reproduce;
    private final Controller starving;
    private final Controller eat;

    public Simulation(Island island) {
        this.island = island;
        move = new MoveController(island);
        reproduce = new ReproduceAnimalController(island);
        starving = new StarvingController(island);
        eat = new EatController(island);
    }

    @Override
    public void run() {
        move.start();
        reproduce.start();
        eat.start();
        starving.start();
    }
}

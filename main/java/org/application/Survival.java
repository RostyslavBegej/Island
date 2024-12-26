package org.application;

import org.application.console.Console;
import org.application.island.Island;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Survival {
    public static void main(String[] args) {
        Island island = new Island();
        Console console = new Console(island);
        Simulation simulation = new Simulation(island);
        PlantGrowth plantGrowth = new PlantGrowth(island);

        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
        service.scheduleAtFixedRate(plantGrowth, 0, 1, TimeUnit.SECONDS);
        service.scheduleAtFixedRate(simulation, 1, 1, TimeUnit.SECONDS);
        service.scheduleAtFixedRate(console, 0, island.getMsToReloadConsole(), TimeUnit.MILLISECONDS);
    }
}
package org.application.island;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.application.annotations.Config;
import org.application.config.database.DataBase;
import org.application.config.database.Record;
import org.application.objects.Organism;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Config(filePath = "island/island.yaml")
public class Island {

    private final Map<Class<? extends Organism>, Record> dataBase = DataBase.getInstance().getDataBase();

    private int width;
    private int height;
    private long msToReloadConsole;
    private volatile Location[][] locations;

    {
        loadIslandConfig();
        locations = new Location[height][width];
        initIsland();
    }

    public void initIsland() {
        for (int y = 0; y < locations.length; y++) {
            for (int x = 0; x < locations[y].length; x++) {
                locations[y][x] = createLocation(x, y);
            }
        }
    }

    private Location createLocation(int x, int y) {
        Location location = new Location(x, y);
        DataBase.setObjects()
                .forEach(clazz -> {
                    Record record = dataBase.get(clazz);
                    int maxCountOnCell = location.countOfAnimals(record.getMaxCountOnCell());
                    if (maxCountOnCell != 0) {
                        location.fillLocation(clazz, record, maxCountOnCell);
                    }

                });
        return location;
    }

    private void loadIslandConfig() {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("island/island.yaml")) {
            Yaml yaml = new Yaml();
            Map<String, Integer> map = yaml.load(inputStream);
            this.width = map.get("width");
            this.height = map.get("height");
            this.msToReloadConsole = map.get("msToReloadConsole");
        } catch (IOException exception) {
            throw new RuntimeException("file not found", exception);
        }
    }

    public Map<Class<? extends Organism>, Set<Organism>> getGroupingOrganismMap() {
        return Arrays.stream(locations)
                .flatMap(Arrays::stream)
                .map(Location::getOrganisms)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue,
                                Collectors.flatMapping(Collection::stream, Collectors.toSet()))));
    }
}
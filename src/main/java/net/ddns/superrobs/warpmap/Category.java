package net.ddns.superrobs.warpmap;

import org.bukkit.Location;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

class Category implements Serializable {

    public final String name;
    public final HashMap<String, Location> warpPoints = new HashMap<String, Location>();
    public CategoryAvailability availability;

    Category(@Nonnull String name, @Nonnull CategoryAvailability type){
        this.name = name;
        this.availability = type;
    }

    String getName() {
        return name;
    }

    CategoryAvailability getAvailability() {
        return availability;
    }

    void setAvailability(CategoryAvailability availability) {
        this.availability = availability;
    }

    void addWarpPoint(String name, Location location) {
        warpPoints.put(name, location);
    }

    void removeWarpPoint(String name) {
        warpPoints.remove(name);
    }

    boolean warpPointExists(String name) {
        return warpPoints.containsKey(name);
    }

    Set<String> getWarpPointNames(){
        return warpPoints.keySet();
    }

    Optional<Location> getWarpLocation(String name){
        return Optional.of(warpPoints.get(name));
    }
}

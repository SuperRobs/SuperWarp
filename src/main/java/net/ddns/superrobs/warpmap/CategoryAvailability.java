package net.ddns.superrobs.warpmap;

public enum CategoryAvailability {
    PUBLIC,
    AUTOMATICALLY_APPLIED,
    PERMISSION,
    PLAYER;

    public String getName() {
        return this.name();
    }
}
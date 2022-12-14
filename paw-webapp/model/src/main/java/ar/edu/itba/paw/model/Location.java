package ar.edu.itba.paw.model;

public enum Location {
    WEST(1, "Zona Oeste", "GBA West"),
    NORTH(2, "Zona Norte", "GBA North"),
    SOUTH(3, "Zona Sur", "GBA South"),
    CABA(4, "CABA", "CABA");

    private final int id;
    private final String nameEs;
    private final String name;

    Location(int id, String nameEs, String name) {
        this.id = id;
        this.nameEs = nameEs;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getNameEs() {
        return nameEs;
    }

    public String getName() {
        return name;
    }

    public static int[] getIds(){
        Location[] locations=  Location.class.getEnumConstants();
        int[] ids = new int[locations.length];
        for (int i = 0; i < locations.length; i++) {
            ids[i] = locations[i].getId();
        }
        return ids;
    }

    public static Location getLocationById(int id){
        switch (id){
            case 1: return Location.WEST;
            case 2: return  Location.NORTH;
            case 3: return Location.SOUTH;
            default: return Location.CABA;
        }
    }
}

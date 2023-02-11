package ar.edu.itba.paw.model;

public enum Availability {
    PART_TIME(1, "Media Jornada", "Part Time"),
    FULL_TIME(2, "Jornada Completa", "Full Time"),
    OVERNIGHT(3, "Con Cama", "Overnight")
    ;

    private final int id;
    private final String nameEs;
    private final String name;
    Availability(int id, String nameEs, String name) {
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
        Availability[] availabilities=  Availability.class.getEnumConstants();
        int[] ids = new int[availabilities.length];
        for (int i = 0; i < availabilities.length; i++) {
            ids[i] = availabilities[i].getId();
        }
        return ids;
    }

    public static Availability getAvailabilityById(int id){
        switch (id){
            case 1: return Availability.PART_TIME;
            case 2: return  Availability.FULL_TIME;
            default: return Availability.OVERNIGHT;
        }
    }

    public static Availability[] getAvailabilities() {
        return Availability.class.getEnumConstants();
    }
}

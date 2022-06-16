package ar.edu.itba.paw.model;

public enum Availability {
    PART_TIME(1, "Medio día", "Part Time"),
    FULL_TIME(2, "Día Completo", "Full Time"),
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
}

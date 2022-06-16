package ar.edu.itba.paw.model;

public enum Abilities {
    COOK(1, "Cocinar", "Cooking"),
    IRON(2, "Planchar", "Iron"),
    ELDER_CARE(3, "Cuidado de mayores", "Elder Care"),
    CHILD_CARE(4, "Cuidado de menores", "Child Care"),
    SPECIAL_NEEDS(5, "Cuidado Especial", "Special Care"),
    PET_CARE(6, "Cuidado de Mascotas", "Pet Care");
    private final int id;
    private final String nameEs;
    private final String name;
    Abilities(int id, String nameEs, String name) {
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

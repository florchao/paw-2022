package ar.edu.itba.paw.model;

public enum Abilities {
    COOK(1, "Cocinar", "Cooking"),
    IRON(2, "Planchar", "Iron"),
    CHILD_CARE(3, "Cuidado de Menores", "Child Care"),
    ELDER_CARE(4, "Cuidado de Mayores", "Elder Care"),
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

    public static int[] getIds(){
        Abilities[] abilities=  Abilities.class.getEnumConstants();
        int[] ids = new int[abilities.length];
        for (int i = 0; i < abilities.length; i++) {
            ids[i] = abilities[i].getId();
        }
        return ids;
    }

    public static Abilities getAbilityById(int id){
        switch (id){
            case 1: return Abilities.COOK;
            case 2: return  Abilities.IRON;
            case 3: return Abilities.CHILD_CARE;
            case 4: return Abilities.ELDER_CARE;
            case 5: return Abilities.SPECIAL_NEEDS;
            default: return Abilities.PET_CARE;
        }
    }
}

package ar.edu.itba.paw.webapp.dto;

import javax.persistence.Id;
import java.util.List;

public class IdsDto {
    int[] availabilities;
    int[] abilities;

    int[] locations;

    public static IdsDto fromForm(int[] availabilities, int[] abilities, int[] locations){
        IdsDto dto = new IdsDto();
        dto.abilities = abilities;
        dto.availabilities = availabilities;
        dto.locations = locations;
        return dto;
    }

    public int[] getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(int[] availabilities) {
        this.availabilities = availabilities;
    }

    public int[] getAbilities() {
        return abilities;
    }

    public void setAbilities(int[] abilities) {
        this.abilities = abilities;
    }

    public int[] getLocations() {
        return locations;
    }

    public void setLocations(int[] locations) {
        this.locations = locations;
    }
}

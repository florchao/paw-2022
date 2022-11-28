package ar.edu.itba.paw.webapp.dto;

import javax.persistence.Id;
import java.util.List;

public class IdsDto {
    int[] availabilities;
    int[] abilities;

    public static IdsDto fromForm(int[] availabilities, int[] abilities){
        IdsDto dto = new IdsDto();
        dto.abilities = abilities;
        dto.availabilities = availabilities;
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
}

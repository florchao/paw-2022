package ar.edu.itba.paw.webapp.dto.IdsDto;

import ar.edu.itba.paw.model.Location;
import org.springframework.context.i18n.LocaleContextHolder;

public class LocationDto {

    private LocationDto[] locations;

    private String name;

    private Integer id;

    public static LocationDto fromForm() {
        LocationDto dto = new LocationDto();
        String language = LocaleContextHolder.getLocale().getLanguage();

        Location[] locationArr = Location.getLocations();
        LocationDto[] aux = new LocationDto[locationArr.length];
        for (int i = 0; i < locationArr.length; i++) {
            aux[i] = LocationDto
                    .fromLocation(
                            language.startsWith("es")?
                                    locationArr[i].getNameEs()
                                    : locationArr[i].getName(),
                            locationArr[i].getId());
        }

        dto.locations = aux;
        return dto;
    }

    public static LocationDto fromLocation(String name, Integer id) {
        LocationDto dto = new LocationDto();
        dto.name = name;
        dto.id = id;
        return dto;
    }


    public LocationDto[] getLocations() {
        return locations;
    }

    public void setLocations(LocationDto[] locations) {
        this.locations = locations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

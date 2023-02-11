package ar.edu.itba.paw.webapp.dto.IdsDto;

import ar.edu.itba.paw.model.Availability;
import org.springframework.context.i18n.LocaleContextHolder;

public class AvailabilitiesDto {
    private AvailabilitiesDto[] availability;
    private String name;
    private Integer id;

    public static AvailabilitiesDto fromForm(){
        AvailabilitiesDto dto = new AvailabilitiesDto();
        String language = LocaleContextHolder.getLocale().getLanguage();

        Availability[] availabilities = Availability.getAvailabilities();
        AvailabilitiesDto[] aux = new AvailabilitiesDto[availabilities.length];
        for (int i = 0; i < availabilities.length; i++) {
            aux[i] = AvailabilitiesDto
                    .fromAvailabilities(
                            language.startsWith("es")?
                                    availabilities[i].getNameEs()
                                    : availabilities[i].getName(),
                            availabilities[i].getId());
        }

        dto.availability = aux;
        return dto;
    }

    public static AvailabilitiesDto fromAvailabilities(String name, Integer id) {
        AvailabilitiesDto dto = new AvailabilitiesDto();
        dto.name = name;
        dto.id = id;
        return dto;
    }

    public AvailabilitiesDto[] getAvailability() {
        return availability;
    }

    public void setAvailability(AvailabilitiesDto[] availability) {
        this.availability = availability;
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

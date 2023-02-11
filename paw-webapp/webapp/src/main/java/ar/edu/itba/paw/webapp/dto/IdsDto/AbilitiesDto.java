package ar.edu.itba.paw.webapp.dto.IdsDto;

import ar.edu.itba.paw.model.Abilities;
import org.springframework.context.i18n.LocaleContextHolder;

public class AbilitiesDto {

    private AbilitiesDto[] abilities;
    private String name;
    private Integer id;

    public static AbilitiesDto fromForm() {
        AbilitiesDto dto = new AbilitiesDto();
        String language = LocaleContextHolder.getLocale().getLanguage();

        Abilities[] abilitiesArr = Abilities.getAbilities();
        AbilitiesDto[] aux = new AbilitiesDto[abilitiesArr.length];
        for (int i = 0; i < abilitiesArr.length; i++) {
            aux[i] = AbilitiesDto.
                    fromAbilities(
                            language.startsWith("es")?
                                    abilitiesArr[i].getNameEs()
                                    : abilitiesArr[i].getName(),
                            abilitiesArr[i].getId()
                    );
        }

        dto.abilities = aux;
        return dto;
    }

    public static AbilitiesDto fromAbilities(String name, Integer id) {
        AbilitiesDto dto = new AbilitiesDto();
        dto.name = name;
        dto.id = id;
        return dto;
    }

    public AbilitiesDto[] getAbilities() {
        return abilities;
    }

    public void setAbilities(AbilitiesDto[] abilities) {
        this.abilities = abilities;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package ar.edu.itba.paw.webapp.dto.RatingDto;

public class RatingDto {

    private Float rating;

    private Long count;

    private Boolean hasRated;

    public static RatingDto fromRating(Float rating, Long count, Boolean hasRated) {
        RatingDto dto = new RatingDto();
        dto.rating = rating;
        dto.count = count;
        dto.hasRated = hasRated;
        return dto;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Boolean getHasRated() {
        return hasRated;
    }

    public void setHasRated(Boolean hasRated) {
        this.hasRated = hasRated;
    }
}

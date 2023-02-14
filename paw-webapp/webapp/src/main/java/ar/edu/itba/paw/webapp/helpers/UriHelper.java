package ar.edu.itba.paw.webapp.helpers;

import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;


@Component
public class UriHelper {

    public Response.ResponseBuilder addPaginationLinks(
            Response.ResponseBuilder responseBuilder,
            UriInfo uriInfo,
            long page,
            long maxPage
    ) {
        if (page > 0) {
            responseBuilder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", page - 1).build(), "prev");
        }
        if (maxPage - (page + 1) > 0) {
            responseBuilder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", page + 1).build(), "next");
        }
        return responseBuilder;
    }

    public void addPaginationLinksForReviews(
            Response.ResponseBuilder responseBuilder,
            UriBuilder uriBuilder,
            long page,
            long maxPage,
            Long except
    ) {
        if (except != null)
            uriBuilder.queryParam("except", except);
        if (page > 0) {
            responseBuilder.link(uriBuilder.replaceQueryParam("page", page - 1).build(), "prev");
        }
        if (maxPage - (page + 1) > 0) {
            responseBuilder.link(uriBuilder.replaceQueryParam("page", page + 1).build(), "next");
        }
    }

    public Response.ResponseBuilder addPaginationLinksForExplore(
            Response.ResponseBuilder responseBuilder,
            UriBuilder uriBuilder,
            long page,
            long maxPage
    ) {
        if (page > 0) {
            responseBuilder.link(uriBuilder.replaceQueryParam("page", page - 1).build(), "prev");
        }
        if (maxPage - (page + 1) > 0) {
            responseBuilder.link(uriBuilder.replaceQueryParam("page", page + 1).build(), "next");
        }
        return responseBuilder;
    }

    public static void fillQueryParams(
            UriBuilder uriBuilder,
            String name,
            Long experienceYears,
            String location,
            String availability,
            String abilities,
            String orderCriteria
    ) {
        if (name != null && !name.equals("")) {
            uriBuilder.queryParam("name", name);
        }
        if (experienceYears != null) {
            uriBuilder.queryParam("experience", experienceYears);
        }
        if (location != null) {
            uriBuilder.queryParam("location", location);
        }
        if (availability != null && !availability.equals("")) {
            uriBuilder.queryParam("availability", availability);
        }
        if (abilities != null && !abilities.equals("")) {
            uriBuilder.queryParam("abilities", abilities);
        }
        if (orderCriteria != null && !orderCriteria.equals("") && !orderCriteria.equals("null")) {
            uriBuilder.queryParam("order", orderCriteria);
        }
    }
}

package ar.edu.itba.paw.webapp.helpers;

import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
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
        return responseBuilder.header("Access-Control-Expose-Headers", "Link");
    }
}

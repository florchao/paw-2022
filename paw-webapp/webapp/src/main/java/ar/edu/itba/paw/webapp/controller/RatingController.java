package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.RaitingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Path("/api/rating")
@Component
public class RatingController {

    @Autowired
    private EmployeeService employeeService;

    @GET
    @Path("/{userId}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getEmployeeRating(@PathParam("userId") long userId) {
        try {
            Rating rating = new Rating(employeeService.getRating(userId), employeeService.getRatingVoteCount(userId));
            GenericEntity<Rating> genericEntity = new GenericEntity<Rating>(rating) {};
            return Response.ok(genericEntity).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }

    //
//    @RequestMapping(value = "/addRating/{idRating}", method = {RequestMethod.POST})
//    public ModelAndView addRating(@RequestParam(value = "rating", required = false) Long rating,
//                                  @PathVariable final long idRating) {
//        if (rating == null)
//            rating = 0L;
//        Authentication authority = SecurityContextHolder.getContext().getAuthentication();
//        HogarUser user = (HogarUser) authority.getPrincipal();
//        float finalRating = raitingService.updateRating(idRating, rating, user.getUserID());
//        long voteCount = employeeService.getRatingVoteCount(idRating);
//        final ModelAndView mav = new ModelAndView("redirect:/verPerfil/"+idRating);
//        mav.addObject("rating",finalRating);
//        mav.addObject("voteCount", voteCount);
//        return mav;
//    }

    static class Rating {
        float rating;
        long count;

        public Rating(float rating, long count) {
            this.rating = rating;
            this.count = count;
        }

        public Rating() {
        }

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "Rating{" +
                    "rating=" + rating +
                    ", count=" + count +
                    '}';
        }
    }
}

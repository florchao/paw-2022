package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewProfileController {

    private final int PAGE_SIZE = 4;
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private RaitingService raitingService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private ImagesService imagesService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewProfileController.class);

    @RequestMapping(value = "/verPerfil", method = {RequestMethod.GET})
    public ModelAndView viewProfile() {
        final ModelAndView mav = new ModelAndView("viewProfile");
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userService.findByUsername(principal.getUsername());
        if(user.isPresent()){
            mav.addObject("user", user.get());
            Optional<Employee> employee = employeeService.getEmployeeById(user.get().getId());
            if(employee.isPresent()){
                employee.get().firstWordsToUpper();
                employee.get().locationFirstWordsToUpper();
                mav.addObject("employee", employee.get());
            }
            mav.addObject("userId", user.get().getId());
            List<Review> myReviews = reviewService.getMyProfileReviews(user.get().getId());
            for(Review rev : myReviews){
                rev.getEmployerId().firstWordsToUpper(rev.getEmployerId());
            }
             mav.addObject("ReviewList", myReviews);
        }
        return mav;
    }

    @RequestMapping(value = "/verPerfil/{userId}", method = RequestMethod.GET)
    public ModelAndView userProfile(@PathVariable("userId") final long userId, @RequestParam(value = "status", required = false) String status, @ModelAttribute("reviewForm") final ReviewForm reviewForm,
                                    @RequestParam(value = "page", required = false) Long page) {
        final ModelAndView mav = new ModelAndView("viewProfile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Employee> employee = employeeService.getEmployeeById(userId);
        if (employee.isPresent()) {
            employee.get().firstWordsToUpper();
            employee.get().locationFirstWordsToUpper();
            mav.addObject("employee", employee.get());
        }
        mav.addObject("status", status);
        if (page == null)
            page = 0L;
        List<Review> reviews;
        int maxPage;
        boolean hasAlreadyRated = false;
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER"))) {
            HogarUser user = (HogarUser) auth.getPrincipal();
            Boolean exists = contactService.existsContact(userId, user.getUserID());
            mav.addObject("contacted", exists);
            Optional<Review> myReview = reviewService.getMyReview(userId, user.getUserID());
            if (myReview.isPresent()) {
                myReview.get().getEmployerId().firstWordsToUpper(myReview.get().getEmployerId());
                mav.addObject("myReview", myReview.get());
            }
            reviews = reviewService.getAllReviews(userId, user.getUserID(), page, PAGE_SIZE);
            maxPage = reviewService.getPageNumber(userId, user.getUserID(), PAGE_SIZE);
            hasAlreadyRated = raitingService.hasAlreadyRated(userId, user.getUserID());
        } else {
            maxPage = reviewService.getPageNumber(userId, null, PAGE_SIZE);
            reviews = reviewService.getAllReviews(userId, null, page, PAGE_SIZE);
        }
        for (Review rev : reviews) {
            rev.firstWordsToUpper(rev);
            rev.getEmployerId().firstWordsToUpper(rev.getEmployerId());
        }
        mav.addObject("alreadyRated", hasAlreadyRated);
        mav.addObject("rating", employeeService.getRating(userId));
        mav.addObject("voteCount", employeeService.getRatingVoteCount(userId));
        mav.addObject("ReviewList", reviews);
        mav.addObject("page", page);
        mav.addObject("maxPage", maxPage);
        return mav;
    }

    @RequestMapping(value = "addReview/{id}", method = {RequestMethod.POST})
    public ModelAndView addReview(@ModelAttribute("reviewForm") final ReviewForm reviewForm, @RequestParam(value = "status", required = false) String status, final BindingResult errors, @PathVariable final long id){
        if(errors.hasErrors())
            return userProfile(id,status, reviewForm, null);
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        reviewService.create(id, principal.getUserID(), reviewForm.getContent());
        return new ModelAndView("redirect:/verPerfil/" + id);
    }


    @RequestMapping(value = "/user/profile-image/{userId}", method = {RequestMethod.GET})
    public void profileImage(HttpServletResponse response, @PathVariable final long userId) throws IOException {
        Optional<byte[]> image = imagesService.getProfileImage(userId);
        if(!image.isPresent()){
            LOGGER.debug("User {} does not have an image", userId);
            return;
        }
        InputStream is = new ByteArrayInputStream(image.get());
        IOUtils.copy(is,response.getOutputStream());
    }

    @RequestMapping(value = "/addRating/{idRating}", method = {RequestMethod.POST})
    public ModelAndView addRating(@RequestParam(value = "rating", required = false) Long rating,
                           @PathVariable final long idRating) {
        if (rating == null)
            rating = 0L;
        Authentication authority = SecurityContextHolder.getContext().getAuthentication();
        HogarUser user = (HogarUser) authority.getPrincipal();
        float finalRating = raitingService.updateRating(idRating, rating, user.getUserID());
        long voteCount = employeeService.getRatingVoteCount(idRating);
        final ModelAndView mav = new ModelAndView("redirect:/verPerfil/"+idRating);
        mav.addObject("rating",finalRating);
        mav.addObject("voteCount", voteCount);
        return mav;
    }
}

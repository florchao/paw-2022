package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.AccessIsDeniedException;
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
            employee.ifPresent(Employee::firstWordsToUpper);
            employee.ifPresent(value -> mav.addObject("employee", value));
            mav.addObject("userId", user.get().getId());
        }
        return mav;
    }

    @RequestMapping(value = "/verPerfil/{userId}", method = RequestMethod.GET)
    public ModelAndView userProfile(@PathVariable("userId") final long userId, @RequestParam(value = "status", required = false) String status, @ModelAttribute("reviewForm") final ReviewForm reviewForm,
                                    @RequestParam(value = "page", required = false) Long page) {
        final ModelAndView mav = new ModelAndView("viewProfile");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE")))
            throw new AccessIsDeniedException("Access is denied");

        employeeService.isEmployee(userId);

        Optional<Employee> employee = employeeService.getEmployeeById(userId);

        if (employee.isPresent()) {
            employee.get().firstWordsToUpper();
            mav.addObject("employee", employee.get());
        }
        mav.addObject("status", status);

        if (page == null)
            page = 0L;

        Optional<List<Review>> reviews;
        int maxPage;
        boolean hasAlreadyRated = false;
        if(auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER"))) {
            HogarUser user = (HogarUser) auth.getPrincipal();
            Optional<Boolean> exists = contactService.existsContact(userId, user.getUserID());
            exists.ifPresent(aBoolean -> mav.addObject("contacted", aBoolean));
            Optional<Review> myReview = reviewService.getMyReview(userId, user.getUserID());
            myReview.ifPresent(review -> mav.addObject("myReview", review));
            reviews = reviewService.getAllReviews(userId, user.getUserID(), page, PAGE_SIZE);
            maxPage = reviewService.getPageNumber(userId, user.getUserID(), PAGE_SIZE);
            hasAlreadyRated = employeeService.hasAlreadyRated(userId, user.getUserID());
        } else {
            maxPage = reviewService.getPageNumber(userId, null, PAGE_SIZE);
            reviews = reviewService.getAllReviews(userId, null, page, PAGE_SIZE);
        }
        mav.addObject("alreadyRated",hasAlreadyRated);
        mav.addObject("rating", employeeService.getRating(userId));
        mav.addObject("voteCount",employeeService.getRatingVoteCount(userId));
        List<Review> reviewsWithUpperCase = null;
        if (reviews.isPresent()) {
            //TODO SE ROMPE ESTO
            //reviewsWithUpperCase = reviews.get().stream().map(Review::firstWordsToUpper).collect(Collectors.toList()).;
            //Lo arregle con esto, pero esta mal
            reviewsWithUpperCase = reviews.get();

        }
        mav.addObject("ReviewList", reviewsWithUpperCase);
        mav.addObject("page", page);
        mav.addObject("maxPage", maxPage);
        return mav;
    }

    @RequestMapping(value = "addReview/{id}", method = {RequestMethod.POST})
    ModelAndView addReview(@ModelAttribute("reviewForm") final ReviewForm reviewForm, @RequestParam(value = "status", required = false) String status, final BindingResult errors, @PathVariable final long id){
        if(errors.hasErrors())
            return userProfile(id,status, reviewForm, null);
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        reviewService.create(id, principal.getUserID(), reviewForm.getContent());
        return new ModelAndView("redirect:/verPerfil/" + id);
    }


    @RequestMapping(value = "/user/profile-image/{userId}", method = {RequestMethod.GET})
    public void profileImage(HttpServletResponse response, @PathVariable final long userId) throws IOException {
        System.out.println("FOTO ID" + userId);
        Optional<byte[]> image = imagesService.getProfileImage(userId);
        if(!image.isPresent()){
            LOGGER.debug("User {} does not have an image", userId);
            return;
        }
        InputStream is = new ByteArrayInputStream(image.get());
        IOUtils.copy(is,response.getOutputStream());
    }

    @RequestMapping(value = "/addRating/{idRating}", method = {RequestMethod.POST})
    ModelAndView addRating(@RequestParam(value = "rating", required = false) Long rating,
                           @PathVariable final long idRating) {
        if (rating == null)
            rating = 0L;
        Authentication authority = SecurityContextHolder.getContext().getAuthentication();
        HogarUser user = (HogarUser) authority.getPrincipal();
        float finalRating = employeeService.updateRating(idRating, rating, user.getUserID());
        long voteCount = employeeService.getRatingVoteCount(idRating);
        final ModelAndView mav = new ModelAndView("redirect:/verPerfil/"+idRating);
        mav.addObject("rating",finalRating);
        mav.addObject("voteCount", voteCount);
        return mav;
    }
}

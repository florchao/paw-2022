package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.ReviewService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ReviewController {

    private final int PAGE_SIZE = 4;

    @Autowired
    ReviewService reviewService;

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/opiniones/{id}", method = {RequestMethod.GET})
    ModelAndView reviews(@ModelAttribute("reviewForm") final ReviewForm reviewForm, @PathVariable final long id,
                         @RequestParam(value = "page", required = false) Long page) {
        ModelAndView mav = new ModelAndView("viewReviews");
        if (page == null)
            page = 0L;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<List<Review>> reviews;
        int maxPage;
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER"))) {
            HogarUser principal = (HogarUser) auth.getPrincipal();
            Optional<Review> myReview = reviewService.getMyReview(id, principal.getUserID());
            myReview.ifPresent(review -> mav.addObject("myReview", review));
            reviews = reviewService.getAllReviews(id, principal.getUserID(), page, PAGE_SIZE);
            maxPage = reviewService.getPageNumber(id, principal.getUserID(), PAGE_SIZE);
        } else {
            maxPage = reviewService.getPageNumber(id, null, PAGE_SIZE);
            reviews = reviewService.getAllReviews(id, null, page, PAGE_SIZE);
        }
        List<Review> reviewsWithUpperCase = null;
        if (reviews.isPresent()) {
            reviewsWithUpperCase = reviews.get().stream().map(Review::firstWordsToUpper).collect(Collectors.toList());
        }
        mav.addObject("ReviewList", reviewsWithUpperCase);
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            employee.get().firstWordsToUpper();
            mav.addObject("employee", employee.get());
        }
        mav.addObject("page", page);
        mav.addObject("maxPage", maxPage);

        return mav;
    }

    @RequestMapping(value = "addReview/{id}", method = {RequestMethod.POST})
    ModelAndView addReview(@ModelAttribute("reviewForm") final ReviewForm reviewForm, final BindingResult errors, @PathVariable final long id){
        if(errors.hasErrors())
            return reviews(reviewForm, id, null);
        //Optional<User> user = userService.getUserById(id);
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        reviewService.create(id, principal.getUserID(), reviewForm.getContent());
        return new ModelAndView("redirect:/opiniones/" + id);
    }
}

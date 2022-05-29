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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/opiniones/{id}", method = {RequestMethod.GET})
    ModelAndView reviews(@ModelAttribute("reviewForm") final ReviewForm reviewForm, @PathVariable final long id){
        ModelAndView mav = new ModelAndView("viewReviews");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<List<Review>> reviews;
        if(auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER"))) {
            HogarUser principal = (HogarUser) auth.getPrincipal();
            Optional<Review> myReview = reviewService.getMyReview(id, principal.getUserID());
            myReview.ifPresent(review -> mav.addObject("myReview", review));
            reviews = reviewService.getAllReviews(id, principal.getUserID());
        } else {
            reviews = reviewService.getAllReviews(id, null);
        }
        reviews.ifPresent(reviewList -> mav.addObject("ReviewList", reviewList));
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if(employee.isPresent()){
            employee.get().firstWordsToUpper();
            mav.addObject("employee", employee.get());
        }
        return mav;
    }

    @RequestMapping(value = "addReview/{id}", method = {RequestMethod.POST})
    ModelAndView addReview(@ModelAttribute("reviewForm") final ReviewForm reviewForm, final BindingResult errors, @PathVariable final long id){
        if(errors.hasErrors())
            return reviews(reviewForm, id);
        //Optional<User> user = userService.getUserById(id);
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        reviewService.create(id, principal.getUserID(), reviewForm.getContent());
        return new ModelAndView("redirect:/opiniones/" + id);
    }
}

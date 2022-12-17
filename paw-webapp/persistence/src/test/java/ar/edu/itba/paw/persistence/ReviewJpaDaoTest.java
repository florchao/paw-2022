//package ar.edu.itba.paw.persistence;
//
//import ar.edu.itba.paw.model.Employee;
//import ar.edu.itba.paw.model.Employer;
//import ar.edu.itba.paw.model.Review;
//import ar.edu.itba.paw.model.User;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.sql.DataSource;
//import javax.swing.text.html.Option;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = ar.edu.itba.paw.persistence.TestConfig.class)
//@Sql("classpath:schema.sql")
//@Transactional
//@Rollback
//public class ReviewJpaDaoTest {
//
//    @Autowired
//    private EmployerJpaDao employerJpaDao;
//
//    @Autowired
//    private EmployeeJpaDao employeeJpaDao;
//
//    @Autowired
//    private UserJpaDao userJpaDao;
//
//    @Autowired
//    private ReviewJpaDao reviewJpaDao;
//
//
//    @Autowired
//    public DataSource dataSource;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    private static final String PASSWORD = "Password";
//    private static final String USERNAME = "Username";
//    private static final String USERNAME2 = "Username2";
//    private static final String TEXT = "TEXT";
//    private static final String NAME = "Name";
//    private static final String LOCATION = "Location";
//    private static final String AVAILABILITY = "Availability";
//    private static final long EXPERIENCE_YEARS = 10;
//    private static final long HOURLY_FEE = 100;
//
//    private static final String ABILITIES = "Abilities";
//
//    private static final Date DATE = new Date();
//
////    @Test
////    public void testCreate(){
////        byte [] image = {};
////        final User user = userJpaDao.create(USERNAME, PASSWORD, 2);
////        final Employer employer = employerJpaDao.create(NAME, user, image);
////        final User user2 = userJpaDao.create(USERNAME2, PASSWORD, 1);
////        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);
////
////
////        Review review = reviewJpaDao.create(employee, employer, TEXT, DATE, true);
////
////        Assert.assertEquals(employer.getId(),review.getEmployerId().getId());
////        Assert.assertEquals(employee.getId(),review.getEmployeeId().getId());
////        Assert.assertEquals(TEXT, review.getReview());
////        Assert.assertEquals(DATE, review.getCreated());
////    }
//
////    @Test
////    public void testGetMyReviews(){
////        byte [] image = {};
////        final User user = userJpaDao.create(USERNAME, PASSWORD, 2);
////        final Employer employer = employerJpaDao.create(NAME, user, image);
////        final User user2 = userJpaDao.create(USERNAME2, PASSWORD, 1);
////        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);
////        reviewJpaDao.create(employee, employer, TEXT, DATE, true);
////
////        Optional<Review> reviewList = reviewJpaDao.getMyReview(employee, employer);
////
////        Assert.assertNotNull(reviewList.get());
////
////    }
////
////    @Test
////    public void testGetAllReviewsEmployer(){
////        byte [] image = {};
////        final User user = userJpaDao.create(USERNAME, PASSWORD, 2);
////        final Employer employer = employerJpaDao.create(NAME, user, image);
////        final User user2 = userJpaDao.create(USERNAME2, PASSWORD, 1);
////        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);
////        reviewJpaDao.create(employee, employer, TEXT, DATE, false);
////
////        List<Review> reviewList = reviewJpaDao.getAllReviewsEmployer(employee, employer, 0L,3 );
////
////        Assert.assertNotNull(reviewList);
////
////    }
////
////    @Test
////    public void testGetMyProfileReviews(){
////        byte [] image = {};
////        final User user = userJpaDao.create(USERNAME, PASSWORD, 2);
////        final Employer employer = employerJpaDao.create(NAME, user, image);
////        final User user2 = userJpaDao.create(USERNAME2, PASSWORD, 1);
////        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);
////        reviewJpaDao.create(employee, employer, TEXT, DATE, true);
////
////        List<Review> reviewList = reviewJpaDao.getMyProfileReviews(employee, 0, 1);
////
////        Assert.assertNotNull(reviewList);
////        Assert.assertEquals(1, reviewList.size());
////
////    }
////
////    @Test
////    public void testGetMyProfileEmployerReviews(){
////        byte [] image = {};
////        final User user = userJpaDao.create(USERNAME, PASSWORD, 2);
////        final Employer employer = employerJpaDao.create(NAME, user, image);
////        final User user2 = userJpaDao.create(USERNAME2, PASSWORD, 1);
////        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);
////        reviewJpaDao.create(employee, employer, TEXT, DATE, false);
////
////        List<Review> reviewList = reviewJpaDao.getMyProfileReviewsEmployer(employer, 0, 1);
////
////        Assert.assertNotNull(reviewList);
////        Assert.assertEquals(1, reviewList.size());
////
////    }
////
////    @Test
////    public void testGetMyReviewEmployer(){
////        byte [] image = {};
////        final User user = userJpaDao.create(USERNAME, PASSWORD, 2);
////        final Employer employer = employerJpaDao.create(NAME, user, image);
////        final User user2 = userJpaDao.create(USERNAME2, PASSWORD, 1);
////        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);
////        Review review = reviewJpaDao.create(employee, employer, TEXT, DATE, true);
////
////        Optional<Review> reviewList = reviewJpaDao.getMyReviewEmployer(employee, employer);
////
////        Assert.assertNotNull(reviewList);
////        Assert.assertEquals(DATE, review.getCreated());
////        Assert.assertEquals(TEXT, review.getReview());
////    }
//
//
//}

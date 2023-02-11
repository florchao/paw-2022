import {mockSuccesfulResponse, review, review2} from "../mocks";
import {ReviewService} from "../../service/ReviewService";

describe("Fetch reviews", () => {
    test("It should return the second page of employee reviews except of the user logged", () => {
        mockSuccesfulResponse(200,[review2]);
        return ReviewService.getEmployeeReviews("http://localhost:8080/employees/2/reviews", 1, 1).then(response => {
            // @ts-ignore
            expect(response.status).toBe(200);
            // @ts-ignore
            expect(response.json().length).toBe(1);
        })
    });
});

describe("Fetch reviews", () => {
    test("It should return the first page of employee reviews", () => {
        mockSuccesfulResponse(200,[review, review2]);
        return ReviewService.getEmployeeReviews("http://localhost:8080/employees/2/reviews", 0).then(response => {
            // @ts-ignore
            expect(response.status).toBe(200);
            // @ts-ignore
            expect(response.json().length).toBe(2);
        })
    });
});

describe("Fetch reviews", () => {
    test("There should be no content on page 10", () => {
        mockSuccesfulResponse(204,{});
        return ReviewService.getEmployeeReviews("http://localhost:8080/employees/2/reviews", 9).then(response => {
            // @ts-ignore
            expect(response.status).toBe(204);
        })
    });
});
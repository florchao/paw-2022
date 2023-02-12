import {ratings, mockSuccesfulResponse, employee1} from "../mocks";
import {RatingService} from "../../service/RatingService";

describe("Fetch ratings for  given employee", () => {
    test("It should return the employee rating", () => {
        mockSuccesfulResponse(200,ratings);
        return RatingService.getEmployeeRating("http://localhost:8080/ratings/2",1).then(response => {
            expect(response).toStrictEqual(ratings)
        })
    });
});
import {LocalStorageMock} from "./LocalStorageMock";
export const employee1 = {
    "abilitiesArr": [
        "Cooking",
        "Pet Care"
    ],
    "availabilityArr": [
        "Part Time"
    ],
    "experienceYears": 3,
    "hourlyFee": 2,
    "id": 1,
    "image": "http://localhost:8080/images/1",
    "location": "GBA West",
    "name": "Sol Konfe",
    "rating": 3.5,
    "ratings": "http://localhost:8080/ratings/1",
    "reviews": "http://localhost:8080/employees/1/reviews",
    "self": "http://localhost:8080/employees/1",
    "users": "http://localhost:8080/users/1"
};

export const employee2 ={
    "abilitiesArr": [
        "Cooking"
    ],
    "availabilityArr": [
        "Full Time",
        "Overnight"
    ],
    "experienceYears": 5,
    "hourlyFee": 297,
    "id": 9,
    "image": "http://localhost:8080/images/9",
    "location": "GBA West",
    "name": "Tomas",
    "rating": 2.875,
    "ratings": "http://localhost:8080/ratings/9",
    "reviews": "http://localhost:8080/employees/9/reviews",
    "self": "http://localhost:8080/employees/9",
    "users": "http://localhost:8080/users/9"
};

export function mockSuccesfulResponse(
    code: number,
    returnBody: any,
    headers?: Headers
) {
    localStorage = new LocalStorageMock();
    return (global.fetch = jest.fn().mockImplementationOnce(() => {
        return new Promise((resolve, reject) => {
            resolve({
                ok: true,
                status: code,
                headers: headers,
                json: () => {
                    return returnBody;
                },
            });
        });
    }));
}
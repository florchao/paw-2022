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

export const employer1 ={
    "id":1,
    "image":"http://localhost:8080/images/1",
    "jobs":"http://localhost:8080/employers/1/jobs",
    "name":"Employer Employer",
    "reviews":"http://localhost:8080/employers/1/reviews",
    "users":"http://localhost:8080/users/1"
}

export const job1={
        "abilities": [
            "Cooking"
        ],
        "applicants": "http://localhost:8080/applicants",
        "availability": [
            "Part Time"
        ],
        "description": "que cocine de todo",
        "employerId": {
            "id": 1,
            "name": "Solci Konfe",
            "reviews": "http://localhost:8080/employers/1/reviews"
        },
        "experienceYears": 0,
        "jobId": 1,
        "location": "GBA South",
        "opened": true,
        "self": "http://localhost:8080/jobs/2",
        "status": 0,
        "title": "Cocinero"
}

export const job2= {
    "abilities": [
        "Cooking",
        "Iron",
        "Elder Care"
    ],
    "applicants": "http://localhost:8080/applicants",
    "availability": [
        "Part Time"
    ],
    "description": "dsakllc;aslcxa[psl';ls>x",
    "employerId": {
        "id": 8,
        "name": "Maria Maria",
        "reviews": "http://localhost:8080/employers/8/reviews"
    },
    "experienceYears": 3,
    "jobId": 7,
    "location": "GBA West",
    "opened": true,
    "self": "http://localhost:8080/jobs/7",
    "status": 1,
    "title": "Mascotas"
}

export const contact1 ={
    "created":"08/02/2023",
    "employer":{
        "email":"fchao@itba.edu.ar",
        "id":1,
        "image":"http://localhost:8080/images/1",
        "name":"Employer Employer"},
    "message":"holaaaaaa",
    "phoneNumber":"11111111"
}

export const contact2 ={
    "created":"08/02/2023",
    "employer":{
        "email":"fchao@itba.edu",
        "id":2,
        "image":"http://localhost:8080/images/2",
        "name":"Employer Employer"},
    "message":"holaaaaaa2",
    "phoneNumber":"11111111"
}

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
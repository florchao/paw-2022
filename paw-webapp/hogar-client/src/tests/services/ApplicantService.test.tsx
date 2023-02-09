import {employee1, job3, job4, mockSuccesfulResponse} from "../mocks";
import {ApplicantService} from "../../service/ApplicantService";

describe("Fetch applied jobs of employee", () => {
    test("It should return the first page of applied jobs", () => {
        const headers = new window.Headers();
        headers.set("Accept-Language", "english");
        mockSuccesfulResponse(200,[job3, job4], headers);
        return ApplicantService.getAppliedJobs(2,0).then(response => {
            // @ts-ignore
            expect(response.status).toBe(200);
            // @ts-ignore
            expect(response.json().length).toBe(2);
        })
    });
});

describe("delete an application", () => {
    test("It should delete employee 2 application", () => {
        mockSuccesfulResponse(204, {});
        return ApplicantService.deleteApplication(2,1).then(response => {
            // @ts-ignore
            expect(response.status).toBe(204);
        })
    });
});

describe("apply to a job", () => {
    test("It should create employee 2 application for job 1", () => {
        mockSuccesfulResponse(201, {});
        return ApplicantService.createApplicant("http://localhost:8080/applicants", 1).then(response => {
            // @ts-ignore
            expect(response.status).toBe(201);
        })
    });
});

describe("update application status", () => {
    test("It should update employee 2 application for job 1", () => {
        const headers = new window.Headers();
        headers.set("Accept-Language", "english");
        mockSuccesfulResponse(200, 2, headers);
        return ApplicantService.updateStatus("http://localhost:8080/applicants/2/1", 2).then(response => {
            // @ts-ignore
            expect(response.status).toBe(200);
        })
    });
});

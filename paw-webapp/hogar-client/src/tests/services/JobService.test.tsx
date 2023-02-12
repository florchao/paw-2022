import { job1, job2, mockSuccesfulResponse} from "../mocks";
import {JobService} from "../../service/JobService";

describe("Fetch jobs", () => {
    test("It should return the first page of explore", () => {
        const headers = new window.Headers();
        headers.set("Accept-Language", "english");
        mockSuccesfulResponse(200,[job1, job2], headers);
        return JobService.getJobs().then(response => {
            // @ts-ignore
            expect(response.status).toBe(200);
            // @ts-ignore
            expect(response.json().length).toBe(2);
        })
    });
});

describe("Fetch job", () => {
    test("It should return job1", () => {
        const headers = new window.Headers();
        headers.set("Accept-Language", "english");
        mockSuccesfulResponse(200, job1, headers);
        return JobService.getJob("http://localhost:8080/jobs/1").then(response => {
            // @ts-ignore
            expect(response.status).toBe(200);
            // @ts-ignore
            expect(response.json()).toStrictEqual(job1)
        })
    });
});

describe("Fetch job", () => {
    test("It should return job1", () => {
        const headers = new window.Headers();
        headers.set("Accept-Language", "english");
        mockSuccesfulResponse(200, job1, headers);
        return JobService.getJobById(1, "user:pass").then(response => {
            // @ts-ignore
            expect(response.status).toBe(200);
            // @ts-ignore
            expect(response.json()).toStrictEqual(job1)
        })
    });
});

// describe("update status", () => {
//     test("It should update status of  job1", () => {
//         mockSuccesfulResponse(200, job1);
//         return JobService.updateJobStatus("http://localhost:8080/jobs/1", false).then(response => {
//             // @ts-ignore
//             expect(response).toStrictEqual("false")
//         })
//     });
// });
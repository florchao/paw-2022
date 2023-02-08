import {contact1, contact2, job1, job2, mockSuccesfulResponse} from "../mocks";
import {JobService} from "../../service/JobService";
import {ContactService} from "../../service/ContactService";

describe("Fetch contacts for employee", () => {
    test("It should return the first page of contacts for employee with id=3", () => {
        const headers = new window.Headers();
        headers.set("Accept-Language", "english");
        mockSuccesfulResponse(200,[contact1, contact2], headers);
        return ContactService.contacts(3, 1).then(response => {
            // @ts-ignore
            expect(response.status).toBe(200);
            // @ts-ignore
            expect(response.json().length).toBe(2);
        })
    });
});

describe("Should get contact for employee id=1 made by employer id=2", () => {
    test("It should return contact from employer id=2 to employee id=1", () => {
        const headers = new window.Headers();
        headers.set("Accept-Language", "english");
        mockSuccesfulResponse(200,contact2, headers);
        return ContactService.getContact(1, 2).then(response => {
            // @ts-ignore
            expect(response).toStrictEqual(contact2)
        })
    });
});
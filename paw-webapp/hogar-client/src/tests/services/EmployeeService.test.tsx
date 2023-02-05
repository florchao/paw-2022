import {mockSuccesfulResponse} from "../mocks";
import {EmployeeService} from "../../service/EmployeeService";

describe("Fetch employees", () => {
    test("It should return the first page of explore", () => {
        const headers = new window.Headers();
        headers.set("Accept-Language", "english");
        mockSuccesfulResponse(200,{}, headers);
        return EmployeeService.getEmployees('').then(response => {
            // @ts-ignore
            expect(response.status).toBe(200);
        })
    });
});
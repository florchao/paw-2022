import {employee1, employee2, mockSuccesfulResponse} from "../mocks";
import {EmployeeService} from "../../service/EmployeeService";

describe("Fetch employees", () => {
    test("It should return the first page of explore", () => {
        const headers = new window.Headers();
        headers.set("Accept-Language", "english");
        mockSuccesfulResponse(200,[employee1, employee1], headers);
        return EmployeeService.getEmployees('').then(response => {
            // @ts-ignore
            expect(response.status).toBe(200);
            // @ts-ignore
            expect(response.json().length).toBe(2);
        })
    });
});

describe("Fetch employee", () => {
    test("It should return employee1", () => {
        const headers = new window.Headers();
        headers.set("Accept-Language", "english");
        mockSuccesfulResponse(200, employee1, headers);
        return EmployeeService.getEmployee("http://localhost:8080/employees/1", false).then(response => {
            // @ts-ignore
            expect(response).toStrictEqual(employee1)
        })
    });
});


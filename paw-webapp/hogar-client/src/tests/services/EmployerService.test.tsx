import {employer1, mockSuccesfulResponse} from "../mocks";
import {EmployerService} from "../../service/EmployerService";

describe("Fetch employer", () => {
    test("It should return employer1", () => {
        const headers = new window.Headers();
        headers.set("Accept-Language", "english");
        mockSuccesfulResponse(200,employer1, headers);
        return EmployerService.getEmployer(1).then(response => {
            // @ts-ignore
            expect(response).toStrictEqual(employer1)
        })
    });
});
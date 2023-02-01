import {LocalStorageMock} from "./LocalStorageMock";

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
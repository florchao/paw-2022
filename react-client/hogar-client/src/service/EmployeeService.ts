export class EmployeeService {

    public static async getFilteredEmployees() {
        return await fetch('http://localhost:8080/api/employees', {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json'
            }
        }).then((resp) => resp.json())
            .catch(
                (error) => {
                    throw error
                })
    }
}
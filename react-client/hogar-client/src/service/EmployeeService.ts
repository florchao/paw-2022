export class EmployeeService {

    public static async getFilteredEmployees() {
        return await fetch('http://localhost:8080/api/explore/employees', {
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

    public static async getEmployee(id: number) {
        return await fetch('http://localhost:8080/api/profile/' + id, {
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
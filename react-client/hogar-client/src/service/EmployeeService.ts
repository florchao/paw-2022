
export class EmployeeService {
    
    public static async getFilteredEmployees() {
        const response = await fetch('http://localhost:8080/api/employees', {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin':'*',
                'Content-Type': 'application/json'
            }
        }).then((resp) => resp.json()).then((data) => {
            console.log("ACA")
            console.log(data)
        })
        .catch(
            (error) => {
                throw error
            })
        return response
    }
}
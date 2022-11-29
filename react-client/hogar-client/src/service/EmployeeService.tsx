export class EmployeeService {
    public static async getEmployees() {
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

    public static async getFilteredEmployees(
        minimumYears: number,
        name?: string,
        location?: string,
        abilitites?: string,
        availability?: string
    ) {

        let url = 'http://localhost:8080/api/explore/employees?'

        if (minimumYears > 0)
            url = this.concatStringQueries(url, 'experience', String(minimumYears))
        url = this.concatStringQueries(url, 'name', name)
        url = this.concatStringQueries(url, 'location', location)
        url = this.concatStringQueries(url, 'abilities', abilitites)
        url = this.concatStringQueries(url, 'availability', availability)

        return await fetch(url, {
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

    private static concatStringQueries(url: string, queryParam: string, value?: string): string {
        if (value !== undefined) {
            url = url.concat(queryParam).concat('=').concat(value).concat('&')
        }
        return url
    }


    public static async getEmployee(id: number) {
        return await fetch('http://localhost:8080/api/profile/' + id, {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json"
            },
        }).then((resp) => resp.json())
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async registerEmployee(e: any, mail: string, password: string, confirmPassword: string, name: string, location: string, experienceYears: number, availabilities: string[], abilities: string[], image:any) {
        e.preventDefault();
        const employeeForm = {mail, password, confirmPassword, name, location, experienceYears, availabilities, abilities};
        return await fetch('http://localhost:8080/api/employee', {
            method: 'POST',
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(employeeForm)
        }).then((r) => r.text())
    }
}
export class EmployeeService {
    public static async getEmployees() {
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

    public static async getFilteredEmployees(
        minimumYears: number,
        name?: string,
        location?: string,
        abilitites?: string,
        availability?: string
    ) {

        let url = 'http://localhost:8080/api/employees?'

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
        return await fetch('http://localhost:8080/api/employee/' + id, {
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

    public static async registerEmployee(e: any, mail: string, password: string, confirmPassword: string, name: string, location: string, experienceYears: number, availabilities: string[], abilities: string[], image:File) {
        e.preventDefault();

        const formData:any = new FormData();
        formData.append("mail", mail)
        formData.append("password", password)
        formData.append("confirmPassword", confirmPassword)
        formData.append("name", name)
        formData.append("location", location)
        formData.append("experienceYears", experienceYears)
        availabilities.forEach(a => formData.append("availabilities[]", a))
        abilities.forEach(a => formData.append("abilities[]", a))
        formData.append("image", image, image.name)

        return await fetch('http://localhost:8080/api/employee/', {
            method: 'POST',
            headers: {},
            body: formData
        }).then((r) => r.text())
    }

    public static async editEmployee(e: any, id:number, name: string, location: string, experienceYears: number, availabilities: string[], abilities: string[], image:File) {
        e.preventDefault();

        const formData:any = new FormData();
        formData.append("name", name)
        formData.append("location", location)
        formData.append("experienceYears", experienceYears)
        availabilities.forEach(a => formData.append("availabilities[]", a))
        abilities.forEach(a => formData.append("abilities[]", a))
        formData.append("image", image, image.name)

        return await fetch('http://localhost:8080/api/employee/' + id, {
            method: 'PUT',
            headers: {},
            body: formData
        }).then((r) => r.text())
    }
}
import {EMPLOYEE_URL} from "../utils/utils";

export class EmployeeService {
    public static async getEmployees() {
        return await fetch(EMPLOYEE_URL, {
            method: 'GET',
            headers: localStorage.getItem('hogar-jwt') != null ?{
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
                "Authorization": "Bearer " + localStorage.getItem('hogar-jwt') as string
            } : {
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

        let url = EMPLOYEE_URL + '?'

        if (minimumYears > 0)
            url = this.concatStringQueries(url, 'experience', String(minimumYears))
        url = this.concatStringQueries(url, 'name', name)
        url = this.concatStringQueries(url, 'location', location)
        url = this.concatStringQueries(url, 'abilities', abilitites)
        url = this.concatStringQueries(url, 'availability', availability)

        return await fetch(url, {
            method: 'GET',
            headers: localStorage.getItem('hogar-jwt') != null ?{
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
                "Authorization": "Bearer " + localStorage.getItem('hogar-jwt') as string
            } : {
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


    public static async getEmployee(url: string, edit:boolean) {
        if(edit)
            url = url.concat('?edit=true')
        return await fetch(url, {
            method: 'GET',
            headers: localStorage.getItem('hogar-jwt') != null ? {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem('hogar-jwt') as string
            }: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json"
            }
        }).then((resp) => resp.json())
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async registerEmployee(e: any, mail: string, password: string, confirmPassword: string, name: string, location: string, experienceYears: number, hourlyFee: number, availabilities: string[], abilities: string[], image:File) {
        e.preventDefault();

        const formData:any = new FormData();
        formData.append("mail", mail)
        formData.append("password", password)
        formData.append("confirmPassword", confirmPassword)
        formData.append("name", name)
        formData.append("location", location)
        formData.append("experienceYears", experienceYears)
        formData.append("hourlyFee", hourlyFee)
        availabilities.forEach(a => formData.append("availabilities[]", a))
        abilities.forEach(a => formData.append("abilities[]", a))
        formData.append("image", image, image.name)

        return await fetch(EMPLOYEE_URL, {
            method: 'POST',
            headers: {},
            body: formData
        })
    }

    public static async editEmployee(e: any, self:string, name: string, location: string, experienceYears: number, hourlyFee: number, availabilities: string[], abilities: string[], image:File) {
        e.preventDefault();

        const formData:any = new FormData();
        formData.append("name", name)
        formData.append("location", location)
        formData.append("experienceYears", experienceYears)
        formData.append("hourlyFee", hourlyFee)
        availabilities.forEach(a => formData.append("availabilities[]", a))
        abilities.forEach(a => formData.append("abilities[]", a))
        formData.append("image", image, image.name)

        return await fetch(self, {
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: formData
        }).then((r) => r.text())
    }
}
import {
    EMPLOYEE_URL,
    EMPLOYEES_URL,
    JWTExpired,
    QUERY_PARAM
} from "../utils/utils";

export class EmployeeService {
    public static async getEmployees(basicEncoded: string = "") {
        let header
        if (localStorage.getItem('hogar-jwt') != null) {
            header = {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            }
        } else if (basicEncoded != "") {
            header = {
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + basicEncoded
            }
        } else {
            header = {
                'Content-Type': 'application/json'
            }
        }

        return await fetch(EMPLOYEES_URL, {
            method: 'GET',
            headers: header
        })
            .then((response) => {
                if (response.status === 401) {
                    return JWTExpired()
                }
                return response
            })
            .catch(
                (error) => {
                    throw error
                })
    }

    public static async getFilteredEmployees(
        minimumYears: number,
        page: number,
        name?: string,
        location?: string,
        abilities?: string,
        availability?: string,
        order?: string
    ) {

        let url = EMPLOYEES_URL + QUERY_PARAM

        if (minimumYears > 0)
            url = this.concatStringQueries(url, 'experience', String(minimumYears))
        if (page > 0)
            url = this.concatStringQueries(url, 'page', String(page))
        url = this.concatStringQueries(url, 'name', name)
        url = this.concatStringQueries(url, 'location', location)
        url = this.concatStringQueries(url, 'abilities', abilities)
        url = this.concatStringQueries(url, 'availability', availability)
        url = this.concatStringQueries(url, 'order', order)

        return await fetch(url, {
            method: 'GET',
            headers: localStorage.getItem('hogar-jwt') != null ? {
                'Content-Type': 'application/json',
                "Authorization": "Bearer " + localStorage.getItem('hogar-jwt') as string
            } : {
                'Content-Type': 'application/json'
            }
        })
            .then((response) => {
                if (response.status === 401) {
                    return JWTExpired()
                }
                return response
            })
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


    public static async getEmployee(url: string, edit: boolean) {
        if (edit)
            url = url.concat('?edit=true')
        return await fetch(url, {
            method: 'GET',
            headers: localStorage.getItem('hogar-jwt') != null ? {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem('hogar-jwt') as string
            } : {
                "Content-Type": "application/json"
            }
        }).then((resp) => {
            if (resp.status == 200) {
                return resp.json()
            }
        })
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async registerEmployee(e: any, mail: string, password: string, confirmPassword: string, name: string, location: string, experienceYears: number, hourlyFee: number, availabilities: string[], abilities: string[], image: File) {
        e.preventDefault();

        const employeeForm = JSON.stringify({
            name: name,
            mail: mail,
            password: password,
            confirmPassword: confirmPassword,
            location: location,
            experienceYears: experienceYears,
            hourlyFee: hourlyFee,
            availability: availabilities,
            abilities: abilities,
            image: image
        });

        //TODO: Arreglar lo de las imágenes

        return await fetch(EMPLOYEE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: employeeForm
        })
    }

    public static async editEmployee(e: any, self: string, name: string, location: string, experienceYears: number, hourlyFee: number, availabilities: string[], abilities: string[], image: File) {
        e.preventDefault();

        const employeeEditForm = JSON.stringify({
            name: name,
            location: location,
            experienceYears: experienceYears,
            hourlyFee: hourlyFee,
            availability: availabilities,
            abilities: abilities,
            image: image
        });

        //TODO: Arreglar lo de las imágenes

        return await fetch(self, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: employeeEditForm
        }).then((r) => r.text())
    }
}
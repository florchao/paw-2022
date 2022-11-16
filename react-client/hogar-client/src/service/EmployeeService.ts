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
        availability?: string,
        page?: number,
        pageSize?: number,
        orderCriteria?: string
    ) {

        let url = 'http://localhost:8080/api/explore/employees?'

        if (minimumYears > 0)
            url = this.concatStringQueries(url, 'experience', String(minimumYears))
        url = this.concatStringQueries(url, 'name', name)
        url = this.concatStringQueries(url, 'location', location)
        url = this.concatStringQueries(url, 'abilities', abilitites)
        url = this.concatStringQueries(url, 'availability', availability)
        url = this.concatStringQueries(url, 'page', page?.toString())
        url = this.concatStringQueries(url, 'pageSize', pageSize?.toString())
        url = this.concatStringQueries(url, 'order', orderCriteria)

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

    public static async loadImage(id: number) {
        return fetch('http://localhost:8080/api/profile/image/' + id, {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json"
            },
        }).then((resp) => resp.blob()
        )
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }
}
export class JobService {
    public static async getJobs() {
        return await fetch('http://localhost:8080/api/jobs', {
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

    public static async getFilteredJobs(
        minimumYears: number,
        name?: string,
        location?: string,
        abilitites?: string,
        availability?: string
    ) {

        let url = 'http://localhost:8080/api/explore/jobs?'

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


    public static async getJob(id: number) {
        return await fetch('http://localhost:8080/api/job/' + id, {
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

    public static async postJob(e: any, title: string, location: string, experienceYears: number, availability: string, abilities: string[], description: string) {
        e.preventDefault();
        const jobForm = {title, location, experienceYears, availability, abilities, description};
        return await fetch('http://localhost:8080/api/job', {
            method: 'POST',
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(jobForm)
        }).then((r) => r.text())
    }

    public static async getCreatedJob(id: number) {
        console.log("En el service")
        return await fetch('http://localhost:8080/api/job/employer/' + id, {
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

    public static async getIds() {
        console.log("En el service")
        return await fetch('http://localhost:8080/api/job/ids', {
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
}
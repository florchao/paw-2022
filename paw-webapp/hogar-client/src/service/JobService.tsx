import {BACK_SLASH, JOB_URL, QUERY_PARAM} from "../utils/utils";

export class JobService {
    public static async getJobs() {
        return await fetch(JOB_URL, {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            }
        })
            .catch(
                (error) => {
                    throw error
                })
    }

    public static async getFilteredJobs(
        minimumYears: number,
        page: number,
        name?: string,
        location?: string,
        abilities?: string,
        availability?: string
    ) {

        let url = JOB_URL + QUERY_PARAM

        if (minimumYears > 0)
            url = this.concatStringQueries(url, 'experience', String(minimumYears))
        if(page > 0)
            url = this.concatStringQueries(url, 'page', String(page))
        url = this.concatStringQueries(url, 'name', name)
        url = this.concatStringQueries(url, 'location', location)
        url = this.concatStringQueries(url, 'abilities', abilities)
        url = this.concatStringQueries(url, 'availability', availability)

        return await fetch(url, {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            }
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


    public static async getJob(url: string) {
        return await fetch(url, {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
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
        let formData = new FormData();
        formData.append("title", title);
        formData.append("location", location);
        formData.append("experienceYears", experienceYears.toString());
        formData.append("availability", availability);
        abilities.forEach(a => formData.append("abilities[]", a))
        formData.append("description", description);

        return await fetch(JOB_URL, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: formData
        })
    }

    public static async getCreatedJobs(url: string, profile: boolean, page: number) {
        if(profile)
            url = url + QUERY_PARAM + 'profile=true'
        if(page > 0)
            url = url + QUERY_PARAM + 'page=' + page.toString()
        return await fetch(url, {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
        })
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async getApplicants(url : string, page: number){
        if (page > 0) {
            url = url + QUERY_PARAM + "page=" + page.toString()
        }
        return await fetch(url, {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
        })
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }


    public static async deleteJob(id: number) {
        return await fetch(JOB_URL + BACK_SLASH + id, {
            method: 'DELETE',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            }
        })
    }

    public static async updateJobStatus(id: number, status: boolean){

        const formData:any = new FormData();
        formData.append("status", status)

        return await fetch(JOB_URL + BACK_SLASH + id , {
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: formData
        }).then((r) => r.text())
    }
}
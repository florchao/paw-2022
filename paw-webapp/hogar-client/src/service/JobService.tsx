import {BASE_URL, JOB_URL, JWTExpired, QUERY_PARAM} from "../utils/utils";

export class JobService {
    public static async getJobs() {
        return await fetch(JOB_URL, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            }
        }).then((response) => {
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

    public static async getFilteredJobs(
        minimumYears: number,
        page: number,
        name?: string,
        location?: string,
        abilities?: string,
        availability?: string,
        linkUrl?: string
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

        if (!linkUrl?.startsWith("a") && linkUrl !== undefined && linkUrl !== "") {
            url = linkUrl
        }

        return await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            }
        }).then((response) => {
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

    public static async getJobById(id: number, basicEncoded: string) {
        return await fetch(BASE_URL + 'jobs' + '/' + id, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                'Authorization': 'Basic ' + basicEncoded
            },
        }).catch(
            (error) => {
                console.log(error)
                throw error
            })
    }

    public static async getJob(url: string) {
        return await fetch(url, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt')
            },
        }).then((resp) => {
            if (resp.status === 401) {
                return JWTExpired()
            }
            // else if (resp.status === 200) {
                // return resp.json()
            // }
            return resp
        })
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async postJob(e: any, title: string, location: string, experienceYears: number, availability: string, abilities: string[], description: string) {
        e.preventDefault();

        const jobForm = JSON.stringify({
            title: title,
            location: location,
            experienceYears: experienceYears,
            availability: availability,
            abilities: abilities,
            description: description
        });

        return await fetch(JOB_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: jobForm
        })
            .then((response) => {
                if (response.status === 401) {
                    return JWTExpired()
                }
                return response
            })
    }

    public static async getCreatedJobs(url: string, profile: boolean, page: number, linkUrl?: string) {
        if(profile)
            url = url + QUERY_PARAM + 'profile=true'
        if(page > 0)
            url = url + QUERY_PARAM + 'page=' + page.toString()
        if (linkUrl !== undefined) {
            url = linkUrl
        }
        return await fetch(url, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
        }).then((response) => {
            if (response.status === 401) {
                return JWTExpired()
            }
            return response
        })
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async getApplicants(url : string, page: number, linkUrl?: string){
        if (page > 0) {
            url = url + QUERY_PARAM + "page=" + page.toString()
        }
        if (linkUrl !== undefined) {
            url = linkUrl
        }
        return await fetch(url, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
        }).then((response) => {
            if (response.status === 401) {
                return JWTExpired()
            }
            return response
        })
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }


    public static async deleteJob(url: string) {
        return await fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            }
        }).then((response) => {
            if (response.status === 401) {
                return JWTExpired()
            }
            return response
        })
    }

    public static async updateJobStatus(url: string, status: boolean) {

        const formData: any = new FormData();
        formData.append("status", status)

        return await fetch(url, {
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: formData
        }).then((response) => {
            if (response.status === 401) {
                return JWTExpired()
            }
            return response.text()
        })
    }
}
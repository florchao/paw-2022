import {APPLICANT_URL, BACK_SLASH, EMPLOYEE_URL, JOBS, JWTExpired} from "../utils/utils";


export class ApplicantService{

    public static async getAppliedJobs(id : number, page: number){
        let url = EMPLOYEE_URL + BACK_SLASH + id + JOBS
        if(page > 0) {
            url = url + "?page=" + page
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


    public static async createApplicant(url: string, jobId: number){

        const applyForm = JSON.stringify({
            jobId: jobId
        });
        //TODO: que me pase el url el DTO?
        return await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: applyForm
        }).then((response) => {
            if (response.status === 401) {
                return JWTExpired()
            }
            return response
        })
    }

    public static async updateStatus(url: string, status: number){

        const statusForm = JSON.stringify({
            status: status
        });

        return await fetch(url , {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: statusForm
        }).then((response) => {
            if (response.status === 401) {
                return JWTExpired()
            }
            return response
        })
    }

    public static async deleteApplication(employeeId: number, jobId: number){

        //TODO: que me pase el url el DTO?
        return await fetch(APPLICANT_URL + BACK_SLASH + employeeId + BACK_SLASH + jobId , {
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

    public static async getApplicationStatus(employeeId: number, jobId: number){

        //TODO: que me pase el url el DTO?
        return await fetch(APPLICANT_URL + BACK_SLASH + employeeId + BACK_SLASH + jobId, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
        }).then((response) => {
            if (response.status === 401) {
                return JWTExpired()
            }
            return response.text()

        })
    }


}
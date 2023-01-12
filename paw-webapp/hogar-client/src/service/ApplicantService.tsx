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


    public static async createApplicant(jobId: number){

        const applyForm = JSON.stringify({
            jobId: jobId
        });

        return await fetch(APPLICANT_URL, {
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

    public static async updateStatus(employeeId: number, jobId: number, status: number){

        const statusForm = JSON.stringify({
            status: status
        });

        return await fetch(APPLICANT_URL + BACK_SLASH + employeeId + BACK_SLASH + jobId , {
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
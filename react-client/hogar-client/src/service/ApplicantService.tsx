import {APPLICANT_URL, BACK_SLASH, EMPLOYEE_URL, JOBS} from "../utils/utils";


export class ApplicantService{

    public static async getAppliedJobs(id : number, page: number){
        let url = EMPLOYEE_URL + BACK_SLASH + id + JOBS
        if(page > 0) {
            url = url + "?page=" + page
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


    public static async createApplicant(jobId: number){

        const formData:any = new FormData();
        formData.append("jobId", jobId)

        return await fetch(APPLICANT_URL, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: formData
        }).then((r) => r.text())
    }

    public static async updateStatus(employeeId: number, jobId: number, status: number){

        const formData:any = new FormData();
        formData.append("status", status)

        return await fetch(APPLICANT_URL + BACK_SLASH + employeeId + BACK_SLASH + jobId , {
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: formData
        }).then((r) => r.text())
    }

    public static async deleteApplication(employeeId: number, jobId: number){
        return await fetch(APPLICANT_URL + BACK_SLASH + employeeId + BACK_SLASH + jobId , {
            method: 'DELETE',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string

            }
        })
    }

    public static async getApplicationStatus(employeeId: number, jobId: number){
        return await fetch(APPLICANT_URL + BACK_SLASH + employeeId + BACK_SLASH + jobId, {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
        }).then((r) => r.text())
    }


}
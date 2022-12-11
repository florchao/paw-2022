import {APPLICANT_URL, BACK_SLASH, EMPLOYEE_URL, JOBS} from "../utils/utils";


export class ApplicantService{

    public static async getAppliedJobs(id : number){
        return await fetch(EMPLOYEE_URL + BACK_SLASH + id + JOBS, {
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


    public static async createApplicant(employeeId: number, jobId: number){

        const formData:any = new FormData();
        formData.append("employeeId", employeeId)
        formData.append("jobId", jobId)

        return await fetch(APPLICANT_URL, {
            method: 'POST',
            headers: {},
            body: formData
        }).then((r) => r.text())
    }

    public static async updateStatus(employeeId: number, jobId: number, status: number){

        const formData:any = new FormData();
        formData.append("status", status)

        return await fetch(APPLICANT_URL + BACK_SLASH + employeeId +'/'+ jobId , {
            method: 'PUT',
            headers: {},
            body: formData
        }).then((r) => r.text())
    }

    public static async deleteApplication(employeeId: number, jobId: number){
        return await fetch(APPLICANT_URL + BACK_SLASH + employeeId + BACK_SLASH + jobId , {
            method: 'DELETE',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json'
            }
        })
    }

    public static async getApplicationStatus(employeeId: number, jobId: number){
        return await fetch(APPLICANT_URL + BACK_SLASH + employeeId + BACK_SLASH + jobId, {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json"
            },
        }).then((r) => r.text())
    }


}
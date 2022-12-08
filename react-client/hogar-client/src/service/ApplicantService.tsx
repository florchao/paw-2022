export class ApplicantService{

    public static async getAppliedJobs(id : number){
        return await fetch('http://localhost:8080/api/applicant/' + id + '/jobs', {
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

        return await fetch('http://localhost:8080/api/applicant/', {
            method: 'POST',
            headers: {},
            body: formData
        }).then((r) => r.text())
    }

    public static async updateStatus(employeeId: number, jobId: number, status: number){

        const formData:any = new FormData();
        formData.append("status", status)

        return await fetch('http://localhost:8080/api/applicant/' + employeeId +'/'+ jobId , {
            method: 'PUT',
            headers: {},
            body: formData
        }).then((r) => r.text())
    }

    public static async deleteApplication(employeeId: number, jobId: number){
        return await fetch('http://localhost:8080/api/applicant/' + employeeId +'/'+ jobId , {
            method: 'DELETE',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json'
            }
        })
    }

    public static async getApplicationStatus(employeeId: number, jobId: number){
        return await fetch('http://localhost:8080/api/applicant/' + employeeId +'/'+ jobId, {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json"
            },
        }).then((r) => r.text())
    }


}
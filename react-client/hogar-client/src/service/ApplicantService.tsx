export class ApplicantService{
    public static async getApplicants(id : number){
        return await fetch('http://localhost:8080/api/applicant/' + id, {
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
    //TODO: ver que devuelve el response
    public static async createApplicant(e: any, employeeId: number, jobId: number){
        e.preventDefault();

        const formData:any = new FormData();
        formData.append("employeeId", employeeId)
        formData.append("jobId", jobId)

        return await fetch('http://localhost:8080/api/applicant/', {
            method: 'POST',
            headers: {},
            body: formData
        }).then((r) => r.text())
        //TODO: habria que refreshear la pagina del trabajo
    }

    public static async updateStatus(e:any, employeeId: number, jobId: number, status: number){
        e.preventDefault();

        const formData:any = new FormData();
        formData.append("status", status)

        return await fetch('http://localhost:8080/api/applicant/' + employeeId +'/'+ jobId , {
            method: 'PUT',
            headers: {},
            body: formData
        }).then((r) => r.text())
        // TODO: despues de que volvio me tendria que refreshear la lista de aplicantes
    }

    public static async deleteApplication(e:any, employeeId: number, jobId: number){
        return await fetch('http://localhost:8080/api/applicant/' + employeeId +'/'+ jobId , {
            method: 'DELETE',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json'
            }
        })
        //TODO: redirigir a trabajos aplicados
    }


}
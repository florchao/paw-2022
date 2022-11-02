export class EmployerService {

    public static async getEmployer(id: bigint) {
        return await fetch('http://localhost:8080/api/employer/' + id, {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json'
            }
        }).then((resp) => resp.json())
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }
}
export class EmployerService {

    public static async getEmployer(id: number) {
        //todo harcodeado la url para el usuario
        return await fetch('http://localhost:8080/api/profile/employer/3', {
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
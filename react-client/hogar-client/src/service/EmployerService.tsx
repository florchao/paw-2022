export class EmployerService {

    public static async getEmployer(id: number) {
        //todo harcodeado la url para el usuario
        return await fetch('http://localhost:8080/api/profile/employer/' + id, {
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

    public static async registerEmployer (e: any, name: string,lastname: string, mail: string, password: string, confirmPassword: string, image: string) {
        e.preventDefault();
        const employer = {name, lastname, mail, password, confirmPassword, image};
        return await fetch('http://localhost:8080/api/create/employer', {
            method: 'POST',
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(employer)
        }).then((r) => r.text())
    }
}
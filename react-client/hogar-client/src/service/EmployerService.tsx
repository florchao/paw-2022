export class EmployerService {

    public static async getEmployer(id: number) {
        //todo harcodeado la url para el usuario
        return await fetch('http://localhost:8080/api/employers/' + id, {
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

    public static async registerEmployer (e: any, name: string,lastname: string, mail: string, password: string, confirmPassword: string, image: File) {
        e.preventDefault();

        const formData:any = new FormData();
        formData.append("mail", mail)
        formData.append("password", password)
        formData.append("confirmPassword", confirmPassword)
        formData.append("name", name)
        formData.append("last", lastname)
        formData.append("image", image, image.name)

        return await fetch('http://localhost:8080/api/employers/', {
            method: 'POST',
            headers: {},
            body: formData
        }).then((r) => r.text())
    }
}
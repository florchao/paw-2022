export class UserService{

    public static async deleteUser(id: number) {
        return await fetch('http://localhost:8080/api/user/' + id, {
            method: 'DELETE',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json'
            }
        })
    }

    public static async loadImage(id: number) {
        return fetch('http://localhost:8080/api/image/' + id, {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json"
            },
        }).then((resp) => resp.blob()
        )
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async newPassword(e: any, mail: string, password: string, confirmPassword: string){
        e.preventDefault();
        const form = {mail, password, confirmPassword};
        return await fetch('http://localhost:8080/api/user/',{
            method: 'PUT',
            headers: {"Content-Type": "application/json",
            },
            body: JSON.stringify(form)
        })
    }
}

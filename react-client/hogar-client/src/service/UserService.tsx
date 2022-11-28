export class UserService{

    public static async deleteUser(id: number) {
        return await fetch('http://localhost:8080/api/profile/delete/' + id, {
            method: 'DELETE',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json'
            }
        })
    }

    public static async loadImage(id: number) {
        return fetch('http://localhost:8080/api/profile/image/' + id, {
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
}

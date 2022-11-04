export class RatingService {

    public static async getEmployeeRating(id: number) {
        return await fetch('http://localhost:8080/api/rating/' + id , {
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

}

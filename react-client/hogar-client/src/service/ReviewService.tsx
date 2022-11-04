export class ReviewService {

    public static async getEmployeeReviews(id: number) {
        return await fetch('http://localhost:8080/api/review/employee/' + id , {
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
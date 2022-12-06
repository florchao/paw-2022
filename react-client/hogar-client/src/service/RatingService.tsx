export class RatingService {

    public static async getEmployeeRating(id: number, employerId: number) {
        return await fetch('http://localhost:8080/api/rating/' + id +'/' + employerId, {
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

    public static async postEmployeeRating(employeeId: number, employerId: number) {
        return await fetch('http://localhost:8080/api/rating/' + employeeId +'/' + employerId, {
            method: 'POST',
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

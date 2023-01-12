import {BACK_SLASH, JWTExpired, RATINGS_URL} from "../utils/utils";

export class RatingService {

    public static async getEmployeeRating(url: string, employerId: number) {
        return await fetch(url + BACK_SLASH + employerId, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
        }).then((response) => {
            if (response.status === 401) {
                return JWTExpired()
            }
            return response.json()
        })
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async postEmployeeRating(e: any, employeeId: number, employerId: number, rating: number) {
        e.preventDefault();
        const ratingForm = JSON.stringify({
            rating: rating,
            employeeId: employeeId,
            employerId: employerId
        });

        return await fetch(RATINGS_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: ratingForm
        }).then( r => {
            if (r.status === 401) {
                return JWTExpired()
            }
            return this.getEmployeeRating(r.headers.get('Location') as string, employerId)
            }
        )
    }

}

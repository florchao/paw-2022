import {BACK_SLASH, RATINGS_URL} from "../utils/utils";

export class RatingService {

    public static async getEmployeeRating(url: string, employerId: number) {
        return await fetch(url + BACK_SLASH + employerId, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
        }).then((resp) => resp.json())
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

        //TODO: cambiar lo que hace con el response

        return await fetch(RATINGS_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: ratingForm
        })
    }

}

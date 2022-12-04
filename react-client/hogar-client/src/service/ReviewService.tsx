export class ReviewService {

    public static async getEmployeeReviews(id: number) {
        return await fetch('http://localhost:8080/api/review/employee/' + id, {
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

    public static async getEmployerReviews(id: number) {
        return await fetch('http://localhost:8080/api/review/employer/' + id, {
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

    public static async getMyEmployerReview(employerId: number) {
        //todo el id del employeeId despues va por token. Esta harcodeado
        return await fetch('http://localhost:8080/api/review/employer/' + '4/' + employerId, {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json"
            },
        }).then((resp) => {
                if (resp.status == 200) {
                    return resp.json()
                }
            }
        )
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async putEmployerReview(e: any, employerId: number, message: string) {
        e.preventDefault();
        const formData: any = new FormData();
        formData.append("content", message)
        //todo harcoded id del employee en 4
        return await fetch('http://localhost:8080/api/review/employer/' + employerId + '/4', {
            method: 'POST',
            headers: {},
            body: formData
        }).then((r) => r.json())
    }
}
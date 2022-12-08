export class ReviewService {

    public static async getEmployeeReviews(url: string) {
        return await fetch(url, {
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

    public static async getEmployerReviews(url: string) {
        return await fetch(url, {
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

    public static async getMyEmployerReview(url: string) {
        //todo el id del employeeId despues va por token. Esta harcodeado
        return await fetch(url + '/1?type=employer', {
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

    public static async getMyEmployeeReview(url: number) {
        //todo el id del employeeId despues va por token. Esta harcodeado
        return await fetch(url +  '/2?type=employee', {
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

    public static async putEmployeeReview(e: any, employeeId: number, message: string) {
        e.preventDefault();
        const formData: any = new FormData();
        formData.append("content", message)
        //todo harcoded id del employer en 1
        return await fetch('http://localhost:8080/api/review/employee/' + employeeId + '/1', {
            method: 'POST',
            headers: {},
            body: formData
        }).then((r) => r.json())
    }
}
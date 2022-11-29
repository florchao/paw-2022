export class ContactService {

    public static async contactUs (e: any, name: string, mail: string, content: string) {
        e.preventDefault();
        const blog = {name, mail, content};
        return await fetch('http://localhost:8080/api/contact/us', {
        method: 'POST',
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(blog)
        }).then(() => {
        // history.go(-1);
        })
    }

    public static async contactEmployee(e: any, phone: string, content: string, id: number) {
        e.preventDefault();
        const contactForm = {phone, content};

        return await fetch('http://localhost:8080/api/contact/' + id, {
            method: 'POST',
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(contactForm)
        }).then((r) => r.text())
    }

    public static async contacts(id: number) {
        return await fetch('http://localhost:8080/api/contact/' + id , {
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
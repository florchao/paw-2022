import {IDS_URL} from "../utils/utils";

export class IdsService {

    public static async getIds() {
        return await fetch(IDS_URL, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
            },
        }).then((resp) => resp.json())
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

}
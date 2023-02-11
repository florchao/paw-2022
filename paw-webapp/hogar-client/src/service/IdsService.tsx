import {ABIL_URL, AVAIL_URL, IDS_URL, LOCATION_URL} from "../utils/utils";

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

    public static async getLocations() {
        return await fetch(LOCATION_URL, {
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

    public static async getAvail() {
        return await fetch(AVAIL_URL, {
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

    public static async getAbilities() {
        return await fetch(ABIL_URL, {
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
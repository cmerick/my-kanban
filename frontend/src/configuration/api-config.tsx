import axios from "axios";

const API_URL: string = process.env.API_URL ?? "";


const kanbanApi = axios.create({
    baseURL: API_URL,
});

export { kanbanApi };

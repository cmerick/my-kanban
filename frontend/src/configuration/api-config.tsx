import axios from "axios";

const API_URL: string = process.env.API_URL ?? "";


export const kanbanApi = axios.create({
    baseURL: API_URL,
});

export const frontendApi = axios.create({
    baseURL: 'http://localhost:3000/api'
})

export class ApiConstructor {
    apiUrl: string;
    headers: HeadersInit;

    constructor(apiUrl: string, headers: HeadersInit = {}) {
        this.apiUrl = apiUrl;
        this.headers = headers;
    }

    private async makeRequest(endpoint: string, options: RequestInit) {
        const url = `${this.apiUrl}${endpoint}`;
        const response = await fetch(url, options);
        return response.json();
    }

    async post(endpoint: string, headers: HeadersInit, body: any) {
        const options: RequestInit = {
            method: 'POST',
            headers: {
                ...this.headers,
                ...headers
            },
            body: body,
        };
        return this.makeRequest(endpoint, options);
    }

    async get(endpoint: string, headers: HeadersInit) {
        const options: RequestInit = {
            method: 'GET',
            headers: {
                ...this.headers,
                ...headers,
            },

        };
        return this.makeRequest(endpoint, options);
    }

    // Método PUT
    async put(endpoint: string, headers: HeadersInit, body?: any) {
        const options: RequestInit = {
            method: 'PUT',
            headers: {
                ...this.headers,
                ...headers

            },
            body: body,
        };
        return this.makeRequest(endpoint, options);
    }

    async delete(endpoint: string) {
        const options: RequestInit = {
            method: 'DELETE',
            headers: this.headers,
        };
        return this.makeRequest(endpoint, options);
    }
}

export default class SignInResponseDto {
    access_token!: string;
    expires_in!: number;

    constructor(entity?: SignInResponseDto) {
        if (entity) {
            Object.assign(this, entity);
        }
    }
}
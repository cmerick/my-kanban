import SimpleStatusEnum from "../enum/simple-status.enum";

export class UserMeDto {
    id!: string;
    keycloakId!: string;
    email!: string;
    username!: string;
    status!: SimpleStatusEnum;

    constructor(entity?: UserMeDto) {
        if (entity) {
            Object.assign(this, entity);
        }
    }
}
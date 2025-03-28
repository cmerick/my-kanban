import SimpleStatusEnum from "../enum/simple-status.enum";

export default class ClientResponseDto {
    id!: string;
    name!: string;
    status!: SimpleStatusEnum;
    createdAt!: Date;
    updatedAt!: Date;

    constructor(entity?: ClientResponseDto) {
        if (entity) {
            Object.assign(this, entity);
        }
    }
}
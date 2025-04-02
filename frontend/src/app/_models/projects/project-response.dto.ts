import SimpleStatusEnum from "../enum/simple-status.enum";

export default class ProjectResponseDto {
    id!: string;
    clientId!: string;
    creatorId!: string;
    title!: string;
    description?: string;
    status!: SimpleStatusEnum;
    createdAt!: Date;
    updatedAt!: Date;

    constructor(entity?: ProjectResponseDto) {
        if (entity) {
            Object.assign(this, entity);
        }
    }
}
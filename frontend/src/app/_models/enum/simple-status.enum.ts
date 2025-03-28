import LayoutOptionsModel from "../layout/layout-options.model";

enum SimpleStatusEnum {
    ACTIVE = 'A',
    INACTIVE = 'I',
}

const labels = {
    [SimpleStatusEnum.ACTIVE]: 'Active',
    [SimpleStatusEnum.INACTIVE]: 'Inactive',
}


export default SimpleStatusEnum;

export function parserSimpleStatus(value: SimpleStatusEnum): string {
    return labels[value];
}

export const simpleStatusOptions: LayoutOptionsModel<SimpleStatusEnum>[] = [
    new LayoutOptionsModel(
        parserSimpleStatus(SimpleStatusEnum.ACTIVE),
        SimpleStatusEnum.ACTIVE,
    ),
    new LayoutOptionsModel(
        parserSimpleStatus(SimpleStatusEnum.INACTIVE),
        SimpleStatusEnum.INACTIVE,
    ),
];

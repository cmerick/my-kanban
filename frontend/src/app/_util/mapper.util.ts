export function mapperList<
    T extends new (arg: V) => InstanceType<T>,
    V = never,
>(clazz: T, values?: V[]) {
    return values?.map((value) => mapper(clazz, value)) ?? [];
}

export function mapper<T extends new (arg: V) => InstanceType<T>, V = never>(
    clazz: T,
    value: V,
): InstanceType<T> {
    return new clazz(value);
}
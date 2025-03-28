export default class LayoutOptionsModel<T> {
    constructor(
        public readonly label: string,
        public readonly value: T,
    ) { }
}
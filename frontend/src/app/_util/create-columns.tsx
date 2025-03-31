import { ColumnDef } from "@tanstack/react-table";

export const createColumns = <T,>(keys: Array<keyof T & string>): ColumnDef<T>[] => {
  return keys.map((key) => ({
    accessorKey: key,
    header: (info: any) => { return <div>{info.getValue()}</div> },
    cell: (info: any) => { return <div>{info.getValue()}</div> }
  }));
}
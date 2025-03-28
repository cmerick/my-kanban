import { ColumnDef } from "@tanstack/react-table";
import SimpleStatusEnum, { parserSimpleStatus } from "../_models/enum/simple-status.enum";

const columnFormat = (value: any, key: string) => {
  switch (key) {
    case 'status': // Para tipos de objetos como enums
      return parserSimpleStatus(value); // Aplica a formatação do enum
    case 'createdAt':
    case 'updatedAt':
      return `${new Date(value).toLocaleString()}`;
    // Formata data no formato local
    default:
      return value; // Retorna o valor padrão caso o tipo não seja reconhecido
  }
};

// Define um tipo genérico para colunas
export const createColumns = <T,>(keys: Array<keyof T & string>): ColumnDef<T>[] => {
  return keys.map((key) => ({
    accessorKey: key,
    header: key.charAt(0).toUpperCase() + key.slice(1), // Capitaliza o header automaticamente
    cell: (info) => {
      const type = typeof info.getValue();
      const formated = columnFormat(info.getValue(), key); // Formata conforme o tipo
      return <div>{formated}</div>; // JSX para renderizar o valor formatado
    }
  }));
}
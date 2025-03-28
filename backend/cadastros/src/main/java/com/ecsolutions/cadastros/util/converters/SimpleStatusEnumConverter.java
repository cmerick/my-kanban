package com.ecsolutions.cadastros.util.converters;

import com.ecsolutions.cadastros.model.enums.SimpleStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SimpleStatusEnumConverter implements AttributeConverter<SimpleStatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(SimpleStatusEnum attribute) {
        // Se o atributo for nulo, retorna null; caso contrário, retorna o valor associado ao enum
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public SimpleStatusEnum convertToEntityAttribute(String dbData) {
        // Verifica se o valor do banco é válido e converte para o enum, caso contrário, lança uma exceção
        if (dbData == null) {
            return null;
        }

        for (SimpleStatusEnum status : SimpleStatusEnum.values()) {
            if (status.getValue().equals(dbData)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Invalid value '" + dbData + "' for enum SimpleStatusEnum");
    }
}

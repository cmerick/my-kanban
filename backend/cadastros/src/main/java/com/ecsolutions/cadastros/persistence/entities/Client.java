package com.ecsolutions.cadastros.persistence.entities;


import com.ecsolutions.cadastros.model.enums.SimpleStatusEnum;
import com.ecsolutions.cadastros.util.converters.SimpleStatusEnumConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_client")
public class Client implements Serializable {

    @Serial
    private static final long serialVersionUID = 6436968871178057858L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime updatedAt;

    @Column(name= "status", nullable = false)
    @Convert(converter = SimpleStatusEnumConverter.class)
    private SimpleStatusEnum status;

}

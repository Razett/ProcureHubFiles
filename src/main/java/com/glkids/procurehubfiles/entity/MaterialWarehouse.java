package com.glkids.procurehubfiles.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

/**
 * <b>자재 창고</b>
 *
 * <p>{@code String wrhscode} - 창고 코드 [Varchar(30), PK, Not Null]</p>
 * <p>{@code String name} - 창고명 [Varchar(100), Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class MaterialWarehouse {

    @Id
    @Column(length = 30, nullable = false)
    private String wrhscode;

    @Column(length = 100, nullable = false)
    private String name;
}

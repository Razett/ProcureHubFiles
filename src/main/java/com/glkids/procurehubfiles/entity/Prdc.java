package com.glkids.procurehubfiles.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * <b>제품</b>
 *
 * <p>{@code Long prdcno} - 제품 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code String name} - 제품명 [Varchar(200), Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class Prdc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prdcno;

    @Column(length = 100, nullable = false)
    private String name;
}

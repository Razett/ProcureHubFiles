package com.glkids.procurehubfiles.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * <b>자재 도면</b>
 *
 * <p>{@code Long mtrlfno} - 자재 도면 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Material material} - 자재 [FK, Not Null]</p>
 * <p>{@code String uuid} - UUID [Varchar(150), Nullable]</p>
 * <p>{@code String name} - 파일명 [Varchar(255), Not Null]</p>
 * <p>{@code String url} - URL [Varchar(255), Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class MaterialFile extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mtrlfno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Material material;

    @Column(nullable = true)
    private String uuid;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String url;
}

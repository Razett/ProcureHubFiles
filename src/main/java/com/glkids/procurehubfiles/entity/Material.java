package com.glkids.procurehubfiles.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * <b>자재</b>
 *
 * <p>{@code Long mtrlno} - 자재 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code String name} - 자재명 [Varchar(100), Not Null]</p>
 * <p>{@code String description} - 세부 내용 [Varchar(1024), Nullable]</p>
 * <p>{@code String standard} - 규격 [Varchar(60), Not Null]</p>
 * <p>{@code Long quantity} - 수량 [BIGINT, Not Null]</p>
 * <p>{@code MaterialGroup materialGroup} - 자재 그룹 [FK, Not Null]</p>
 * <p>{@code MaterialWarehouse materialWarehouse} - 자재 창고 위치 [FK, Not Null]</p>
 * <p>{@code Integer status} - 자재 상태 코드 [INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class Material extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mtrlno;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 1024, nullable = true)
    private String description;

    @Column(length = 60, nullable = false)
    private String standard;

    @Column(nullable = false)
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private MaterialGroup materialGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private MaterialWarehouse materialWarehouse;

    @Column(nullable = false)
    private Integer status;
}

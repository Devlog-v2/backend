package com.project.hexagonal.global.entity

import javax.persistence.*


@MappedSuperclass
open class BaseIdxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long = 0L

}
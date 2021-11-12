package ru.dariaaa.biirr.domain.entity

data class Ingredients(
    val hops: List<Hop>,
    val malt: List<Malt>,
    val yeast: String
)
package ru.dariaaa.biirr.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.dariaaa.biirr.domain.entity.Beer

@Entity(tableName = "beers")
data class BeerModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String?,
    val picUrl: String?,
    val abv: Double?,
    val ibu: Double?,
    val foodPairing: String?
) {
    companion object {
        fun from(obj: Beer) = BeerModel(obj.id, obj.name, obj.tagline, obj.description, obj.image_url, obj.abv, obj.ibu,
            obj.food_pairing.joinToString(SEPARATOR).takeIf { it.isNotEmpty() })

        const val SEPARATOR = ", "
    }

}
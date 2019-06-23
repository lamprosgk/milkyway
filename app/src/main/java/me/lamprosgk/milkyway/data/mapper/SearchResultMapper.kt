package me.lamprosgk.milkyway.data.mapper

import me.lamprosgk.milkyway.data.remote.dateCreatedPretty
import me.lamprosgk.milkyway.data.remote.model.Item
import me.lamprosgk.milkyway.domain.model.Milky

class SearchResultMapper : Mapper<Item, Milky> {

    override fun mapTo(from: List<Item>) = from.map { mapTo(it) }

    override fun mapTo(from: Item): Milky {
        val info = from.info.firstOrNull()
        val link = from.links.firstOrNull()

        val id = info?.nasaId ?: ""
        val center = info?.center ?: ""
        val dateCreated = info?.dateCreatedPretty ?: ""
        val title = info?.title ?: ""
        val description = info?.description ?: ""
        val imageUrl = link?.url ?: ""

        return Milky(center = center, dateCreated = dateCreated, title = title,
            description = description, imageUrl = imageUrl, nasaId = id)
    }
}
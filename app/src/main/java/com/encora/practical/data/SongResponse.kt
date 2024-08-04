package com.encora.practical.data

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "feed", strict = false)
data class Feed(
    @field:ElementList(name = "entry", inline = true) @param:ElementList(
        name = "entry",
        inline = true
    ) val entries: List<Song>? = null,
)


@Root(name = "entry", strict = false)
data class Song(
    @field:Element(name = "id") @param:Element(name = "id") var id: String? = null,
    @field:Element(name = "title") @param:Element(name = "title") var title: String? = null,
    @field:Element(name = "artist", required = false) @param:Element(
        name = "artist",
        required = false
    ) var artist: String? = null,
    @field:ElementList(
        entry = "image",
        inline = true,
        required = false
    ) @Namespace(prefix = "im") var images: List<Image>? = null,
    @field:ElementList(
        name = "link",
        inline = true,
        required = false
    ) @param:ElementList(name = "link", inline = true, required = false)
    var previewLinks: List<PreviewLink>? = null,

    @field:Element(name = "releaseDate", required = false) @param:Element(
        name = "releaseDate",
        required = false
    ) var releaseDate: String? = null,

    @field:Element(name = "rights", required = false) @param:Element(
        name = "rights",
        required = false
    ) var rights: String? = null,

    )

@Root(name = "im:image", strict = false)
data class Image(
    @field:Text var url: String = "",
)

@Root(name = "link", strict = false)
@Namespace(reference = "http://www.w3.org/2005/Atom")
data class PreviewLink(
    @field:Attribute(name = "href", required = false)
    var href: String? = null,

    )

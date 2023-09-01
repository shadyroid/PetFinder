package com.softxpert.domain.entity.beans

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class PetBean(
    val id: String,
    var photos: MutableList<Image>? = null,
    var name: String? = null,
    var gender: String? = null,
    var type: String? = null,
    var size: String? = null,
    var colors: Colors? = null,
    var contact: Contact? = null,
    var url: String? = null,
) : Parcelable {
    val displayName: String
        get() = name.takeIf { !it.isNullOrEmpty() } ?: "NA"

    val displayGender: String
        get() = gender.takeIf { !it.isNullOrEmpty() } ?: "NA"

    val displayType: String
        get() = type.takeIf { !it.isNullOrEmpty() } ?: "NA"

    val displaySize: String
        get() = size.takeIf { !it.isNullOrEmpty() } ?: "NA"

    val displayColor: String
        get() = colors?.primary.takeIf { it?.isNotEmpty() == true } ?: "NA"

    val displayAddress: String
        get() {
            val addressParts = listOfNotNull(
                contact?.address?.city,
                contact?.address?.state,
                contact?.address?.country
            )
            return if (addressParts.isEmpty()) "NA" else addressParts.joinToString(", ")
        }


    val displaySmallImage: String?
        get() = photos?.getOrNull(0)?.small

    val displayMediumImage: String?
        get() = photos?.getOrNull(0)?.medium

}

@Parcelize
data class Image(
    var small: String? = null,
    var medium: String? = null,
) : Parcelable

@Parcelize
data class Colors(
    var primary: String? = null,
) : Parcelable

@Parcelize
data class Contact(
    var address: Address,
) : Parcelable

@Parcelize
data class Address(
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
) : Parcelable
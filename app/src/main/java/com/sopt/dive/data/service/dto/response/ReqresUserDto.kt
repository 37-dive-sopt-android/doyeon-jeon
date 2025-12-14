package com.sopt.dive.data.service.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReqresUserResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("total")
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("data")
    val data: List<ReqresUserDto>,
    @SerialName("support")
    val support: ReqresSupportDto,
    @SerialName("_meta")
    val meta: ReqresMetaDto
)

@Serializable
data class ReqresUserDto(
    @SerialName("id")
    val id: Int,
    @SerialName("email")
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("avatar")
    val avatar: String
)

@Serializable
data class ReqresSupportDto(
    @SerialName("url")
    val url: String,
    @SerialName("text")
    val text: String
)

@Serializable
data class ReqresMetaDto(
    @SerialName("powered_by")
    val poweredBy: String,
    @SerialName("upgrade_url")
    val upgradeUrl: String,
    @SerialName("docs_url")
    val docsUrl: String,
    @SerialName("template_gallery")
    val templateGallery: String,
    @SerialName("message")
    val message: String,
    @SerialName("features")
    val features: List<String>,
    @SerialName("upgrade_cta")
    val upgradeCta: String
)

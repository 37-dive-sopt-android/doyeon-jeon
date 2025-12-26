package com.sopt.dive.presentation.search

import com.sopt.dive.presentation.main.Route
import kotlinx.serialization.Serializable

@Serializable
data class Search(
    val id: Int,
    val category: String? = null,
    val keyword: String? = null,
) : Route
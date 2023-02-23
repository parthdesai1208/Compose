package com.parthdesai1208.compose.model.networking.paging3

data class paging3Response(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
package com.example.chaesiktak

data class RecommendRecipe(
    val image: Int,       // 이미지 리소스 ID
    val title: String,    // 레시피 제목
    val subtext: String,   // 서브 텍스트 (0인분, 0시간)
    var isFavorite:Boolean = false // '좋아요' 상태
)

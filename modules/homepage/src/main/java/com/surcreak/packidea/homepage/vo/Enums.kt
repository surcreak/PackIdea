package com.surcreak.packidea.homepage.vo

enum class HomePager(val type: Int, val text: String) {
    RECOMMEND(0, "推荐"),
    HOT(1, "热门"),
    VIDEO(2, "视频"),
    MUSIC(3, "音乐");

    companion object {
        fun from(state: Int?): HomePager {
            enumValues<HomePager>().forEach {
                if (it.type == state) {
                    return it
                }
            }
            return RECOMMEND
        }
    }
}
package com.jonahstarling.memorychimp

class Difficulties {
    companion object {
        val easy = Difficulty("EASY", 5, 9)
        val medium = Difficulty("MEDIUM", 5, 9, 3000L)
        val hard = Difficulty("HARD", 5, 9, 2000L)
        val impossible = Difficulty("IMPOSSIBLE", 5, 9, 1000L)
    }
}

class Difficulty(
    val name: String,
    val minBoxes: Int,
    val maxBoxes: Int,
    val time: Long? = null
)
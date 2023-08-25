package part.soten

import kotlinx.serialization.Serializable

@Serializable
data class Sample(
    val name: String = "최윤호",
    val email: String = "assd"
)

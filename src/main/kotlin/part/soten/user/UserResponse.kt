package part.soten.user

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(val id: Long = 5, val name: String, val email: String)
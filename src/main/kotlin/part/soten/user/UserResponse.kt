package part.soten.user

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(val id: Int = 5, val name: String, val email: String)
package part.soten.user

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(val name: String, val email: String)

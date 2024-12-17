package engine.BusinessLayer

import engine.PersistenceLayer.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {

        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("Not found")
        return UserAdapter(user)
    }
    fun saveUser(userSave: User): User {
        return userRepository.save(userSave)
    }

    fun existUserByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    fun getAuthenticatedUserId(): Long? {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication.isAuthenticated) {
            val userDetails = authentication.principal as? UserAdapter
            return userDetails?.getId() // Recupera el ID del usuario autenticado
        }
        return null
    }
}
package engine.BusinessLayer

import engine.PersistenceLayer.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {

        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Not found")
        return UserAdapter(user)
    }
    fun saveUser(userSave: User): User {
        return userRepository.save(userSave)
    }
}
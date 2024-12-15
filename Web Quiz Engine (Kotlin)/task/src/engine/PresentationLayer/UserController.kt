package engine.PresentationLayer

import engine.BusinessLayer.User
import engine.BusinessLayer.UserService
import engine.PersistenceLayer.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/register")
class UserController(
    private val passwordEncoder: PasswordEncoder,
    private val userService: UserService,
    private val userRepository: UserRepository
) {

    data class RegistrationRequest(val username: String, val password: String, val authority: String)
    @PostMapping("")
    fun register(@RequestBody request: RegistrationRequest){
        val user = User()
        user.username = request.username
        user.password = passwordEncoder.encode(request.password)
        user.authority = "ROLE_USER"

        userService.saveUser(user)


    }

    @GetMapping("")
    fun getRegister(): String{
        return "hola"
    }

}
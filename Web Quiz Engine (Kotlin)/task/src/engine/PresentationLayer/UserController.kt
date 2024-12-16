package engine.PresentationLayer

import engine.BusinessLayer.Quiz
import engine.BusinessLayer.User
import engine.BusinessLayer.UserService
import engine.PersistenceLayer.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/register")
class UserController(
    private val passwordEncoder: PasswordEncoder,
    private val userService: UserService
) {

    /*//data class RegistrationRequest(@field:val email: String, val password: String, val authority: String)
    data class RegistrationRequest(
        @field:Email
        @field:NotEmpty
        val email: String,

        @field:NotEmpty
        @field:Size(min = 5)
        val password: String,

    )*/

   @PostMapping("")
    fun register(@RequestBody request: Map<String, Any>): ResponseEntity<String> {

        if(request.keys != setOf("email", "password")){
            return  ResponseEntity.badRequest().build()
        }
        val email = request["email"]
        val password = request["password"]
        println("hola")
        if (email !is String || !email.matches(Regex("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}\$"))) {
            println("errorEmail")
            return ResponseEntity.badRequest().build()
        }
        println("hola1")
        if(userService.existUserByEmail(email)){
            return ResponseEntity.badRequest().build()
        }
        println("hola2")
        // Validar que la contraseña es una cadena y tiene al menos 5 caracteres
        if (password !is String || password.length < 5) {
            return ResponseEntity.badRequest().build()
        }
        println("hola3")
        val user = User()
        user.email = email
        user.password = passwordEncoder.encode(password)
        user.authority = "ROLE_USER"
        userService.saveUser(user)
        return ResponseEntity.ok().build()

    }

    data class RegistrationRequest(val username: String, val password: String, val authority: String)
    @PostMapping("/hola")
    fun register(@RequestBody request: RegistrationRequest){
        val user = User()
        user.email = request.username
        user.password = passwordEncoder.encode(request.password)
        user.authority = "ROLE_USER"

        userService.saveUser(user)


    }


    @GetMapping("")
    fun getRegister(): ResponseEntity<String> {
        return  ResponseEntity.ok("dwqd")
    }

}
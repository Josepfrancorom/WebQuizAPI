package engine.BusinessLayer

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "quizzes")
@JsonIgnoreProperties(value = ["id"], allowGetters = true)
class Quiz (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    @Column(name = "title")
    @field:NotEmpty
    val title: String = "",

    @Column(name = "text")
    @field:NotEmpty
    val text: String = "",

    @Fetch(value = FetchMode.SUBSELECT)
    @ElementCollection(fetch = FetchType.EAGER)
    @field:Size(min = 2)
    val options: List<String> = emptyList(),

    @Fetch(value = FetchMode.SUBSELECT)
    @ElementCollection(fetch = FetchType.EAGER)
    private val answer: List<Long>? = null,

    @JsonIgnore
    @Column(name = "user_id")
    var userId: Long

){
    fun checkAnswer(): List<Long>{
        return answer ?: emptyList()
    }
}
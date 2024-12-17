package engine.BusinessLayer

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "quiz_completions")
class QuizCompletion(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,

    @Column(name = "quiz_id", nullable = false)
    var quizId: Long,

    @Column(name = "user_id", nullable = false)
    var userId: Long,

    @Column(name = "completed_at", nullable = false)
    var completedAt: LocalDateTime = LocalDateTime.now()
)
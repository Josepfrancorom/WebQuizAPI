package engine.BusinessLayer

import engine.PersistenceLayer.QuizCompletionRepository
import engine.PersistenceLayer.QuizRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class QuizService(
    private val quizRepository: QuizRepository,
    private val userService: UserService,
    private val quizCompletionRepository: QuizCompletionRepository
) {
    fun findQuizById(id: Long): Quiz? {
        return quizRepository.findQuizById(id)
    }

    fun saveQuiz(quizSave: Quiz): Quiz {
        val userId = userService.getAuthenticatedUserId()
        if (userId != null) {
            quizSave.userId = userId
        }
        return quizRepository.save(quizSave)
    }

    @Transactional
    fun deletQuizById(id: Long): Int {
        val quiz = quizRepository.findQuizById(id)
        if(quiz == null){
            return 1;
        }
        if(quiz.userId != userService.getAuthenticatedUserId()){
            return 2;
        }
        quizRepository.deleteQuizById(id)
        return 3
    }



    fun getAllQuizzesPage(page: Int): Page<Quiz> {
        val pageable = PageRequest.of(page, 10)
        return quizRepository.findAll(pageable)
    }

    fun solveQuiz(id: Long, answer: List<Long>): Map<String, Any>? {
        val quiz = findQuizById(id)
        if(quiz == null) {
            return null
        } else {
            val correctanswer = quiz.checkAnswer()
            if (answer.size == correctanswer.size) {
                // Comparar elemento por elemento
                var isEqual = true
                for (i in answer.indices) {
                    if (answer[i] != correctanswer[i]) {
                        isEqual = false
                        break
                    }
                }

                if (isEqual) {
                    val userId = userService.getAuthenticatedUserId()
                    if (userId != null) {
                        saveQuizCompletion(id)
                    }
                    return mapOf("success" to true, "feedback" to "Congratulations, you're right!")
                } else {
                    return mapOf("success" to false, "feedback" to "Wrong answer! Please, try again.")
                }
            } else {
                return mapOf("success" to false, "feedback" to "Wrong answer! Please, try again.")
            }


        }
    }

    fun getCompletedQuizzesPage(page: Int): Page<QuizCompletion> {
        val userId = userService.getAuthenticatedUserId()
            ?: throw IllegalStateException("User not authenticated")

        val pageable = PageRequest.of(page, 10)
        return quizCompletionRepository.findByUserIdOrderByCompletedAtDesc(userId, pageable)
    }

    fun saveQuizCompletion(quizId: Long) {
        val userId = userService.getAuthenticatedUserId()
        if (userId != null) {

            val completion = QuizCompletion(
                quizId = quizId,
                userId = userId,
                completedAt = LocalDateTime.now()
            )
            quizCompletionRepository.save(completion)
        }
    }


}
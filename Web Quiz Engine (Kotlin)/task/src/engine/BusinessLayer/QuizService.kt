package engine.BusinessLayer

import engine.PersistenceLayer.QuizRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class QuizService(private val quizRepository: QuizRepository, private val userService: UserService) {
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
    fun deletQuizById(id: Long): Boolean {
        println("he entrado")
        if(quizRepository.findQuizById(id) == null){
            println("he entrado")
            return false;
        }
        quizRepository.deleteQuizById(id)
        return true
    }

    fun getAllQuizzes(): List<Quiz> {
        return quizRepository.findAll().toList()
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
                    return mapOf("success" to true, "feedback" to "Congratulations, you're right!")
                } else {
                    return mapOf("success" to false, "feedback" to "Wrong answer! Please, try again.")
                }
            } else {
                return mapOf("success" to false, "feedback" to "Wrong answer! Please, try again.")
            }


            /*if (answer == quiz.checkAnswer().toMutableList()) {
                return mapOf("success" to true, "feedback" to "Congratulations, you're right!")
            } else {
                return mapOf("success" to false, "feedback" to "Wrong answer! Please, try again.")
            }*/
        }
    }


}
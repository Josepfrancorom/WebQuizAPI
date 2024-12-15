package engine.PersistenceLayer

import engine.BusinessLayer.Quiz
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface QuizRepository : CrudRepository<Quiz, Long> {
    fun findQuizById(id: Long): Quiz?
}
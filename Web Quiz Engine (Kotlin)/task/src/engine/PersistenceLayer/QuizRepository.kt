package engine.PersistenceLayer

import engine.BusinessLayer.Quiz
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface QuizRepository : PagingAndSortingRepository<Quiz, Long> {
    fun findQuizById(id: Long): Quiz?
    fun deleteQuizById(id: Long)
    fun save(quiz: Quiz): Quiz
}
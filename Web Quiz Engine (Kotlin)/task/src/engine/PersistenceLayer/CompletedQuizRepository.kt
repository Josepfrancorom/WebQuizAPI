package engine.PersistenceLayer

import engine.BusinessLayer.QuizCompletion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface QuizCompletionRepository : PagingAndSortingRepository<QuizCompletion, Long> {
    fun findByUserIdOrderByCompletedAtDesc(userId: Long, pageable: Pageable): Page<QuizCompletion>
    fun save(quizCompletion: QuizCompletion): QuizCompletion
}
package engine.PresentationLayer

import engine.BusinessLayer.Quiz
import engine.BusinessLayer.QuizService


import jakarta.validation.Valid

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quizzes")
class QuizController(private val quizService: QuizService) {

    @GetMapping("/{id}")
    fun getQuizById(@PathVariable id:Long): ResponseEntity<Quiz>{
        val quiz = quizService.findQuizById(id)
        return if (quiz == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(quiz)
        }

    }

    @GetMapping("")
    fun getAllQuizzes(): List<Quiz>{
        return quizService.getAllQuizzes()
    }

    @PostMapping("/{id}/solve")
    fun solveQuizById(@PathVariable id:Long, @RequestBody requestBody: Map<String, Any>): ResponseEntity<Map<String, Any>> {

        val answer = requestBody["answer"] as? List<Long> ?: return ResponseEntity.badRequest().build()

        val response = quizService.solveQuiz(id, answer) ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(response)
    }

    @PostMapping("")
    fun createNewQuiz(@RequestBody @Valid requestBody: Quiz): ResponseEntity<Quiz> {

        quizService.saveQuiz(requestBody)

        return ResponseEntity.ok(requestBody)

    }

}
package com.bignerdranch.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.*
import org.junit.Test

class QuizViewModelTest {
    @Test
    fun providesExpectedQuestionText() {
        // Testing default question text
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)

        // Starting at first question (index 0)
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
        // Testing question text after moving to pre question for the first question
        quizViewModel.moveToPrev()
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)

        // Move to next question
        quizViewModel.moveToNext()
        assertEquals(R.string.question_oceans, quizViewModel.currentQuestionText)

        // Move to next question again
        quizViewModel.moveToNext()
        assertEquals(R.string.question_mideast, quizViewModel.currentQuestionText)
    }

    @Test
    fun wrapsAroundQuestionBankAndMore() {
        // Start with the last question
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
        // Return to the first question
        repeat(5) {
            quizViewModel.moveToPrev()
        }
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
        // Move back and forth
        quizViewModel.moveToNext()
        quizViewModel.moveToPrev()
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)

        // Question should wrap around to the first question
        repeat(15) {
            quizViewModel.moveToPrev()
        }
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun hasCorrectAnswerToQuestion() {
        var savedStateHandle = SavedStateHandle()
        var quizViewModel = QuizViewModel(savedStateHandle)
        assertTrue(quizViewModel.currentQuestionAnswer)

        // Start with the last question
        savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        quizViewModel = QuizViewModel(savedStateHandle)
        assertTrue(quizViewModel.currentQuestionAnswer)

        repeat(10) {
            quizViewModel.moveToPrev()
        }
    }


    @Test
    fun savedStateHandlePreservesCurrentIndex() {
        // Create with initial index of 3
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 3))
        val quizViewModel = QuizViewModel(savedStateHandle)

        assertEquals(R.string.question_africa, quizViewModel.currentQuestionText)

        // Change index
        quizViewModel.moveToNext()

        // Create new ViewModel with same SavedStateHandle
        val newQuizViewModel = QuizViewModel(savedStateHandle)

        // Should have preserved the updated index
        assertEquals(R.string.question_americas, newQuizViewModel.currentQuestionText)
    }

    @Test
    fun savedStateHandlePreservesCheatingStatus() {
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)

        // Set cheating status
        quizViewModel.isCheatingCurrentQuestion = true

        // Create new ViewModel with same SavedStateHandle
        val newQuizViewModel = QuizViewModel(savedStateHandle)

        // Should have preserved cheating status
        assertTrue(newQuizViewModel.isCheatingCurrentQuestion)
    }

    @Test
    fun cheatingStatusPreservedForMultipleQuestions() {
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)

        // Cheat on first question
        quizViewModel.isCheatingCurrentQuestion = true

        // Move to second question and cheat
        quizViewModel.moveToNext()
        quizViewModel.isCheatingCurrentQuestion = true

        // Move to third question but don't cheat
        quizViewModel.moveToNext()

        // Create new ViewModel with same SavedStateHandle
        val newQuizViewModel = QuizViewModel(savedStateHandle)

        // Check third question
        assertFalse(newQuizViewModel.isCheatingCurrentQuestion)
        newQuizViewModel.moveToPrev()

        // Check second question
        assertTrue(newQuizViewModel.isCheatingCurrentQuestion)
        newQuizViewModel.moveToPrev()

        // Check first question
        assertTrue(newQuizViewModel.isCheatingCurrentQuestion)
    }
}

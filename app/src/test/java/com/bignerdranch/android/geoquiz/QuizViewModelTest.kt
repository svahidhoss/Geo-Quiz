package com.bignerdranch.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.*
import org.junit.Test

class QuizViewModelTest {
    @Test
    fun providesExpectedQuestionText() {
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun wrapsAroundQuestionBankAndMore() {
        // Start with the last question
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
        quizViewModel.moveToNext()
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
        // Move back and forth
        quizViewModel.moveToNext()
        quizViewModel.moveToPrev()
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
        quizViewModel.moveToPrev()
        assertTrue(quizViewModel.currentQuestionAnswer)
        quizViewModel.moveToPrev()
        assertFalse(quizViewModel.currentQuestionAnswer)
        quizViewModel.moveToPrev()
        assertFalse(quizViewModel.currentQuestionAnswer)
    }
}

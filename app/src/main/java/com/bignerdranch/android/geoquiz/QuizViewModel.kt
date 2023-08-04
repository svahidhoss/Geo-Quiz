package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    private val questionList = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    var hasAlreadyAnswered = false

    val currentQuestionAnswer: Boolean
        get() = questionList[currentIndex].answer


    val currentQuestionText: Int
        get() = questionList[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionList.size
    }

    fun moveToPrev() {
        currentIndex = if (currentIndex - 1 > 0) currentIndex - 1
        else questionList.size - 1
    }

    init {
        Log.d(TAG, "ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}

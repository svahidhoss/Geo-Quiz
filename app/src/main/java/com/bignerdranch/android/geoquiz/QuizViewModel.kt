package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

private val questionList = listOf(
    Question(R.string.question_australia, true),
    Question(R.string.question_oceans, true),
    Question(R.string.question_mideast, false),
    Question(R.string.question_africa, false),
    Question(R.string.question_americas, true),
    Question(R.string.question_asia, true)
)

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private var currentIndex: Int
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionList[currentIndex].answer

    @get:StringRes
    val currentQuestionText: Int
        get() = questionList[currentIndex].textResId

    private val _isCheatButtonEnabled = MutableLiveData(true)
    val isCheatButtonEnabled: LiveData<Boolean>
        get() = _isCheatButtonEnabled

    var isCheatingCurrentQuestion: Boolean
        get() {
            val isCheatedList: BooleanArray? = savedStateHandle[IS_CHEATER_KEY]
            return isCheatedList?.get(currentIndex) ?: false
        }
        set(value) {
            var isCheatedList: BooleanArray? = savedStateHandle[IS_CHEATER_KEY]
            if (isCheatedList == null) isCheatedList = BooleanArray(questionList.size)

            var recountCheats = false
            if (!isCheatedList[currentIndex] && value) recountCheats = true

            // Set the value first
            isCheatedList[currentIndex] = value
            // Save the updated array
            savedStateHandle[IS_CHEATER_KEY] = isCheatedList

            // Only update cheat count if this is a new cheat
            if (recountCheats) {
                // Count the number of true values in the array
                val cheatedCount = isCheatedList.count { it }

                // Disable cheat button if limit reached
                if (cheatedCount >= 3) _isCheatButtonEnabled.value = false
                Log.d(TAG, "Number of cheats is $cheatedCount")
            }
        }

    fun moveToNext() {
        // Added for debugging purposes of catching an exception in the log
        // Log.d(TAG, "Updating question text", Exception())
        if (currentIndex < questionList.size - 1) currentIndex++
    }

    fun moveToPrev() {
        if (currentIndex > 0) currentIndex -= 1
    }

    init {
        Log.d(TAG, "ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}

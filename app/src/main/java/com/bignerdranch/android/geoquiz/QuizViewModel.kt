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

    private var numOfCheats: Int = 0

    private val _isCheatButtonEnabled = MutableLiveData(true)
    val isCheatButtonEnabled: LiveData<Boolean>
        get() = _isCheatButtonEnabled

    var isCheater: Boolean
        get() {
            val isCheatedList: BooleanArray? = savedStateHandle[IS_CHEATER_KEY]
            return isCheatedList?.get(currentIndex) ?: false
        }
        set(value) {
            var isCheatedList: BooleanArray? = savedStateHandle[IS_CHEATER_KEY]
            if (isCheatedList == null) isCheatedList = BooleanArray(questionList.size)
            if (!isCheatedList[currentIndex] && value) numOfCheats++
            // Disable the button after 3 question cheats
            if (numOfCheats >= 3) _isCheatButtonEnabled.value = false
            Log.d(TAG, "numOfCheats is %d".format(numOfCheats))
            isCheatedList[currentIndex] = value
            savedStateHandle[IS_CHEATER_KEY] = isCheatedList
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

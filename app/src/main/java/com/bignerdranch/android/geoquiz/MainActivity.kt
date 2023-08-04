package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val questionList = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener { _: View ->
            checkAnswer(true)

//            Snackbar.make(
//                findViewById(android.R.id.content),
//                R.string.correct_toast,
//                Snackbar.LENGTH_LONG
//            )
//                .setAction("CLOSE") { }
//                .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
//                .show()
        }

        binding.falseButton.setOnClickListener { _: View ->
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            nextQuestion()
        }

        binding.prevButton.setOnClickListener {
            prevQuestion()
        }

        binding.questionTextView.setOnClickListener {
            nextQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionList.size
        updateQuestion()
    }

    private fun prevQuestion() {
        currentIndex = if (currentIndex - 1 > 0) currentIndex - 1
        else questionList.size - 1
        updateQuestion()
    }

    private fun updateQuestion() {
        binding.questionTextView.setText(questionList[currentIndex].textResId)
    }

    /**
     * Checks the answer given by the user and updates the view
     */
    private fun checkAnswer(userAnswer: Boolean) {
        val isAnswerCorrect = userAnswer == questionList[currentIndex].answer

        val messageResId = if (isAnswerCorrect) R.string.correct_toast else R.string.incorrect_toast

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }
}
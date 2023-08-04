package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener { _: View ->
            checkAnswer(true)
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
        quizViewModel.moveToNext()
        updateQuestion()
    }

    private fun prevQuestion() {
        quizViewModel.moveToPrev()
        updateQuestion()
    }

    private fun updateQuestion() {
        binding.questionTextView.setText(quizViewModel.currentQuestionText)
    }

    /**
     * Checks the answer given by the user and updates the view
     */
    private fun checkAnswer(userAnswer: Boolean) {
        val isAnswerCorrect = userAnswer == quizViewModel.currentQuestionAnswer

        val messageResId = if (isAnswerCorrect) R.string.correct_toast else R.string.incorrect_toast

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

        Snackbar.make(
            findViewById(android.R.id.content),
            messageResId,
            Snackbar.LENGTH_LONG
        )
            .setAction("CLOSE") { }
            .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
            .show()
    }
}

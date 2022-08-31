package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//ScoreViewModelFactory call this method
// Step 3
class ScoreViewModel(finalScore:Int): ViewModel() {
    //The final score
    //Declare score as LiveData
    private val _score=MutableLiveData<Int>()
    val score:LiveData<Int>
    get()=_score

    //Play Again
    private val _eventPlayAgain=MutableLiveData<Boolean>()
    val eventPlayAgain:LiveData<Boolean>
    get()=_eventPlayAgain

    init{
       _score.value=finalScore
    }

    fun onPlayAgain(){
        _eventPlayAgain.value=true
    }

    fun onPlayAgainComplete(){
        _eventPlayAgain.value=false
    }
}
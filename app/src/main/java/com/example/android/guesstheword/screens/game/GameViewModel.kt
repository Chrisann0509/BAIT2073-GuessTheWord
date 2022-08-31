package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    // The current word
    val word = MutableLiveData<String>()
    // The current score
    val score = MutableLiveData<Int>()
    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    //Event which triggers the end of the game
    private val _eventGameFinish=MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
    get()=_eventGameFinish

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    init {

        //word and score is LivaData now
        word.value=""
        score.value=0
        //reset the word list when the ViewModal is created, not every time the fragment created
        resetList()
        nextWord()
        Log.i("GameViewModel", "GameViewModel created!")
    }
    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        if (wordList.isEmpty()) {
            onGameFinish()
        }else{
            //Select and remove a word from the list
            word.value = wordList.removeAt(0)
        }
    }
    /** Methods for buttons presses
     * not private, you will refernce these method from the fragment **/

    fun onSkip() {
        score.value=(score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        score.value=(score.value)?.plus(1)
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

    fun onGameFinish(){
        _eventGameFinish.value=true
    }

    fun onGameFinishComplete(){
        _eventGameFinish.value=false
    }
}
package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    //cannot use private, will cause Fragment cannot call the value
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

    //Countdown time
    private val _currentTime =MutableLiveData<Long>()
    val currentTime:LiveData<Long>
    get()=_currentTime
    private val timer: CountDownTimer

    //The String version of the current time
    val currentTimeString= Transformations.map(currentTime){time->
        DateUtils.formatElapsedTime(time)
    }

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
        //populated the function
        resetList()
        nextWord()
        Log.i("GameViewModel", "GameViewModel created!")

        //Creates a timer which triggers the end of the game when it finishes
        timer=object:CountDownTimer(COUNTDOWN_TIMER, ONE_SECOND){
            override fun onTick(milliUntilFinished:Long) {
                _currentTime.value=milliUntilFinished/ ONE_SECOND

                //COUNTDOWN_TIMER =60000
                //ONE_SECOND=1000
                //60000/1000= 60 second
            }

            override fun onFinish() {
               _currentTime.value= DONE
                onGameFinish()
            }
        }

        timer.start()

    }
    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        if (wordList.isEmpty()) {
           // onGameFinish()
            resetList()
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

        //LiveData Practical
        //Cancel the timer
        timer.cancel()
    }

    fun onGameFinish(){
        _eventGameFinish.value=true
    }

    fun onGameFinishComplete(){
        _eventGameFinish.value=false
    }

    companion object{
        private const val DONE =0L
        private const  val  ONE_SECOND = 1000L
        private const val COUNTDOWN_TIMER=60000L
    }
}
package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    enum class Player
    {
        NOUGHT,
        CROSS
    }

    companion object
    {
        const val NOUGHT = "O"
        const val CROSS = "X"
    }

    private var firstTurn = Player.CROSS
    private var currentTurn = Player.CROSS

    private var crossesScore = 0
    private var noughtsScore = 0

    private var boardList = mutableListOf<Button>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()

        val title: TextView = findViewById(R.id.header);
        title.text = getString(R.string.app_name);

        val sec: TextView = findViewById(R.id.secondary_header);
        sec.text = getString(R.string.who_plays);
    }

    private fun initBoard()
    {
        boardList.add(binding.btn1)
        boardList.add(binding.btn2)
        boardList.add(binding.btn3)
        boardList.add(binding.btn4)
        boardList.add(binding.btn5)
        boardList.add(binding.btn6)
        boardList.add(binding.btn7)
        boardList.add(binding.btn8)
        boardList.add(binding.btn9)
    }

    fun onClickButton(view: View) {
        if (view !is Button)
            return

        drawOnBoard(view)

        if (checkVictory(NOUGHT)) {
            noughtsScore++
            result("Noughts Win")
        } else if (checkVictory(CROSS)) {
            crossesScore++
            result("Crosses Win")
        }

        if (fullBoard()) {
            result("It's a Draw")
        }
    }


    private fun drawOnBoard(button: Button){
        if(button.text != "")
            return

        if(currentTurn == Player.NOUGHT)
        {
            button.text = NOUGHT
            currentTurn = Player.CROSS
        }
        else if(currentTurn == Player.CROSS)
        {
            button.text = CROSS
            currentTurn = Player.NOUGHT
        }

        setCurrentPlayerLabel()
    }

    private fun setCurrentPlayerLabel() {
        var turnText = ""
        if(currentTurn == Player.CROSS)
            turnText = CROSS
        else if(currentTurn == Player.NOUGHT)
            turnText = NOUGHT

        binding.currentPlayer.text = turnText
    }

    private fun match(button: Button, symbol : String): Boolean = button.text == symbol

    private fun checkVictory(s: String): Boolean
    {
        //Horizontal Victory
        if(match(binding.btn1,s) && match(binding.btn2,s) && match(binding.btn3,s))
            return true
        if(match(binding.btn4,s) && match(binding.btn5,s) && match(binding.btn6,s))
            return true
        if(match(binding.btn7,s) && match(binding.btn8,s) && match(binding.btn3,s))
            return true

        //Vertical Victory
        if(match(binding.btn1,s) && match(binding.btn4,s) && match(binding.btn7,s))
            return true
        if(match(binding.btn2,s) && match(binding.btn5,s) && match(binding.btn8,s))
            return true
        if(match(binding.btn3,s) && match(binding.btn6,s) && match(binding.btn9,s))
            return true

        //Diagonal Victory
        if(match(binding.btn1,s) && match(binding.btn5,s) && match(binding.btn9,s))
            return true
        if(match(binding.btn3,s) && match(binding.btn5,s) && match(binding.btn7,s))
            return true

        return false
    }

    private fun result(title: String)
    {
        val message = "\nNoughts $noughtsScore\n\nCrosses $crossesScore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Play again")
            { _,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun fullBoard(): Boolean
    {
        for(button in boardList)
        {
            if(button.text == "")
                return false
        }
        return true
    }

    private fun resetBoard()
    {
        for(button in boardList)
        {
            button.text = ""
        }

        if(firstTurn == Player.NOUGHT)
            firstTurn = Player.CROSS
        else if(firstTurn == Player.CROSS)
            firstTurn = Player.NOUGHT

        currentTurn = firstTurn
        setCurrentPlayerLabel()
    }
}
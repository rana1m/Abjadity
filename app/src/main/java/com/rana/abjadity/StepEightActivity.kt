package com.rana.abjadity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_step_eight.*

class StepEightActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_eight)

        var back: FloatingActionButton = findViewById<FloatingActionButton>(R.id.back)
        var forward: FloatingActionButton = findViewById(R.id.forward)
        var childId = intent.getStringExtra("childId")
        var parentId = intent.getStringExtra("parentId")
        var childLevel = intent.getStringExtra("childLevel")
        var button = intent.getStringExtra("button")
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val alphabetsRef: DatabaseReference =  database.getReference("alphabets")
        val character = findViewById<VideoView>(R.id.character)

        characterInitialization()

        alphabetsRef.orderByChild("id").equalTo(button).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //loop through letters to
                for (userSnapshot in dataSnapshot.children) {
                    val letter = userSnapshot.getValue(Letter::class.java)!!
                    Log.e("StepEigthActivity", letter.getName())
                    StrokeManager.download()
                    recognize.setOnClickListener {
                        StrokeManager.recognize(this@StepEightActivity,letter.getName())
                    }
                    clear.setOnClickListener {
                        drawingView.clear()
                        StrokeManager.clear()
                    }


                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })



        back.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(this@StepEightActivity, StepSevenActivity::class.java)
                startActivity(intent)
            }

        })

        forward.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(this@StepEightActivity, StepSevenActivity::class.java)
                startActivity(intent)            }

        })

    }

    private fun characterInitialization() {
        val path = "android.resource://" + packageName + "/" + R.raw.v2
        val uri = Uri.parse(path)
        character.setVideoURI(uri)
        character.setZOrderOnTop(true)
        character.start()
        character.setOnTouchListener { sv, event ->
            character.start()
            false
        }
    }
}
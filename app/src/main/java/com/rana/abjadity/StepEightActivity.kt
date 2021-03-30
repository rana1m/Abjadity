package com.rana.abjadity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_step_eight.*

class StepEightActivity : AppCompatActivity() {
    var button=""
    var childLevel=""
    var parentId=""
    var childId=""
    var character: VideoView? =null
    var accountRef: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_eight)


        var back: FloatingActionButton = findViewById<FloatingActionButton>(R.id.back)
        var forward: FloatingActionButton = findViewById(R.id.forward)
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val database2: FirebaseDatabase = FirebaseDatabase.getInstance()
        var alphabetsRef: DatabaseReference =  database.getReference("alphabets")
        accountRef = database2.getReference("accounts")
        character = findViewById<VideoView>(R.id.character)
        childId = intent.getStringExtra("childId")
        parentId = intent.getStringExtra("parentId")
        button = intent.getStringExtra("button")
        var level: TextView = findViewById(R.id.level)
        var scores:TextView = findViewById(R.id.score)

        characterInitialization()
        Log.e("StepEigthActivity", childId)
        Log.e("StepEigthActivity", parentId)

        accountRef?.orderByChild("id")?.equalTo(parentId)?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //loop through accounts to find the parent with that id
                for (userSnapshot in dataSnapshot.children) {

                    //loop through parent children to add them to adapter ArrayList
                    for (theChild in userSnapshot.child("children").children) {
                        val child = theChild.getValue(Child::class.java)!!
                        if (child.id == childId) {
                            scores.setText(child.score.toString())
                            level.text = child.level.toString()
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })

        alphabetsRef.orderByChild("id").equalTo(button).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //loop through letters to
                for (userSnapshot in dataSnapshot.children) {
                    val letter = userSnapshot.getValue(Letter::class.java)!!
                    Log.e("StepEigthActivity", letter.getName())
                    StrokeManager.download()
                    recognize.setOnClickListener {
                        StrokeManager.recognize(this@StepEightActivity,letter.getName(),parentId,childId)

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
                intent.putExtra("childId", childId)
                intent.putExtra("parentId", parentId)
                intent.putExtra("button", button)
                intent.putExtra("childLevel", childLevel)
                startActivity(intent)
            }

        })

        forward.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(this@StepEightActivity, FinalStepActivity::class.java)
                intent.putExtra("childId", childId)
                intent.putExtra("parentId", parentId)
                intent.putExtra("button", button)

                startActivity(intent)            }

        })

    }



    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@StepEightActivity, StepSevenActivity::class.java)
        intent.putExtra("childId", childId)
        intent.putExtra("parentId", parentId)
        intent.putExtra("button", button)

        startActivity(intent)
    }

    private fun characterInitialization() {
        val path = "android.resource://" + packageName + "/" + R.raw.v2
        val uri = Uri.parse(path)
        character?.setVideoURI(uri)
        character?.setZOrderOnTop(true)
        character?.start()
        character?.setOnTouchListener { sv, event ->
            character?.start()
            false
        }
    }
}
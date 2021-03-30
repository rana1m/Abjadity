package com.rana.abjadity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.util.Log
import android.view.MotionEvent
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.database.*
import com.google.mlkit.common.MlKitException
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.vision.digitalink.*


object StrokeManager {
    private var inkBuilder = Ink.builder()
    private var strokeBuilder = Ink.Stroke.builder()
    private lateinit var model: DigitalInkRecognitionModel
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val accountRef: DatabaseReference =  database.getReference("accounts")
    var mediaPlayer = MediaPlayer()

    fun launchNextScreen(context: Context,parentId: String,childId: String): Intent? {
        val intent = Intent(context, StrokeManager::class.java)
        intent.putExtra("childId", childId)
        intent.putExtra("parentId", parentId)
        return intent
    }

    fun addNewTouchEvent(event: MotionEvent) {
        val action = event.actionMasked
        val x = event.x
        val y = event.y
        val t = System.currentTimeMillis()

        when (action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> strokeBuilder.addPoint(
                    Ink.Point.create(
                            x,
                            y,
                            t
                    )
            )
            MotionEvent.ACTION_UP -> {
                strokeBuilder.addPoint(Ink.Point.create(x, y, t))
                inkBuilder.addStroke(strokeBuilder.build())
                strokeBuilder = Ink.Stroke.builder()

            }
            else -> {
                // Action not relevant for ink construction
            }
        }
    }

    fun download() {
        var modelIdentifier: DigitalInkRecognitionModelIdentifier? = null
        try {
            modelIdentifier =
                    DigitalInkRecognitionModelIdentifier.fromLanguageTag("ar-SA")
        } catch (e: MlKitException) {
            // language tag failed to parse, handle error.
        }

        model =
                DigitalInkRecognitionModel.builder(modelIdentifier!!).build()

        val remoteModelManager = RemoteModelManager.getInstance()
        remoteModelManager.download(model, DownloadConditions.Builder().build())
                .addOnSuccessListener {
                    Log.i("StrokeManager", "Model downloaded")
                }
                .addOnFailureListener { e: Exception ->
                    Log.e("StrokeManager", "Error while downloading a model: $e")
                }
    }

    fun recognize(context: Context, letter: String,parentId: String,childId: String) {
        val recognizer: DigitalInkRecognizer =
                DigitalInkRecognition.getClient(
                        DigitalInkRecognizerOptions.builder(model).build()
                )

        val ink = inkBuilder.build()

        recognizer.recognize(ink)
                .addOnSuccessListener { result: RecognitionResult ->
                    Toast.makeText(context, "أرى حرف الـ ${result.candidates[0].text}", Toast.LENGTH_LONG)
                            .show()
                    if (result.candidates[0].text.equals(letter)) {
                        winningFunction(context,parentId,childId)                    }
                    else if (letter.equals("أ")&&result.candidates[0].text.equals("ا")){
                        winningFunction(context,parentId,childId)
                    } else{tryAgain(context)}
                }
                .addOnFailureListener { e: Exception ->
                    Log.e("StrokeManager", "Error during recognition: $e")
                }
    }
    private fun tryAgain(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.wrong_answer_dialog)
        val yesBtn = dialog.findViewById(R.id.buttonOk) as Button
        dialog.show()
        playVoice("try_again",context)

        yesBtn.setOnClickListener {
            dialog.dismiss()
        }

    }

    private fun winningFunction(context: Context,parentId: String,childId: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.correct_answer_dialog)

        val yesBtn = dialog.findViewById(R.id.buttonOk) as Button
        dialog.show()
        playVoice("",context)
        updateScores(parentId,childId)

        yesBtn.setOnClickListener {
          // context.startActivity(launchNextScreen(context,parentId,childId))
            dialog.dismiss()
        }

    }
    private fun updateScores(parentId: String,childId: String) {
        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //loop through accounts to find the parent with that id
                for (userSnapshot in dataSnapshot.children) {

                    //loop through parent children to add them to adapter ArrayList
                    for (theChild in userSnapshot.child("children").children) {
                        val child = theChild.getValue(Child::class.java)!!
                        if (child.id == childId) {
                            val score =5+ child.score
                            theChild.ref.child("score").setValue(score)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })
    }
    private fun playVoice(s: String,context: Context) {
        var path = ""
        path = if (s == "try_again") {
            "android.resource://" + context.getPackageName() + "/" + R.raw.try_again
        } else {
            "android.resource://" + context.getPackageName() + "/" + R.raw.correct_answer
        }
        val uri = Uri.parse(path)
        mediaPlayer.reset()
        mediaPlayer.setDataSource(context, uri)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener(OnPreparedListener { mp -> mp.start() })
    }


    fun clear() {
        inkBuilder = Ink.builder()
    }

}
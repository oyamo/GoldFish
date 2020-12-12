package com.oyasis.goldfish

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class FishAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var fishes = listOf<Fish>()
    private var height = 0
    private var points = 0
    private var successes = 0x0;
    private val POINT_INTERVALS = 10
    private var time = System.currentTimeMillis()
    private var turns = 0
    private var inHomePage = false
    private var showRealFishes = false
    private var isAnimating = false

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return FishViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.fish_view, parent, false), height
        )
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val fishViewHolder = holder as FishViewHolder
        val fishItem = fishViewHolder.bindView(fishes[position])

        val fish = fishes[position]

        if (showRealFishes) {
            GlobalScope.launch(Dispatchers.Main) {
                // Change the color
                GlobalScope.launch(Dispatchers.Main) {
                    fishItem.setCardBackgroundColor(Color.parseColor("#00ffffff"))
                    fishItem.setStrokeColor(Color.parseColor("#ffffff"))
                    fishItem.strokeWidth = 3
                }

                // Delay
                delay(position * 120L)

                // Change image
                GlobalScope.launch (Dispatchers.Main){
                    fishItem.findViewById<ImageView>(R.id.fishPic).apply {
                        setImageResource(fish.pic)
                    }
                }

                // Animate
                GlobalScope.launch(Dispatchers.Main) {
                    val animator = AnimatorInflater.loadAnimator(context, R.animator.rotation_anim)
                    animator.setTarget(fishItem)
                    animator.start()
                }
                var stop = false
                GlobalScope.launch(Dispatchers.Main) {
                    do {

                       if(!stop){
                           var delayTime = (Math.random() * 7000) + 2000L
                           delay(delayTime.toLong())
                           fishItem.setStrokeColor(Color.parseColor("#00ffffff"))
                           fishItem.strokeWidth = 0
                           var animation =
                                   arrayListOf(R.animator.flip_anim_y, R.animator.rotation_scale_anim, R.animator.flip_anim_x, R.animator.rotation_anim).random()
                           val animator = AnimatorInflater.loadAnimator(context, animation)
                           GlobalScope.launch(Dispatchers.Main) {
                               fishItem.setCardBackgroundColor(Color.parseColor("#CA2424"))
                               delay(500)
                               fishItem.setCardBackgroundColor(Color.parseColor("#1E88E5"))
                           }
                           animator.setTarget(fishItem)
                           animator.start()
                       }
                    }while (!stop)
                        GlobalScope.launch (Dispatchers.Main){

                            fishItem.findViewById<ImageView>(R.id.fishPic).apply {
                                setImageResource(R.drawable.fish_0)
                            }

                            showRealFishes = false
                            inHomePage = false
                            notifyDataSetChanged()
                        }

                }

                delay(13000)
                stop = true





            }
            return
        }

        if (inHomePage) {
            GlobalScope.launch(Dispatchers.Main) {
                while (true) {
                    var delayTime = (Math.random() * 7000) + 2000L
                    delay(delayTime.toLong())
                    val animator = AnimatorInflater.loadAnimator(context, R.animator.flip_anim_y)
                    GlobalScope.launch(Dispatchers.Main) {
                        fishItem.setCardBackgroundColor(Color.parseColor("#CA2424"))
                        delay(500)
                        fishItem.setCardBackgroundColor(Color.parseColor("#1E88E5"))
                    }
                    animator.setTarget(fishItem)
                    animator.start()
                }
            }
        }


        // Set onclick listener
        fishItem.setOnClickListener {

            lateinit var mp: MediaPlayer
            var gameProgress = GameProgress(context=context);



            // Increase the number of turns by one
            turns += 1

            if (fish.hasBeenTapped)
                return@setOnClickListener

            val fishOfSameType = fishes.filter {
                it.hasBeenTapped && it.type == fish.type
            }

            val openFishes = fishes.filter {
                it.hasBeenTapped
            }

            if (openFishes.size % 2 == 0
            /**Opening*/
            ) {

                fish.hasBeenTapped = true
                notifyItemChanged(position)

            } else if (fishOfSameType.size == 1
            /**Closing*/
            ) {

                fish.hasBeenTapped = true
                notifyItemChanged(position)

                // Play the sound
                mp = MediaPlayer.create(context, R.raw.win)
                mp.isLooping = false
                mp.setVolume(100f, 100f)
                mp.start()

                val closedFish = fishes.filter { it.hasBeenTapped }
                val maxPoints = 40 + (Math.random() * POINT_INTERVALS).toInt()
                if (points >= maxPoints) {
                    gameProgress.progress
                }
                if(gameProgress.fewestTurns == 0 ){
                    gameProgress.fewestTurns = turns
                } else if(gameProgress.fewestTurns > turns) {
                    gameProgress.fewestTurns = turns
                }

                val timeTaken = ((System.currentTimeMillis() - time) / 1000).toInt()
                if(gameProgress.shortestTime == 0 ) {
                    gameProgress.shortestTime  = timeTaken
                } else if(gameProgress.shortestTime > timeTaken) {
                    gameProgress.shortestTime = timeTaken
                }

                gameProgress.longestProgress += arrayListOf(1,0,0,0,1).random()

                Toast.makeText(context, "Congrats", Toast.LENGTH_SHORT).show()

                points += 10 + (Math.random() * POINT_INTERVALS).toInt()

                if(!gameProgress.firstTimeLucky){
                    gameProgress.firstTimeLucky = true
                }

                successes++
                if (successes == 3 && turns < 7) {
                    gameProgress.threeInArow = true
                }

                if (successes == 5 && turns < 15) {
                    gameProgress.fiveInArow = true
                }



                if (closedFish.size == 20) {
                    // Dialog for displaying the  alert
                    val dialog = Dialog(context)

                    // set the content view
                    dialog.setContentView(R.layout.high_score)

                    // Set the title which will be visible to the user
                    dialog.setTitle("Game over")


                    val gameProgress = GameProgress(context)

                    gameProgress.gamesPlayed += 1

                    val numberOfTurns = dialog.findViewById<MaterialTextView>(R.id.numberOfTurns)
                    val timeElapsed = dialog.findViewById<MaterialTextView>(R.id.timeElapsed)
                    val matchesPlayed = dialog.findViewById<MaterialTextView>(R.id.longestMatchSequences)
                    val continueButton = dialog.findViewById<MaterialButton>(R.id.replay)

                    matchesPlayed.text = "Longest match sequence: ${gameProgress.gamesPlayed}"

                    val seconds = (System.currentTimeMillis() - time) / 1000

                    timeElapsed.text = "Game time elapsed: $seconds sec"

                    numberOfTurns.text = "Turns : $turns"

                    continueButton.setOnClickListener {
                        dialog.dismiss()
                        context.startActivity(Intent(context, GameActivity::class.java))
                    }
                    dialog.show()


                }


                var animaTimes = 0

                GlobalScope.launch(Dispatchers.Main) {
                    val animator = AnimatorInflater.loadAnimator(context, R.animator.flip_anim_x)
                    animator.addListener(object : Animator.AnimatorListener {

                        override fun onAnimationStart(animation: Animator?) {

                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            animaTimes++

                            if (animaTimes <= 4) {
                                animator.duration = 500
                                animator.start()
                            }
                        }

                        override fun onAnimationCancel(animation: Animator?) {

                        }

                        override fun onAnimationRepeat(animation: Animator?) {

                        }

                    })
                    animator.setTarget(fishItem)
                    animator.start()
                }


            } else if (!fish.hasBeenTapped) {

                // Play the sound
                mp = MediaPlayer.create(context, R.raw.fail)
                mp.isLooping = false
                mp.setVolume(100f, 100f)
                mp.start()

                var animation = arrayListOf(R.animator.flip_anim_y, R.animator.flip_anim_x)
                val animator = AnimatorInflater.loadAnimator(context, animation.random())

                animator.setTarget(fishItem)
                animator.start()

                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(
                            VibrationEffect.createOneShot(
                                    100,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                            )
                    )
                } else {
                    vibrator.vibrate(100)
                }


                GlobalScope.launch(Dispatchers.Main) {
                    delay(100)
                    mp.stop()
                    mp.release()

                }

            }


        }
    }

    override fun getItemCount(): Int {
        return this.fishes.size
    }

    fun setFishes(fishes: List<Fish>) {
        this.fishes = fishes
        notifyDataSetChanged()
    }

    fun setDisplayHeight(height: Int) {
        this.height = (height / 8) - 56
    }

    fun toggleRestingMode(restingMode: Boolean) {
        this.inHomePage = restingMode
        notifyDataSetChanged()
    }

    fun showRealFishes(show: Boolean) {
        this.showRealFishes = true
        notifyDataSetChanged()
    }


}
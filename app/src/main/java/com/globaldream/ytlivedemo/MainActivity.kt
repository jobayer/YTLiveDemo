package com.globaldream.ytlivedemo

import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener,
    YouTubePlayer.PlayerStateChangeListener, YouTubePlayer.PlaybackEventListener {

    private lateinit var ytPlayer: YouTubePlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        yt.initialize("YOUR_YOUTUBE_API_KEY", this)
        btn.setOnClickListener {
            if (::ytPlayer.isInitialized) {
                val url = input.text.toString()
                val id = getID(url)
                if (id.isNotBlank()) {
                    ytPlayer.loadVideo(id)
                } else Toast.makeText(this, "Invalid url", Toast.LENGTH_LONG).show()
            }
        }
        rg.setOnCheckedChangeListener { group, checkedId ->
            if (::ytPlayer.isInitialized) {
                when(checkedId) {
                    R.id.rb1 -> {
                        ytPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                    }
                    R.id.rb2 -> {
                        ytPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)
                    }
                    R.id.rb3 -> {
                        ytPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL)
                    }
                }
            }
        }
    }

    private fun getID(url: String): String {
        val pattern =
            "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"

        val compiledPattern: Pattern = Pattern.compile(pattern)
        val matcher: Matcher =
            compiledPattern.matcher(url) //url is youtube url for which you want to extract the id.

        return if (matcher.find()) {
            matcher.group()
        } else ""
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        p1?.let {
            ytPlayer = it
            it.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
            it.setPlayerStateChangeListener(this)
            it.setPlaybackEventListener(this)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {

    }

    override fun onAdStarted() {}

    override fun onLoading() {}

    override fun onVideoStarted() {}

    override fun onLoaded(p0: String?) {}

    override fun onVideoEnded() {}

    override fun onError(p0: YouTubePlayer.ErrorReason?) {}

    override fun onSeekTo(p0: Int) {}

    override fun onBuffering(p0: Boolean) {}

    override fun onPlaying() {}

    override fun onStopped() {}

    override fun onPaused() {}
}
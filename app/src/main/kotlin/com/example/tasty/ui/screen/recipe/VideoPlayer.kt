package com.example.tasty.ui.screen.recipe

import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem.fromUri
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.tasty.ui.theme.TastyTheme

@Composable
@OptIn(UnstableApi::class)
fun VideoPlayer(
	videoUrl: String
) {
	val context = LocalContext.current

	val exoPlayer = remember {
		ExoPlayer.Builder(context)
			.build()
			.apply {
				val defaultDataSourceFactory = DefaultDataSource.Factory(context)
				val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
					context,
					defaultDataSourceFactory
				)
				val mediaSource = getMediaSource(videoUrl, dataSourceFactory)
				setMediaSource(mediaSource)
				prepare()
				playWhenReady = true
			}
	}

	AndroidView(
		factory = { c ->
			PlayerView(c).apply {
				resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
				player = exoPlayer
				layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
			}
		}
	)

	DisposableEffect(key1 = Unit) {
		onDispose { exoPlayer.release() }
	}
}

private const val M3U8_VIDEO_EXTENSION = "m3u8"

private fun getMediaSource(
	videoUrl: String,
	dataSourceFactory: DataSource.Factory
) = if (videoUrl.endsWith(M3U8_VIDEO_EXTENSION)) {
	getHlsMediaSource(
		mediaUrl = videoUrl,
		dataSourceFactory = dataSourceFactory
	)
} else {
	getProgressiveMediaSource(
		mediaUrl = videoUrl,
		dataSourceFactory = dataSourceFactory
	)
}

@OptIn(UnstableApi::class)
private fun getHlsMediaSource(
	mediaUrl: String,
	dataSourceFactory: DataSource.Factory
): MediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(fromUri(mediaUrl))

@OptIn(UnstableApi::class)
private fun getProgressiveMediaSource(
	mediaUrl: String,
	dataSourceFactory: DataSource.Factory
): MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
	.createMediaSource(fromUri(Uri.parse(mediaUrl)))

@Preview
@Composable
fun VideoPlayerComposablePreview() {
	TastyTheme {
		VideoPlayer(videoUrl = "")
	}
}

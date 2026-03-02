package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

data class ArtItem(
    @DrawableRes val img: Int,
    @StringRes val title: Int,
    @StringRes val artist: Int,
    @StringRes val year: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtGallery()
                }
            }
        }
    }
}

@Composable
fun ArtGallery() {
    val list = listOf(
        ArtItem(R.drawable.art_1, R.string.art_1_title, R.string.art_1_artist, R.string.art_1_year),
        ArtItem(R.drawable.art_2, R.string.art_2_title, R.string.art_2_artist, R.string.art_2_year),
        ArtItem(R.drawable.art_3, R.string.art_3_title, R.string.art_3_artist, R.string.art_3_year)
    )

    var i by remember { mutableIntStateOf(0) }

    // looping logic
    fun next() { i = (i + 1) % list.size }
    fun prev() { i = if (i == 0) list.lastIndex else i - 1 }

    ArtLayout(
        item = list[i],
        onPrev = { prev() },
        onNext = { next() }
    )
}

@Composable
fun ArtLayout(
    item: ArtItem,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        // IMAGE FRAME (fixed size so UI never jumps)
        Box(
            modifier = Modifier
                .widthIn(max = 360.dp)
                .fillMaxWidth()
                .aspectRatio(3f / 4f) // square like the sample
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(item.img),
                contentDescription = stringResource(item.title),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // TEXT CARD (title + artist/year)
        Surface(
            modifier = Modifier
                .widthIn(max = 360.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            tonalElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = stringResource(item.title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${stringResource(item.artist)} (${stringResource(item.year)})",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // BUTTONS (same width, fixed position)
        Row(
            modifier = Modifier
                .widthIn(max = 360.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onPrev,
                modifier = Modifier.width(140.dp)
            ) { Text(text = stringResource(R.string.previous)) }

            Button(
                onClick = onNext,
                modifier = Modifier.width(140.dp)
            ) { Text(text = stringResource(R.string.next)) }
        }
    }
}
package com.example.composemovie.ui.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.composemovie.movie_detail.domain.models.Review
import com.example.composemovie.ui.components.CollapsibleText
import com.example.composemovie.ui.home.itemSpacing
import kotlin.math.round

@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    review: Review
) {
    Column (modifier){
        val nameAnnotatedString = buildAnnotatedString {
            append(review.author)
            append(" • ")
            append(review.createdAt)
        }
        val ratingAnnotatedString = buildAnnotatedString {

            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(round(review.rating).toString()) // round to nearest int for display
            pop()

            pushStyle(SpanStyle(fontSize = 10.sp))
            append("10")
            pop()
        }
        Text(
            text = nameAnnotatedString,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(itemSpacing))
        CollapsibleText(text = review.content)
        Spacer(modifier = Modifier.height(itemSpacing))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Star, contentDescription = null)
            Text(text = ratingAnnotatedString, style = MaterialTheme.typography.bodySmall)
        }
    }

}
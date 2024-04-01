package com.parade621.compose_permission_sample.permissions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.parade621.compose_permission_sample.R

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): AnnotatedString
}

class RequiredPermissionTextProvider(
    private val targetText: String
) : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): AnnotatedString {
        val text = if (isPermanentlyDeclined) {
            "To run this app, Please allow access to the ${targetText}." +
                    "\nPlease manually grant permission in your settings."
        } else {
            "To run this app, you must allow access to your ${targetText}."
        }

        val annotatedText = buildAnnotatedString {
            append(text)
            withStyle(
                style = SpanStyle(
                    color = Color.Red,
                    fontFamily = FontFamily(Font(R.font.gmarket_sans_medium)),
                    fontWeight = FontWeight.Bold
                )
            ) {
                val startIndex = text.indexOf(targetText)
                if (startIndex >= 0) {
                    addStyle(
                        SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold),
                        startIndex,
                        startIndex + targetText.length
                    )
                }
            }
        }
        return annotatedText
    }
}
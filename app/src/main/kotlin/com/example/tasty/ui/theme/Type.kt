package com.example.tasty.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.tasty.R

private val provider = GoogleFont.Provider(
	providerAuthority = "com.google.android.gms.fonts",
	providerPackage = "com.google.android.gms",
	certificates = R.array.com_google_android_gms_fonts_certs
)

private val fontName = GoogleFont("Inter")

private val fontFamily = FontFamily(
	Font(googleFont = fontName, fontProvider = provider)
)

// Set of Material typography styles to start with
val Typography = Typography(
	bodyLarge = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 17.sp,
		lineHeight = 27.sp,
		color = DarkBlue,
	),
	bodyMedium = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 15.sp,
		lineHeight = 25.sp,
		color = DarkBlue,
	),
	bodySmall = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 12.sp,
		lineHeight = 23.sp,
		color = DarkBlue,
	),
	titleLarge = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 22.sp,
		lineHeight = 32.sp,
		letterSpacing = 0.5.sp
	),
	titleMedium = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 17.sp,
		lineHeight = 27.sp,
		letterSpacing = 0.5.sp
	),
	titleSmall = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 15.sp,
		lineHeight = 25.sp,
		letterSpacing = 0.5.sp
	)
)

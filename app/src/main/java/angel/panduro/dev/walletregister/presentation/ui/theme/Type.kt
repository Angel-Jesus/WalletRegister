package angel.panduro.dev.walletregister.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import angel.panduro.dev.walletregister.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val notoSansFamily = FontFamily(fonts = listOf(Font(R.font.noto_sans_jp_regular)))
val notoSansMediumFamily = FontFamily(fonts = listOf(Font(R.font.noto_sans_jp_medium)))
val notoSansBoldFamily = FontFamily(fonts = listOf(Font(R.font.noto_sans_jp_bold)))

val TitleStyle = TextStyle(
    fontFamily = notoSansBoldFamily,
    fontSize = 16.sp
)

val DrawerItemStyle = TextStyle(
    fontFamily = notoSansFamily,
    fontSize = 14.sp
)

val subtitleSmallStyle = TextStyle(
    fontFamily = notoSansMediumFamily,
    fontSize = 12.sp
)

val subtitleRegularStyle = TextStyle(
    fontFamily = notoSansMediumFamily,
    fontSize = 14.sp
)

val subtitleMediumStyle = TextStyle(
    fontFamily = notoSansMediumFamily,
    fontSize = 20.sp
)

val DescriptionStyle = TextStyle(
    fontFamily = notoSansFamily,
    fontSize = 12.sp
)

val BalanceStyle = TextStyle(
    fontFamily = notoSansBoldFamily,
    fontSize = 14.sp
)
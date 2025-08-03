package angel.panduro.dev.walletregister.presentation.ui.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import angel.panduro.dev.walletregister.presentation.ui.model.CategoryInformation
import angel.panduro.dev.walletregister.presentation.ui.theme.DescriptionStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.SubtitleXRegularStyle
import angel.panduro.dev.walletregister.presentation.ui.utils.constance.FormatNumber.FORMAT_WITH_TWO_DECIMAL
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.formatNumber
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.sumOfFloat

@Composable
fun WalletPieChart(
    modifier: Modifier = Modifier,
    data: Map<String, CategoryInformation>,
    color: Color = Color.White,
    radiusOuter: Dp = 80.dp,
    chartBarWidth: Dp = 35.dp,
    animDuration: Int = 1000
){
    val totalSum = data.values.sumOfFloat { it.value }
    val floatValue = mutableListOf<Float>()
    var lastValue = 0f

    if(totalSum > 0){
        data.values.forEach { information ->
            floatValue.add(360 * information.value / totalSum)
        }
    }

    var animationPlayed by remember { mutableStateOf(true) }

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 28.dp, top = 16.dp)
                .size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Deuda Total:\n${data.values.firstOrNull()?.typeMoney.orEmpty()} ${totalSum.formatNumber(FORMAT_WITH_TWO_DECIMAL)}",
                textAlign = TextAlign.Center,
                style = SubtitleXRegularStyle,
                color = color
            )

            Canvas(
                modifier = Modifier
                    .offset { IntOffset.Zero }
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
                data.values.forEachIndexed { index, type ->
                    drawArc(
                        color = type.color,
                        lastValue,
                        floatValue[index],
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += floatValue[index]
                }
            }
        }

        DetailsPieChart(
            color = color,
            data = data
        )
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DetailsPieChart(
    color: Color,
    data: Map<String, CategoryInformation>
) {
    FlowRow(
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        maxItemsInEachRow = 4
    ) {
        data.forEach { (key, category) ->
            DetailPieChartItem(
                color = color,
                key = key,
                category = category
            )
        }
    }
}

@Composable
private fun DetailPieChartItem(
    color: Color,
    key: String,
    category: CategoryInformation
) {
    Row(
        modifier = Modifier
            .wrapContentHeight(align = Alignment.CenterVertically)
    ) {
        Canvas(
            modifier = Modifier
                .size(24.dp)
        ) {
            drawCircle(
                color = category.color,
                radius = 5.dp.toPx()
            )
        }
        Text(
            text = key,
            style = DescriptionStyle,
            color = color
        )
    }
}
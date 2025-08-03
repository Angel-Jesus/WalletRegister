package angel.panduro.dev.walletregister.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import angel.panduro.dev.walletregister.presentation.ui.theme.GreenTopBarColor
import angel.panduro.dev.walletregister.presentation.ui.theme.TitleStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun WalletTab(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    tabsList: List<String>,
    pagerState: PagerState,
    pageContent: @Composable PagerScope.(page: Int) -> Unit
){
    val selectedTab = pagerState.currentPage

    Column(modifier = modifier) {
        TabRow(
            modifier = Modifier
                .fillMaxWidth(),
            indicator = { tabPositions ->
                if (selectedTab < tabPositions.size) {

                    val gradient = Brush.verticalGradient(
                        0.0f to Color.White,
                        0.9f to Color.LightGray,
                        1f to Color.LightGray
                    )

                    if (selectedTab < tabPositions.size) {
                        Box(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[selectedTab])
                                .fillMaxWidth()
                                .height(4.dp)
                                .background(gradient)
                        )
                    }
                }
            },
            selectedTabIndex = selectedTab,
            contentColor = Color.White,
            containerColor = GreenTopBarColor,
        ) {
            tabsList.forEachIndexed { index, item ->
                Tab(
                    selected = selectedTab == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = item,
                            style = TitleStyle,
                            color = Color.White
                        )
                    }
                )
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            pageContent = pageContent
        )
    }
}
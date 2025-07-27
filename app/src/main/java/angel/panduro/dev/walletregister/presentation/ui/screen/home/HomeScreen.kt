package angel.panduro.dev.walletregister.presentation.ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angel.panduro.dev.walletregister.R
import angel.panduro.dev.walletregister.presentation.ui.component.WalletTopAppBar
import angel.panduro.dev.walletregister.presentation.ui.model.CardInformation
import angel.panduro.dev.walletregister.presentation.ui.theme.BalanceStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.ContainerBlockColor
import angel.panduro.dev.walletregister.presentation.ui.theme.ContainerLightColor
import angel.panduro.dev.walletregister.presentation.ui.theme.DescriptionStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.subtitleMediumStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.subtitleRegularStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.subtitleSmallStyle
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY_ID
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.formatNumber
import angel.panduro.dev.walletregister.presentation.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel<HomeViewModel>(),
    onDisplayDrawer: () -> Unit,
    onAddCard: () -> Unit
){
    val uiState by homeViewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            WalletTopAppBar(
                titleTopBar = stringResource(R.string.home_title_top_bar),
                onDisplayDrawer = onDisplayDrawer
            )
        },
        containerColor = ContainerLightColor
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                CardsSection(
                    modifier = Modifier.fillMaxWidth(),
                    cards = uiState.cards,
                    onAddCard = onAddCard
                )
            }

            if(uiState.idCardSelected != Long.EMPTY_ID){
                item {
                    CurrentBalanceCard(
                        card = uiState.cards.first { it.id == uiState.idCardSelected },
                        creditLineUsed = uiState.creditLineUsed
                    )
                }
            }
        }
    }
}

@Composable
private fun CardsSection(
    modifier: Modifier = Modifier,
    cards: List<CardInformation> = emptyList(),
    onAddCard: () -> Unit = {},
){
    Column(
        modifier = modifier.background(ContainerBlockColor).padding(vertical = 8.dp, horizontal = 8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.title_acount_home),
                style = subtitleRegularStyle,
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            OutlinedIconButton(
                modifier = Modifier.size(32.dp),
                onClick = onAddCard,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "AddCard",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(6.dp),
            maxItemsInEachRow = 2
        ) {
            cards.forEachIndexed { index, cardWallet ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clickable(onClick = {  }),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(cardWallet.colorCard)
                    ),
                    shape = RoundedCornerShape(5.dp)
                ){
                    Text(
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                        text = cardWallet.nameCard,
                        style = DescriptionStyle,
                        color = Color.LightGray
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, bottom = 4.dp),
                        text = cardWallet.typeMoney + " " + cardWallet.creditLineCard.formatNumber(),
                        style = BalanceStyle,
                        color = Color.White
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun CurrentBalanceCard(
    modifier: Modifier = Modifier,
    card: CardInformation = CardInformation(nameCard = "Bbva BeFree", creditLineCard = "1000.00", typeMoney = "PEN", paidDateExpired = 0, dateClose = 0, colorCard = Color.Red.value),
    creditLineUsed: Float = 500.0f
){
    val creditLineUsed = card.creditLineCard.toFloat() - creditLineUsed

    Card(
        modifier = modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
        colors = CardDefaults.cardColors(
            containerColor = ContainerBlockColor
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = stringResource(R.string.title_balance),
                style = subtitleRegularStyle,
                color = Color.White
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = card.nameCard,
                style = subtitleMediumStyle,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            LinearProgressIndicator(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth()
                    .padding(start = 18.dp, end = 18.dp),
                progress = { (creditLineUsed / card.creditLineCard.toFloat()) },
                color = Color(card.colorCard),
                trackColor = Color.LightGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column {
                    Text(
                        text = card.typeMoney + " " + creditLineUsed.formatNumber(),
                        style = subtitleRegularStyle,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(R.string.subtitle_credit_line_used),
                        style = subtitleSmallStyle,
                        color = Color.LightGray
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = card.typeMoney + " " + card.creditLineCard.formatNumber(),
                        style = subtitleRegularStyle,
                        color = Color.White,
                        textAlign = TextAlign.End
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(R.string.subtitle_credit_line_available),
                        style = subtitleSmallStyle,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

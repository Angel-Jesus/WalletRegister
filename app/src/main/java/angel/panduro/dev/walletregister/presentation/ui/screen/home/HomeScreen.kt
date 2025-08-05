package angel.panduro.dev.walletregister.presentation.ui.screen.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import angel.panduro.dev.walletregister.R
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEffect
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEvent.ChangeCardSelected
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEvent.DeleteCard
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEvent.EditCard
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEvent.GetAllCards
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEvent.GetCreditLineUsed
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEvent.GetDebtResume
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEvent.HideModal
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEvent.ShowModal
import angel.panduro.dev.walletregister.presentation.ui.component.WalletModal
import angel.panduro.dev.walletregister.presentation.ui.component.WalletPieChart
import angel.panduro.dev.walletregister.presentation.ui.component.WalletTopAppBar
import angel.panduro.dev.walletregister.presentation.ui.model.CardInformation
import angel.panduro.dev.walletregister.presentation.ui.model.CategoryInformation
import angel.panduro.dev.walletregister.presentation.ui.theme.BalanceStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.ContainerBlockColor
import angel.panduro.dev.walletregister.presentation.ui.theme.ContainerDarkColor
import angel.panduro.dev.walletregister.presentation.ui.theme.DescriptionStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.SubtitleLargeStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.SubtitleRegularStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.SubtitleSmallStyle
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.formatNumber
import angel.panduro.dev.walletregister.presentation.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel<HomeViewModel>(),
    onDisplayDrawer: () -> Unit,
    onSettingCard: (String) -> Unit
){
    val sheetState = rememberModalBottomSheetState()

    val cardsState by homeViewModel.cardsState.collectAsStateWithLifecycle()
    val balanceState by homeViewModel.balanceState.collectAsStateWithLifecycle()
    val debtsState by homeViewModel.debtsState.collectAsStateWithLifecycle()
    val modalState by homeViewModel.modalState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        homeViewModel.onEvent(GetAllCards)

        homeViewModel.uiEffect.collect{ effect ->
            when(effect){
                is HomeEffect.GetAllInformationByCard -> {
                    homeViewModel.onEvent(GetCreditLineUsed(cardsState.idCardSelected))
                    homeViewModel.onEvent(GetDebtResume(cardsState.idCardSelected))
                }
                is HomeEffect.OnSettingCard -> onSettingCard(effect.cardInformationJson)
            }
        }
    }

    Log.d("Compose", "recomposition")

    if (modalState.showModal) {
        WalletModal(
            sheetState = sheetState,
            title = stringResource(R.string.setting_card_title),
            description = stringResource(R.string.question_setting_card),
            textPositive = stringResource(R.string.edit_card_button),
            textNegative = stringResource(R.string.delete_card_button),
            onPositiveClick = { homeViewModel.onEvent(EditCard(modalState.temporalIdCard)) },
            onNegativeClick = { homeViewModel.onEvent(DeleteCard(modalState.temporalIdCard)) },
            onDismiss = { homeViewModel.onEvent(HideModal) }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Log.d("Compose", "recomposition TopAppBar")
            WalletTopAppBar(
                titleTopBar = stringResource(R.string.home_title_top_bar),
                onDisplayDrawer = onDisplayDrawer
            )
        },
        containerColor = ContainerDarkColor
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                CardsSection(
                    modifier = Modifier.fillMaxWidth(),
                    cards = cardsState.cards,
                    idCardSelected = cardsState.idCardSelected,
                    onCardSelected = { idCard -> homeViewModel.onEvent(ChangeCardSelected(idCard)) },
                    onCardLongClick = { idCard -> homeViewModel.onEvent(ShowModal(idCard)) },
                    onAddCard = { onSettingCard(String.EMPTY) }
                )
            }


            item {
                CurrentBalanceCard(balance = balanceState)
            }


            item {
                DebtsSection(debts = debtsState)
            }

        }
    }
}

@Composable
private fun CardsSection(
    modifier: Modifier = Modifier,
    idCardSelected: Long,
    cards: List<CardInformation> = emptyList(),
    onCardSelected: (Long) -> Unit = {},
    onCardLongClick: (Long) -> Unit = {},
    onAddCard: () -> Unit = {},
){
    Column(
        modifier = modifier
            .background(ContainerBlockColor)
            .padding(vertical = 8.dp, horizontal = 8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.title_acount_home),
                style = SubtitleRegularStyle,
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
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 2
        ) {
            cards.forEach{ cardWallet ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(horizontal = 4.dp)
                        .combinedClickable(
                            onClick = { onCardSelected(cardWallet.id) },
                            onLongClick = { onCardLongClick(cardWallet.id) }
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(cardWallet.colorCard)
                    ),
                    elevation = CardDefaults.cardElevation(4.dp.takeIf { cardWallet.id == idCardSelected } ?: 0.dp),
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

        Spacer(modifier = Modifier.height(8.dp))
    }

}

@Composable
private fun CurrentBalanceCard(
    modifier: Modifier = Modifier,
    balance: HomeViewModel.BalanceState?,
){
    if(balance == null) return

    val creditLineAvailable = balance.card.creditLineCard.toFloat() - balance.creditLineUsed

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
        colors = CardDefaults.cardColors(
            containerColor = ContainerBlockColor
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = stringResource(R.string.title_balance),
                style = SubtitleRegularStyle,
                color = Color.White
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = balance.card.nameCard,
                style = SubtitleLargeStyle,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            LinearProgressIndicator(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth()
                    .padding(start = 18.dp, end = 18.dp),
                progress = { (balance.creditLineUsed / balance.card.creditLineCard.toFloat()) },
                color = Color(balance.card.colorCard),
                trackColor = Color.LightGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column {
                    Text(
                        text = balance.card.typeMoney + " " + balance.creditLineUsed.formatNumber(),
                        style = SubtitleRegularStyle,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(R.string.subtitle_credit_line_used),
                        style = SubtitleSmallStyle,
                        color = Color.LightGray
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = balance.card.typeMoney + " " + creditLineAvailable.formatNumber(),
                        style = SubtitleRegularStyle,
                        color = Color.White,
                        textAlign = TextAlign.End
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(R.string.subtitle_credit_line_available),
                        style = SubtitleSmallStyle,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

@Composable
private fun DebtsSection(
    modifier: Modifier = Modifier,
    debts: Map<String, CategoryInformation>
){
    if(debts.isEmpty()) return

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
        colors = CardDefaults.cardColors(
            containerColor = ContainerBlockColor
        )
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.subtitle_debts),
                style = SubtitleRegularStyle,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            WalletPieChart(modifier = Modifier.fillMaxWidth(), data = debts)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
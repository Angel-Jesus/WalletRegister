package angel.panduro.dev.walletregister.presentation.ui.screen.card_section

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import angel.panduro.dev.walletregister.R
import angel.panduro.dev.walletregister.presentation.contract.card_section.CardSectionEffect
import angel.panduro.dev.walletregister.presentation.contract.card_section.CardSectionEvent
import angel.panduro.dev.walletregister.presentation.contract.card_section.CardSectionEvent.ColorChanged
import angel.panduro.dev.walletregister.presentation.contract.card_section.CardSectionEvent.InputChanged
import angel.panduro.dev.walletregister.presentation.contract.card_section.CardSectionEvent.SaveCard
import angel.panduro.dev.walletregister.presentation.ui.component.WalletDropDownColor
import angel.panduro.dev.walletregister.presentation.ui.component.WalletInput
import angel.panduro.dev.walletregister.presentation.ui.theme.ContainerDarkColor
import angel.panduro.dev.walletregister.presentation.ui.theme.GreenTopBarColor
import angel.panduro.dev.walletregister.presentation.ui.theme.TitleTopAppBarStyle
import angel.panduro.dev.walletregister.presentation.ui.utils.constance.WarningMessage
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.CardInformationEnum
import angel.panduro.dev.walletregister.presentation.viewmodel.CardSectionViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CardSectionScreen(
    cardSectionViewModel: CardSectionViewModel = koinViewModel<CardSectionViewModel>(),
    cardInformation: String,
    onBack: () -> Unit
){
    val scope = rememberCoroutineScope()
    val creditName by cardSectionViewModel.creditCardName.collectAsStateWithLifecycle()
    val creditLine by cardSectionViewModel.creditLineValue.collectAsStateWithLifecycle()
    val moneyType by cardSectionViewModel.moneyType.collectAsStateWithLifecycle()
    val paymentDueDay by cardSectionViewModel.paymentDueDay.collectAsStateWithLifecycle()
    val closingDay by cardSectionViewModel.closingDay.collectAsStateWithLifecycle()
    val colorCard by cardSectionViewModel.colorCard.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        if(cardInformation.isNotEmpty()){
            cardSectionViewModel.onEvent(CardSectionEvent.InitCardInformation(cardInformation))
        }

        cardSectionViewModel.uiEffect.collectLatest { effect ->
            when(effect){
                CardSectionEffect.MissingFields -> scope.launch { snackbarHostState.showSnackbar(WarningMessage.PENDING_FIELD_CARD) }
                CardSectionEffect.SuccessSaveCard -> onBack()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CardSectionTopBar(
                onSaveCard = { cardSectionViewModel.onEvent(SaveCard) },
                onBack = onBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = ContainerDarkColor
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                WalletInput(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = stringResource(R.string.credit_crd_name),
                    text = creditName,
                    valueChanged = {
                        cardSectionViewModel.onEvent(InputChanged(CardInformationEnum.CREDIT_CARD_NAME, it))
                    },
                )
            }

            item {
                WalletInput(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = stringResource(R.string.credit_line_value),
                    text = creditLine,
                    valueChanged = {
                        cardSectionViewModel.onEvent(InputChanged(CardInformationEnum.CREDIT_LINE_VALUE, it))
                    },
                    isNumeric = true
                )
            }

            item {
                WalletInput(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = stringResource(R.string.money_type),
                    text = moneyType,
                    valueChanged = {
                        cardSectionViewModel.onEvent(InputChanged(CardInformationEnum.MONEY_TYPE, it))
                    },
                )
            }

            item {
                WalletInput(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = stringResource(R.string.payment_due_date),
                    text = paymentDueDay,
                    valueChanged = {
                        cardSectionViewModel.onEvent(InputChanged(CardInformationEnum.PAYMENT_DUE_DATE, it))
                    },
                    isNumeric = true
                )
            }

            item {
                WalletInput(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = stringResource(R.string.closing_date),
                    text = closingDay,
                    valueChanged = {
                        cardSectionViewModel.onEvent(InputChanged(CardInformationEnum.CLOSING_DATE, it))
                    },
                    isNumeric = true
                )
            }

            item {
                WalletDropDownColor(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = stringResource(R.string.credit_card_color),
                    color = colorCard,
                    colorClick = {
                        cardSectionViewModel.onEvent(ColorChanged(it))
                    },
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardSectionTopBar(
    onSaveCard: () -> Unit = {},
    onBack: () -> Unit = {}
){
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.title_add_card),
                style = TitleTopAppBarStyle
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "CloseIcon"
                )
            }
        },
        actions = {
            IconButton(
                onClick = onSaveCard
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "AddIcon"
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = GreenTopBarColor,
            scrolledContainerColor = GreenTopBarColor,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}
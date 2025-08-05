package angel.panduro.dev.walletregister.presentation.ui.screen.debt

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import angel.panduro.dev.walletregister.R
import angel.panduro.dev.walletregister.presentation.contract.debt.DebtEvent
import angel.panduro.dev.walletregister.presentation.ui.component.WalletEmptyState
import angel.panduro.dev.walletregister.presentation.ui.component.WalletModal
import angel.panduro.dev.walletregister.presentation.ui.component.WalletPieChart
import angel.panduro.dev.walletregister.presentation.ui.component.WalletTab
import angel.panduro.dev.walletregister.presentation.ui.component.WalletTopAppBar
import angel.panduro.dev.walletregister.presentation.ui.model.CategoryInformation
import angel.panduro.dev.walletregister.presentation.ui.model.DebtInformation
import angel.panduro.dev.walletregister.presentation.ui.theme.ContainerDarkColor
import angel.panduro.dev.walletregister.presentation.ui.theme.GreenTopBarColor
import angel.panduro.dev.walletregister.presentation.ui.theme.LabelStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.NotPaidStateColor
import angel.panduro.dev.walletregister.presentation.ui.theme.PaidStateColor
import angel.panduro.dev.walletregister.presentation.ui.theme.TitleStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.WalletIconColor
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY_ID
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.CategoriesEnum.Companion.getCategoriesByDescription
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DebtTapOptionEnum
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.formatNumber
import angel.panduro.dev.walletregister.presentation.viewmodel.DebtViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DebtScreen(
    debtViewModel: DebtViewModel = koinViewModel<DebtViewModel>(),
    onAddDebt: (Long) -> Unit,
    onDisplayDrawer: () -> Unit
){
    val scope = rememberCoroutineScope()
    val debtsTab = DebtTapOptionEnum.sectionsTitle
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { debtsTab.size })

    val cardState by debtViewModel.cardState.collectAsStateWithLifecycle()
    val debtsState by debtViewModel.debtsState.collectAsStateWithLifecycle()
    val modalState by debtViewModel.modalState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        debtViewModel.onEvent(DebtEvent.GetDebtAllInformation)
    }

    ModalDebtSection(
        showModal = modalState.showModal,
        isPaid = modalState.temporalDebt.first,
        onDeleteDebt = { debtViewModel.onEvent(DebtEvent.DeleteDebt) },
        onPaidQuote = { debtViewModel.onEvent(DebtEvent.PaidOneQuoteDebt) },
        onDismissModal = { debtViewModel.onEvent(DebtEvent.HideQuestionModal) }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            WalletTopAppBar(
                titleTopBar = stringResource(R.string.debt_title_top_bar),
                onDisplayDrawer = onDisplayDrawer
            )
        },
        floatingActionButton = {
            FABWallet(
                modifier = Modifier.padding(bottom = 16.dp, end = 8.dp),
                onAddDebt = { onAddDebt(cardState.idCardSelected) },
                enabled = cardState.idCardSelected != Long.EMPTY_ID
            )
        },
        containerColor = ContainerDarkColor
    ) { innerPadding ->
        WalletTab(
            modifier = Modifier.fillMaxWidth().padding(innerPadding),
            scope = scope,
            tabsList = debtsTab,
            pagerState = pagerState
        ){ page ->
            when(DebtTapOptionEnum.sections[page]){
                DebtTapOptionEnum.DEBT_NOT_PAID -> DebtNotPaidContent(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 8.dp),
                    debts = debtsState.debtNotPaid,
                    debtResume = debtsState.totalDebtByType,
                    onClick = { idDebt ->
                        debtViewModel.onEvent(DebtEvent.ShowQuestionModal(idDebt, false))
                    }
                )
                DebtTapOptionEnum.DEBT_PAID -> DebtPaidContent(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 8.dp),
                    debtsPaid = debtsState.debtPaid,
                    onClick = { idDebt ->
                        debtViewModel.onEvent(DebtEvent.ShowQuestionModal(idDebt, true))
                    }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ModalDebtSection(
    showModal: Boolean,
    isPaid: Boolean,
    onDeleteDebt: () -> Unit,
    onPaidQuote: () -> Unit,
    onDismissModal: () -> Unit
) {
    if (showModal && isPaid) {
        PaidDebtModal(
            onDeleteDebt = onDeleteDebt,
            onDismiss = onDismissModal
        )
    }

    if (showModal && !isPaid) {
        UnpaidDebtModal(
            onPaidQuote = onPaidQuote,
            onDeleteDebt = onDeleteDebt,
            onDismiss = onDismissModal
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaidDebtModal(
    onDeleteDebt: () -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    WalletModal(
        modifier = Modifier.fillMaxWidth(),
        sheetState = sheetState,
        icon = R.drawable.card,
        title = stringResource(R.string.title_option_debt_paid_modal),
        description = stringResource(R.string.description_debt_paid_option_modal),
        textPositive = stringResource(R.string.text_delete_option_modal),
        onPositiveClick = onDeleteDebt,
        onDismiss = onDismiss
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UnpaidDebtModal(
    onPaidQuote: () -> Unit,
    onDeleteDebt: () -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    WalletModal(
        modifier = Modifier.fillMaxWidth(),
        sheetState = sheetState,
        icon = R.drawable.card,
        title = stringResource(R.string.title_option_debt_modal),
        description = stringResource(R.string.description_debt_option_modal),
        textPositive = stringResource(R.string.text_paid_option_modal),
        textNegative = stringResource(R.string.text_delete_option_modal),
        onPositiveClick = onPaidQuote,
        onNegativeClick = onDeleteDebt,
        onDismiss = onDismiss
    )
}

@Composable
private fun DebtNotPaidContent(
    modifier: Modifier = Modifier,
    debts: List<DebtInformation>,
    debtResume: Map<String, CategoryInformation>,
    onClick: (Long) -> Unit
){
    if(debts.isEmpty()){
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            WalletEmptyState(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.empty_title_debt),
                description = stringResource(R.string.description_debt)
            )
        }
    } else {
        LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                WalletPieChart(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    data = debtResume,
                    chartBarWidth = 30.dp
                )
            }

            item {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = stringResource(R.string.title_debt_not_paid),
                    style = TitleStyle,
                    color = Color.White
                )
            }

            items(debts){ debt ->
                CardDebtItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onClick,
                    value = debt
                )
            }
        }
    }
}

@Composable
private fun DebtPaidContent(
    modifier: Modifier = Modifier,
    debtsPaid: List<DebtInformation>,
    onClick: (Long) -> Unit
){
    if(debtsPaid.isEmpty()){
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            WalletEmptyState(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.empty_title_debt),
                description = stringResource(R.string.description_debt_paid)
            )
        }
    } else {
        LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    text = stringResource(R.string.title_debt_paid),
                    style = TitleStyle,
                    color = Color.White
                )
            }

            items(debtsPaid){ debtPaid ->
                CardDebtItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onClick,
                    value = debtPaid
                )
            }
        }
    }
}

@Composable
private fun CardDebtItem(
    modifier: Modifier = Modifier,
    onClick: (Long) -> Unit,
    value: DebtInformation
) {
    Box(
        modifier = modifier.clickable { onClick(value.id) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_wallet),
                    contentDescription = "IconWallet",
                    tint = WalletIconColor
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.weight(1f),
                    text = value.nameCard,
                    style = LabelStyle,
                    color = Color.White
                )

                Text(
                    text = value.date,
                    color = Color.White,
                    style = LabelStyle
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                val category = getCategoriesByDescription(value.category)
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = ImageVector.vectorResource(category.icon),
                    contentDescription = category.name,
                    tint = category.color
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.weight(1f),
                    text = value.category,
                    style = LabelStyle,
                    color = Color.White
                )

                Text(
                    text = value.typeMoney + " -".takeIf { value.isPaid == 0 }.orEmpty() + value.debt.formatNumber(),
                    color = PaidStateColor.takeIf { value.isPaid == 1 } ?: NotPaidStateColor,
                    style = LabelStyle
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Fecha de pago: ${value.dateExpired}",
                    color = Color.White,
                    style = LabelStyle
                )

                Text(
                    text = if (value.quote > 1) {
                        "Cuotas: ${value.quotePaid}/${value.quote}"
                    } else {
                        "Directo"
                    },
                    color = Color.White,
                    style = LabelStyle
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

    }
}

@Composable
private fun FABWallet(
    modifier: Modifier = Modifier,
    onAddDebt: () -> Unit = {},
    enabled: Boolean = true
){
    FloatingActionButton(
        modifier = modifier,
        onClick = {
            if(enabled) onAddDebt()
        },
        containerColor = GreenTopBarColor.takeIf { enabled } ?: Color.LightGray,
        contentColor = Color.White
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Adddebts"
        )
    }
}
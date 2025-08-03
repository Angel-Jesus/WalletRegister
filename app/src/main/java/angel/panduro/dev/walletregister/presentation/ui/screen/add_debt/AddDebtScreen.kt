package angel.panduro.dev.walletregister.presentation.ui.screen.add_debt

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
import angel.panduro.dev.walletregister.R
import angel.panduro.dev.walletregister.presentation.contract.add_debt.AddDebtEffect
import angel.panduro.dev.walletregister.presentation.contract.add_debt.AddDebtEvent
import angel.panduro.dev.walletregister.presentation.ui.component.DropDownParameters
import angel.panduro.dev.walletregister.presentation.ui.component.WalletDropDown
import angel.panduro.dev.walletregister.presentation.ui.component.WalletInput
import angel.panduro.dev.walletregister.presentation.ui.theme.ContainerDarkColor
import angel.panduro.dev.walletregister.presentation.ui.theme.GreenTopBarColor
import angel.panduro.dev.walletregister.presentation.ui.theme.TitleStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.TitleTopAppBarStyle
import angel.panduro.dev.walletregister.presentation.ui.utils.constance.WarningMessage
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.CategoriesEnum
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DebtDropDownEnum
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DebtInputEnum
import angel.panduro.dev.walletregister.presentation.viewmodel.AddDebtViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddDebtScreen(
    addDebtViewModel: AddDebtViewModel = koinViewModel<AddDebtViewModel>(),
    idCard: Long,
    onBack: () -> Unit
){
    val scope = rememberCoroutineScope()
    val uiState by addDebtViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        addDebtViewModel.onEvent(AddDebtEvent.GetAllCards)

        addDebtViewModel.uiEffect.collectLatest { effect ->
            when(effect){
                is AddDebtEffect.FinishSaveDebt -> onBack()
                is AddDebtEffect.PendingFields -> scope.launch { snackbarHostState.showSnackbar(WarningMessage.PENDING_FIELD_DEBT) }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AddDebtTopBar(
                onSaveDebt = {
                    addDebtViewModel.onEvent(AddDebtEvent.SaveDebt(idCard))
                },
                onBack = onBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = ContainerDarkColor
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)){
            item {
                WalletDropDown(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = stringResource(R.string.acount_list_add_debt),
                    data = uiState.cardAccounts.map { DropDownParameters(icon = R.drawable.icon_wallet, text = it.nameCard, color = Color(it.colorCard)) },
                    value = DropDownParameters(icon = R.drawable.icon_wallet.takeIf { uiState.account.isNotEmpty() }, text = uiState.account, color = uiState.accountColor),
                    valueSelected = { account ->
                        addDebtViewModel.onEvent(AddDebtEvent.UpdateDropDownValue(account, DebtDropDownEnum.ACCOUNT))
                    }
                )
            }

            item {
                WalletInput(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = stringResource(R.string.amount_add_debt),
                    text = uiState.amount,
                    valueChanged = { amount ->
                        addDebtViewModel.onEvent(AddDebtEvent.UpdateInputValue(amount, DebtInputEnum.AMOUNT))
                    },
                    isNumeric = true
                )
            }

            item {
                WalletInput(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = stringResource(R.string.quote_add_debt),
                    text = uiState.quote,
                    valueChanged = { quote ->
                        addDebtViewModel.onEvent(AddDebtEvent.UpdateInputValue(quote, DebtInputEnum.QUOTE))
                    },
                    isNumeric = true
                )
            }

            item {
                WalletDropDown(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = stringResource(R.string.category_add_debt),
                    data = CategoriesEnum.options.map { DropDownParameters(icon = it.icon, text = it.description, color = it.color) },
                    value = DropDownParameters(icon = CategoriesEnum.getIconByDescription(uiState.category), text = uiState.category, color = CategoriesEnum.getColorByDescription(uiState.category)),
                    valueSelected = { category ->
                        addDebtViewModel.onEvent(AddDebtEvent.UpdateDropDownValue(category, DebtDropDownEnum.CATEGORY))
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddDebtTopBar(
    onSaveDebt: () -> Unit = {},
    onBack: () -> Unit = {}
){
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.title_add_debt),
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
                onClick = onSaveDebt
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
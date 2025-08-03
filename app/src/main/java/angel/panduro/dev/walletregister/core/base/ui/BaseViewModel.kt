package angel.panduro.dev.walletregister.core.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angel.panduro.dev.walletregister.core.base.usecase.BaseFlowUseCase
import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S: BaseUiState, E: BaseEvent, F: BaseEffect>(
    initialState: S
): ViewModel() {

    // Ui State
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    // Ui Effect
    private val _uiEffect = Channel<F>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    // Ui Event
    abstract fun onEvent(event: E)

    // Send Effect to Channel
    protected fun sendEffect(effetc: F){
        viewModelScope.launch {
            _uiEffect.send(effetc)
        }
    }

    // Update Ui State
    protected fun updateState(newState: S.() -> S){
        viewModelScope.launch {
            _uiState.update {
                it.newState()
            }
        }
    }

    // Execute UseCase
    protected fun <Params, Result> executeUseCase(
        useCase: BaseUseCase<Params, Result>,
        params: Params,
        onResult: suspend (Result) -> Unit = {},
    ){
        viewModelScope.launch {
            onResult(useCase.execute(params))
        }
    }

    // Execute UseCase with Job
    protected fun <Params, Result> executeJobUseCase(
        useCase: BaseFlowUseCase<Params, Result>,
        params: Params,
        onResult: suspend (Result) -> Unit,
    ): Job{
        return useCase.execute(params)
            .onEach { result ->
                onResult(result)
            }.launchIn(viewModelScope)
    }

    // Cancel ViewModelScope
    protected fun clearScope(){
        viewModelScope.cancel()
    }
}
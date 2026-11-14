package Mobile-Application-Programming-Project-

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SpreadViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SpreadUiState())
    val uiState: StateFlow<SpreadUiState> = _uiState.asStateFlow()
}

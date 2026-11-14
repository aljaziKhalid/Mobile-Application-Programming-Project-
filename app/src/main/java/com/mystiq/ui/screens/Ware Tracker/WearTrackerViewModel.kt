package Mobile-Application-Programming-Project-

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WearTrackerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WearTrackerUiState())
    val uiState: StateFlow<WearTrackerUiState> = _uiState.asStateFlow()
}
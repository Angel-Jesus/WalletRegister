package angel.panduro.dev.walletregister

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import angel.panduro.dev.walletregister.presentation.ui.navigation.NavManager
import angel.panduro.dev.walletregister.presentation.ui.theme.WalletRegisterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WalletRegisterTheme {
                NavManager()
            }
        }
    }
}

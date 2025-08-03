package angel.panduro.dev.walletregister.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import angel.panduro.dev.walletregister.R
import angel.panduro.dev.walletregister.presentation.ui.theme.EmptyTitleStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.SubtitleMediumStyle

@Composable
fun WalletEmptyState(
    modifier: Modifier = Modifier,
    title: String,
    description: String
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(bottom = 5.dp)
                .size(220.dp),
            painter = painterResource(id = R.drawable.empty_state),
            contentDescription = "Logo App"
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            textAlign = TextAlign.Center,
            style = EmptyTitleStyle
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description,
            style = SubtitleMediumStyle,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

    }
}
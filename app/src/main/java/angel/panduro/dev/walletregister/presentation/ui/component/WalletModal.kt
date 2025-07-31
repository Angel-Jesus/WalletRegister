package angel.panduro.dev.walletregister.presentation.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import angel.panduro.dev.walletregister.presentation.ui.theme.SubtitleRegularStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.TitleStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletModal(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    @DrawableRes icon: Int? = null,
    title: String,
    description: String? = null,
    textPositive: String,
    textNegative: String,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    onDismiss: () -> Unit
){
    ModalBottomSheet(
        onDismissRequest = { onDismiss },
        sheetState = sheetState,
    ) {
        Column(modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            icon?.let {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = icon),
                    contentDescription = "iconModal"
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = TitleStyle,
                textAlign = TextAlign.Center
            )

            description?.let {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = description,
                    style = SubtitleRegularStyle,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                onClick = onPositiveClick
            ){
                Text(
                    text = textPositive,
                    style = SubtitleRegularStyle
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                onClick = onNegativeClick
            ){
                Text(
                    text = textNegative,
                    style = SubtitleRegularStyle
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
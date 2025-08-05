package angel.panduro.dev.walletregister.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import angel.panduro.dev.walletregister.presentation.ui.theme.DescriptionStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.ErrorIconColor
import angel.panduro.dev.walletregister.presentation.ui.theme.ErrorTextColor
import angel.panduro.dev.walletregister.presentation.ui.theme.GreenTopBarColor
import angel.panduro.dev.walletregister.presentation.ui.theme.LabelStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.notoSansFamily
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY

@Composable
fun WalletInput(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    valueChanged: (String) -> Unit,
    errorMessage: String = String.EMPTY,
    isError: Boolean = false,
    isNumeric: Boolean = false
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = LabelStyle,
            color = Color.White
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontFamily = notoSansFamily, fontSize = 14.sp),
            value = text,
            onValueChange = {
                if(isNumeric){
                    if(it.isBlank() || it.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) valueChanged(it)
                } else {
                    valueChanged(it)
                }
            },
            supportingText = if(isError){
                {
                    Text(
                        text = errorMessage,
                        style = DescriptionStyle,
                        color = ErrorTextColor
                    )
                }
            } else null,
            suffix = if(isError){
                {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = ErrorIconColor
                    )
                }
            } else null,
            isError = isError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number.takeIf { isNumeric } ?: KeyboardType.Text),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color.Gray,
                unfocusedContainerColor = Color.Transparent,
                errorTextColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}
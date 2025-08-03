package angel.panduro.dev.walletregister.presentation.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import angel.panduro.dev.walletregister.presentation.ui.theme.ContainerBlockColor
import angel.panduro.dev.walletregister.presentation.ui.theme.GreenTopBarColor
import angel.panduro.dev.walletregister.presentation.ui.theme.LabelStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.LabelXStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.notoSansFamily
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY

data class DropDownParameters(
    @DrawableRes val icon: Int? = null,
    val text: String,
    val color: Color
)

@Composable
fun WalletDropDown(
    modifier: Modifier = Modifier,
    label: String,
    data: List<DropDownParameters>,
    value: DropDownParameters,
    valueSelected: (String) -> Unit
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

        DropDown(
            data = data,
            value = value,
            valueSelected = valueSelected
        )
    }
}

@Composable
private fun DropDown(
    modifier: Modifier = Modifier,
    data: List<DropDownParameters>,
    value: DropDownParameters,
    valueSelected: (String) -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    var textFiledWidth by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    Box(modifier = modifier.fillMaxWidth()){
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFiledWidth = coordinates.size.width
                }
                .clickable { expanded = !expanded },
            textStyle = TextStyle(fontFamily = notoSansFamily, fontSize = 14.sp),
            value = String.EMPTY,
            onValueChange = {},
            enabled = false,
            label = {
                Row {
                    value.icon?.let {
                        Icon(
                            modifier = Modifier
                                .drawBehind {
                                    drawCircle(
                                        color = value.color,
                                        radius = this.size.maxDimension - 4f
                                    )
                                }
                                .size(18.dp),
                            imageVector = ImageVector.vectorResource(value.icon),
                            contentDescription = "${value.text}Icon",
                            tint = Color.White
                        )

                        Spacer(modifier = Modifier.width(14.dp))
                    }

                    Text(
                        text = value.text,
                        style = LabelXStyle,
                        color = Color.White
                    )
                }
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.rotate(180f.takeIf { expanded } ?: 0f).clickable { expanded = !expanded },
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "DropDownColorIcon",
                    tint = Color.White
                )
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GreenTopBarColor,
                disabledBorderColor = Color.LightGray,
                unfocusedContainerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(density) { textFiledWidth.toDp() })
                .align(Alignment.TopStart),
            containerColor = ContainerBlockColor
        ) {
            data.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Row {
                            item.icon?.let {
                                Icon(
                                    modifier = Modifier
                                        .drawBehind {
                                            drawCircle(
                                                color = item.color,
                                                radius = this.size.maxDimension - 4f
                                            )
                                        }
                                        .size(18.dp),
                                    imageVector = ImageVector.vectorResource(item.icon),
                                    contentDescription = "${item.text}IconMenu",
                                    tint = Color.White
                                )

                                Spacer(modifier = Modifier.width(14.dp))
                            }

                            Text(
                                text = item.text,
                                style = LabelXStyle,
                                color = Color.White
                            )
                        }
                    },
                    onClick = {
                        expanded = false
                        valueSelected(item.text)
                    }
                )
            }
        }
    }
}
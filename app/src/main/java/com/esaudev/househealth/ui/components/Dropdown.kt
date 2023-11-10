package com.esaudev.househealth.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.esaudev.househealth.R
import com.esaudev.househealth.ui.theme.HouseHealthTheme

data class ExposedDropdownOption<T>(
    val labelToShow: String,
    val optionObject: T
)

fun <T> T.toExposedDropdownOption(
    labelToShow: String
): ExposedDropdownOption<T> {
    return ExposedDropdownOption(
        labelToShow = labelToShow,
        optionObject = this
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> ExposedDropdownMenu(
    options: List<ExposedDropdownOption<T>>,
    selectedOption: ExposedDropdownOption<T>?,
    onOptionSelected: (ExposedDropdownOption<T>) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedOption?.labelToShow.orEmpty(),
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(
                        text = stringResource(id = R.string.service_type_label),
                        style = MaterialTheme.typography.body1
                    )
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(50),
                textStyle = MaterialTheme.typography.body1,
                singleLine = true
            )

            ExposedDropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { item ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            Text(
                                text = item.labelToShow,
                                style = MaterialTheme.typography.body1
                            )
                        },
                        onClick = {
                            onOptionSelected(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

private class SelectedOptionProvider : PreviewParameterProvider<ExposedDropdownOption<String>?> {
    override val values: Sequence<ExposedDropdownOption<String>?>
        get() = sequenceOf(
            ExposedDropdownOption(
                labelToShow = "Option selected",
                optionObject = ""
            ),
            null
        )
}

@Composable
@Preview
private fun ExposedDropdownMenuPreview(
    @PreviewParameter(SelectedOptionProvider::class) providerSelectedOption: ExposedDropdownOption<String>
) {
    HouseHealthTheme {
        Surface {
            val context = LocalContext.current

            ExposedDropdownMenu(
                options = listOf(
                    ExposedDropdownOption(
                        labelToShow = "Esau",
                        optionObject = ""
                    ),
                    ExposedDropdownOption(
                        labelToShow = "Willys",
                        optionObject = ""
                    ),
                    ExposedDropdownOption(
                        labelToShow = "Wilfrido",
                        optionObject = ""
                    )
                ),
                selectedOption = providerSelectedOption,
                onOptionSelected = {
                    Toast.makeText(context, it.labelToShow, Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            )
        }
    }
}
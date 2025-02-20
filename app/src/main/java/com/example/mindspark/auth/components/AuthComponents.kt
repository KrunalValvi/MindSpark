package com.example.mindspark.auth.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.auth.ui.register.FillProfileScreen
import com.example.mindspark.ui.theme.customTypography

@Composable
fun OTPInputFields(
    otpLength: Int,
    onUpdateOtpValuesByIndex: (Int, String) -> Unit,
    onOtpInputComplete: () -> Unit,
    modifier: Modifier = Modifier,
    otpValues: List<String> = List(otpLength) { "" },
    isError: Boolean = false,
) {
    val focusRequesters = List(otpLength) { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        otpValues.forEachIndexed { index, value ->
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(6.dp)
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent { keyEvent ->
                        if (keyEvent.key == Key.Backspace) {
                            if (otpValues[index].isEmpty() && index > 0) {
                                onUpdateOtpValuesByIndex(index, "")
                                focusRequesters[index - 1].requestFocus()
                            } else {
                                onUpdateOtpValuesByIndex(index, "")
                            }
                            true
                        } else {
                            false
                        }
                    }
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(15.dp),
                        spotColor = Color(0x1A000000),
                        ambientColor = Color(0x1A000000)
                    ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color(0xFF1565C0)
                ),
                value = value,
                onValueChange = { newValue ->
                    if (newValue.length == otpLength) {
                        for (i in otpValues.indices) {
                            onUpdateOtpValuesByIndex(
                                i,
                                if (i < newValue.length && newValue[i].isDigit()) newValue[i].toString() else ""
                            )
                        }

                        keyboardController?.hide()
                        onOtpInputComplete()
                    } else if (newValue.length <= 1) {
                        onUpdateOtpValuesByIndex(index, newValue)
                        if (newValue.isNotEmpty()) {
                            if (index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            } else {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                onOtpInputComplete()
                            }
                        }
                    } else {
                        if (index < otpLength - 1) focusRequesters[index + 1].requestFocus()
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (index == otpLength - 1) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        if (index < otpLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }
                    },
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        onOtpInputComplete()
                    }
                ),
                shape = MaterialTheme.shapes.small,
                isError = isError,
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    color = Color(0xFF505050),
                    fontSize = 22.sp
                )
            )

            LaunchedEffect(value) {
                if (otpValues.all { it.isNotEmpty() }) {
                    focusManager.clearFocus()
                    onOtpInputComplete()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequesters.first().requestFocus()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTopBar(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 21.sp,
                color = Color(0xFF202244)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF202244)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF5F9FF)
        )
    )
}

@Composable
fun GenderDropdown(
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val genders = listOf("Male", "Female", "Other")
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "dropdown_rotation"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(15.dp),
                    spotColor = Color(0x1A000000),
                    ambientColor = Color(0x1A000000)
                )
                .background(Color.White, RoundedCornerShape(15.dp))
                .border(
                    width = 1.dp,
                    color = if (expanded) Color(0xFF1565C0) else Color.Transparent,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Gender Icon",
                        tint = Color.Gray,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Text(
                        text = if (selectedGender.isEmpty()) "Gender" else selectedGender,
                        style = MaterialTheme.customTypography.mulish.regular,
                        fontSize = 16.sp,
                        color = if (selectedGender.isEmpty()) Color.Gray else Color(0xFF1A1A1A)
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown Arrow",
                    tint = Color(0xFF1565C0),
                    modifier = Modifier.rotate(rotationState)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) {
                    (LocalConfiguration.current.screenWidthDp - 48).dp
                })
                .background(Color.White, RoundedCornerShape(12.dp))
                .shadow(4.dp, RoundedCornerShape(12.dp))
        ) {
            genders.forEach { gender ->
                DropdownMenuItem(
                    onClick = {
                        onGenderSelected(gender)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = gender,
                            style = MaterialTheme.customTypography.mulish.regular,
                            fontSize = 14.sp,
                            color = if (gender == selectedGender) Color(0xFF1565C0) else Color.Black
                        )
                    },
                    leadingIcon = if (gender == selectedGender) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = Color(0xFF1565C0),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    } else null,
                    modifier = Modifier.background(
                        if (gender == selectedGender) Color(0xFFF5F9FF) else Color.White
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FillProfileScreenPreview() {
    GenderDropdown(selectedGender = "", onGenderSelected = {})
}
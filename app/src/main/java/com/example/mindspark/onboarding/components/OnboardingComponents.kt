package com.example.mindspark.onboarding.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.onboarding.model.OnboardingScreenContent
import com.example.mindspark.ui.theme.customTypography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    content: OnboardingScreenContent,
    onSkip: () -> Unit,
    onNext: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val backgroundColor = Color(0xFFF5F9FF)
    val primaryColor = Color(0xFF335EEA)
    val textColor = Color(0xFF1A1A1A)
    val secondaryTextColor = Color(0xFF6B7280)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = (screenWidth * 0.05f),
                    end = (screenWidth * 0.05f),
                    top = (screenHeight * 0.03f),
                    bottom = (screenHeight * 0.03f)
                )
        ) {
            // Skip Button
            SkipButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = (screenHeight * 0.02f),
                        end = (screenWidth * 0.02f)
                    ),
                onSkip = onSkip,
                textColor = Color.Black
            )

            // Content
            OnboardingContent(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(
                        horizontal = (screenWidth * 0.05f),
                        vertical = (screenHeight * 0.05f)
                    ),
                title = content.title,
                description = content.description,
                titleColor = textColor,
                descriptionColor = secondaryTextColor,
                screenHeight = screenHeight
            )

            // Navigation Controls
            NavigationControls(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = (screenHeight * 0.03f))
                    .fillMaxWidth(),
                currentStep = content.currentStep,
                onNext = onNext,
                primaryColor = primaryColor,
                secondaryColor = Color.LightGray,
                screenWidth = screenWidth,
                screenHeight = screenHeight
            )
        }
    }
}

@Composable
fun SkipButton(
    modifier: Modifier = Modifier,
    onSkip: () -> Unit,
    textColor: Color
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Text(
        text = "Skip",
        fontSize = (screenHeight * 0.02f).value.sp,
        style = MaterialTheme.customTypography.jost.semiBold,
        color = textColor,
        modifier = modifier.clickable(onClick = onSkip)
    )
}


@Composable
fun OnboardingContent(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    titleColor: Color,
    descriptionColor: Color,
    screenHeight: androidx.compose.ui.unit.Dp
) {
    var isVisible by remember { mutableStateOf(false) }
    val slideOffset = remember { Animatable(initialValue = 100f) }
    val titleAlpha = remember { Animatable(initialValue = 0f) }
    val descriptionAlpha = remember { Animatable(initialValue = 0f) }

    // Trigger animations when the component is first composed
    LaunchedEffect(Unit) {
        isVisible = true
        launch {
            slideOffset.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = EaseOutQuart
                )
            )
        }
        launch {
            titleAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = EaseOutCirc
                )
            )
        }
        // Slightly delayed animation for description
        launch {
            delay(300) // Add a small delay for staggered effect
            descriptionAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = EaseOutCirc
                )
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 390.dp)
            .offset(y = slideOffset.value.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            style = MaterialTheme.customTypography.jost.semiBold,
            textAlign = TextAlign.Center,
            color = titleColor,
            modifier = Modifier
                .graphicsLayer {
                    alpha = titleAlpha.value
                    scaleX = 1f + (titleAlpha.value * 0.1f)
                    scaleY = 1f + (titleAlpha.value * 0.1f)
                }
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = description,
            fontSize = 14.sp,
            style = MaterialTheme.customTypography.mulish.bold,
            textAlign = TextAlign.Center,
            color = descriptionColor,
            modifier = Modifier
                .graphicsLayer {
                    alpha = descriptionAlpha.value
                }
        )
    }
}

@Composable
fun NavigationControls(
    modifier: Modifier = Modifier,
    currentStep: Int,
    onNext: () -> Unit,
    primaryColor: Color,
    secondaryColor: Color,
    screenWidth: androidx.compose.ui.unit.Dp,
    screenHeight: androidx.compose.ui.unit.Dp
) {
    Row(
        modifier = modifier.padding(bottom = screenHeight * 0.03f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PageIndicator(currentStep = currentStep, primaryColor = primaryColor, secondaryColor = secondaryColor, screenWidth = screenWidth)
        NextButton(onClick = onNext, primaryColor = primaryColor, screenHeight = screenHeight)
    }
}

@Composable
fun PageIndicator(
    currentStep: Int,
    primaryColor: Color,
    secondaryColor: Color,
    screenWidth: androidx.compose.ui.unit.Dp
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.02f)
    ) {
        repeat(3) { step ->
            Box(
                modifier = Modifier
                    .width(
                        if (step == currentStep - 1)
                            screenWidth * 0.06f else screenWidth * 0.03f
                    )
                    .height(screenWidth * 0.03f)
                    .background(
                        if (step == currentStep - 1) primaryColor else secondaryColor,
                        CircleShape
                    )
            )
        }
    }
}

@Composable
fun NextButton(
    onClick: () -> Unit,
    primaryColor: Color,
    screenHeight: androidx.compose.ui.unit.Dp
) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(onClick) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 100,
                easing = FastOutSlowInEasing
            )
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 100,
                easing = FastOutSlowInEasing
            )
        )
    }

    Box(
        modifier = Modifier
            .size(screenHeight * 0.07f)
            .clip(CircleShape)
            .background(primaryColor)
            .clickable(onClick = onClick)
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Next",
            tint = Color.White,
            modifier = Modifier.size(screenHeight * 0.035f)
        )
    }
}

// Add these custom easing curves
private val EaseOutQuart = CubicBezierEasing(0.25f, 1f, 0.5f, 1f)
private val EaseOutCirc = CubicBezierEasing(0f, 0.55f, 0.45f, 1f)
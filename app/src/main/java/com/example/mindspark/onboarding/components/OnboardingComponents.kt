package com.example.mindspark.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindspark.onboarding.model.OnboardingScreenContent
import com.example.mindspark.ui.theme.customTypography

@Composable
fun OnboardingScreen(
    content: OnboardingScreenContent,
    onSkip: () -> Unit,
    onNext: () -> Unit
) {
    Box(modifier = Modifier.background(Color(0xFFF5F9FF))) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            SkipButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 30.dp, end = 10.dp),
                onSkip = onSkip
            )

            OnboardingContent(
                modifier = Modifier.align(Alignment.Center),
                title = content.title,
                description = content.description
            )

            NavigationControls(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp, start = 12.dp, end = 12.dp)
                    .fillMaxWidth(),
                currentStep = content.currentStep,
                onNext = onNext
            )
        }
    }
}

@Composable
fun SkipButton(
    modifier: Modifier = Modifier,
    onSkip: () -> Unit
) {
    Text(
        text = "Skip",
        fontSize = 16.sp,
        style = MaterialTheme.customTypography.jost.semiBold,
        modifier = modifier.clickable(onClick = onSkip)
    )
}

@Composable
fun OnboardingContent(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Column(
        modifier = modifier.padding(top = 200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            style = MaterialTheme.customTypography.jost.semiBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = description,
            fontSize = 14.sp,
            style = MaterialTheme.customTypography.mulish.bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NavigationControls(
    modifier: Modifier = Modifier,
    currentStep: Int,
    onNext: () -> Unit
) {
    Row(
        modifier = modifier.padding(bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PageIndicator(currentStep = currentStep)
        NextButton(onClick = onNext)
    }
}

@Composable
fun PageIndicator(currentStep: Int) {
    Row {
        repeat(3) { step ->
            Box(
                modifier = Modifier
                    .width(if (step == currentStep - 1) 25.dp else 12.dp)
                    .size(12.dp)
                    .background(
                        if (step == currentStep - 1) Color(0xFF335EEA) else Color.LightGray,
                        CircleShape
                    )
            )
            if (step < 2) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun NextButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(Color(0xFF1565C0))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Next",
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview(){
    PageIndicator(currentStep = 1)
}
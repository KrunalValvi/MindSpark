@file:Suppress("UNREACHABLE_CODE")

package com.example.mindspark.courses.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.home.model.CourseModel
import com.example.mindspark.ui.theme.customTypography

@Composable
fun CourseDetailCard() {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .width(250.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Row() {  // Category Text
                Text(
                    text = "Graphic Design",
                    color = Color(0xFFFF5722), // Orange color
                    style = MaterialTheme.customTypography.mulish.bold,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "* 4.2 ",
                    style = MaterialTheme.customTypography.mulish.extraBold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Text(
                text = "Design Principles: Organizing ..",
                style = MaterialTheme.customTypography.jost.semiBold,
                color = Color.Black,
                fontSize = 20.sp
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun CourseDetailCardPreview() {
    CourseDetailCard()
}
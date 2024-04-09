package com.parade621.compose_permission_sample.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LazyColumnItem(
    checked: Boolean = false,
    checkBoxEvent: () -> Unit = {},
    permissionText: String,
    btnClickEvent: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Checkbox(checked = checked, onCheckedChange = { checkBoxEvent() })
        Box(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = permissionText,
                fontSize = 14.sp
            )
        }
        Button(modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults
                .buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.White
                ),
            // 모서리 둥글게 설정
            shape = RoundedCornerShape(16.dp),
            onClick = { btnClickEvent() }) {
            Text(text = "Request")
        }
    }
}

@Preview
@Composable
fun LazyColumnItemPreView() {
    LazyColumnItem(permissionText = "Permission Text Here") {}
}
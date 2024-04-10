package com.parade621.compose_permission_sample.ui.screen.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parade621.compose_permission_sample.ui.components.LazyColumnItem
import com.parade621.compose_permission_sample.ui.theme.Compose_Permission_SampleTheme

@Composable
fun MainScreen(
    state: MainState,
    onEvent: (MainEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Permission Check",
                fontSize = 20.sp
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = state.checkAll,
                    onCheckedChange = { onEvent(MainEvent.OnCheckAllClicked) }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Select All", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Button(modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults
                    .buttonColors(
                        containerColor = Color.LightGray,
                        contentColor = Color.White
                    ),
                // 모서리 둥글게 설정
                shape = RoundedCornerShape(16.dp),
                onClick = { onEvent(MainEvent.RequestMultiplePermission) }) {
                Text(text = "Request")
            }
        }
        Divider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        LazyColumn {
            itemsIndexed(state.permissionList) { index, permission ->
                LazyColumnItem(
                    checked = permission.isChecked,
                    checkBoxEvent = { onEvent(MainEvent.OnCheckPermissionClicked(index)) },
                    permissionText = permission.permissionText.replace("android.permission.", ""),
                ) {
                    onEvent(MainEvent.RequestSinglePermission(index))
                }
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Compose_Permission_SampleTheme {
        MainScreen(MainState()) {

        }
    }
}
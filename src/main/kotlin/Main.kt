import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Toolkit

// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.


@Preview
@Composable
fun app() {
    val count = remember { mutableStateOf(0) }
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    count.value++
                }) {
                Text(if (count.value == 0) "Hello World" else "Clicked ${count.value}!")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    count.value = 0
                }) {
                Text("Reset")
            }
        }
    }
}


fun main() = application {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val width = 800
    val height = 600
    val windowPosition = WindowPosition(((screenSize.width - width) / 2).dp, ((screenSize.height - height) / 2).dp)
//    GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices[0]
    Window(
        onCloseRequest = ::exitApplication,
        resizable = false,
        title = "hi, this is a new app",
        state = rememberWindowState(width = width.dp, height = height.dp, position = windowPosition)
    ) {
        MaterialTheme {
            Column(Modifier.fillMaxSize(), Arrangement.Center) {
                PressIconButton(
                    onClick = {},
                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = null) },
                    text = { Text("Add to cart") },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}


@Composable
fun PressIconButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,

    interactionSource: MutableInteractionSource =
        remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    Button(
        onClick = onClick, modifier = modifier,
        interactionSource = interactionSource
    ) {
        AnimatedVisibility(visible = isPressed) {
            if (isPressed) {
                Row {
                    icon()
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                }
            }
        }
        text()
    }
}

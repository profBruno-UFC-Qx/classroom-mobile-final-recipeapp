package com.example.recipeapp.ui.components

import android.R
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun HeaderComponent(
    tittle: String,
    @DrawableRes leftIcon: Int,
    @DrawableRes rightIcon: Int,
    isLogo: Boolean? = false,
    onLeftClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .zIndex(2f)
            .padding(horizontal = 20.dp)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(leftIcon),
                contentDescription = "Icone Esquerdo",
                modifier = Modifier.size(38.dp).graphicsLayer(colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.onPrimary, blendMode = BlendMode.SrcIn))
                    .clickable{onLeftClick?.invoke()}
            )

            Text(
                text = tittle,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 32.sp
            )
            if(isLogo == true) {
                Image(
                    painter = painterResource(rightIcon),
                    contentDescription = "Icone Logo",
                    modifier = Modifier.size(38.dp)
                )
            } else {
                Image(
                    painter = painterResource(rightIcon),
                    contentDescription = "Icone Logo",
                    modifier = Modifier.size(38.dp).graphicsLayer(colorFilter = ColorFilter.tint(
                        MaterialTheme.colorScheme.onPrimary, blendMode = BlendMode.SrcIn))
                )
            }
        }
    }
}
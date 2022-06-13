package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.R

@Preview(showSystemUi = true)
@Composable
fun ImageComposeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {
        ResourceImage()
        ImageVectorImage()
        BitmapImage()
        Row(modifier = Modifier.horizontalScroll(state = rememberScrollState())) {
            CircularImage()
            RoundedCornerImage()
            ImageWithBorder()
        }
        ContentScaleImage()
        MirrorImage()
    }
}

@Composable
fun MirrorImage() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Mirror Image",color = MaterialTheme.colors.onSurface)
        Mirror {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .clip(shape = RoundedCornerShape(24.dp)),
                painter = painterResource(id = R.drawable.hl3),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun Mirror(content: @Composable () -> Unit) {
    Column {
        content()
        Box(modifier = Modifier
            .graphicsLayer {   //rotation & alpha
                alpha = 0.99f
                rotationZ = 180f
            }
            /*.drawWithContent {
                val colors = listOf(Color.Transparent, Color.White)
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(colors),
                    blendMode = BlendMode.DstIn
                )
            }*/
            //blur
            .blur(
                radiusX = 1.dp,
                radiusY = 3.dp,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            )
            //half size image
            .clip(
                shape = HalfSizeShape
            )
        ) {
            content()
        }
    }
}

object HalfSizeShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = Outline.Rectangle(
        Rect(
            offset = Offset(x = 0f, y = size.height / 2),
            size = Size(width = size.width, height = size.height)
        )
    )
}

@Composable
fun ResourceImage() {

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
    ) {
        Text(
            text = "Image (from Resource) with full dimensions",
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(painter = painterResource(id = R.drawable.hl1), contentDescription = null)
    }

}

@Composable
fun ImageVectorImage() {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
    ) {
        Text(
            text = "Image (from ImageVector) with full dimensions",
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.width(16.dp))
        Row {
            Text(text = "Icons.Default", color = MaterialTheme.colors.onSurface)
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                imageVector = Icons.Default.AddAPhoto,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row {
            Text(text = "Icons.Filled", color = MaterialTheme.colors.onSurface)
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                imageVector = Icons.Filled.AddAPhoto,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row {
            Text(text = "Icons.Outlined", color = MaterialTheme.colors.onSurface)
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                imageVector = Icons.Outlined.AddAPhoto,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface)
            )
        }
    }
}

@Composable
fun BitmapImage() {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
    ) {
        Text(
            text = "Image (with bitmap.bmp extension) with full dimensions\nwe can provide bitmap also",
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.actual_bitmap_image),
            contentDescription = null
        )
    }
}

@Composable
fun CircularImage(text: String = "Circle Image", contentScale: ContentScale = ContentScale.Crop) {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.hl3), contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .align(alignment = Alignment.CenterHorizontally)
                .clip(shape = CircleShape), contentScale = contentScale
        )
    }
}

@Composable
fun RoundedCornerImage() {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Text(
            text = "Rounded Corner Shape Image",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.hl3), contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .align(alignment = Alignment.CenterHorizontally)
                .clip(shape = RoundedCornerShape(10.dp)), contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ImageWithBorder() {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Text(
            text = "Image with Border",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.hl3), contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .align(alignment = Alignment.CenterHorizontally)
                .border(
                    width = 5.dp,
                    color = MaterialTheme.colors.onSurface,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}

@Composable
fun ContentScaleImage() {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Text(
            text = "Image with different content Scale",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.horizontalScroll(state = rememberScrollState())) {
            CircularImage(text = "Crop", contentScale = ContentScale.Crop)
            CircularImage(text = "Fit", contentScale = ContentScale.Fit)
            CircularImage(text = "FillBounds", contentScale = ContentScale.FillBounds)
            CircularImage(text = "Inside", contentScale = ContentScale.Inside)
            CircularImage(text = "FillHeight", contentScale = ContentScale.FillHeight)
            CircularImage(text = "FillWidth", contentScale = ContentScale.FillWidth)
        }
    }
}


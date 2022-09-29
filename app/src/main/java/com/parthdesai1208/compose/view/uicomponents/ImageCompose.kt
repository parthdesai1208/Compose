package com.parthdesai1208.compose.view.uicomponents

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.view.animation.getScreenWidth
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.palette.BitmapPalette

@Composable
fun ImageComposeScreen() {
    Surface {
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
            ImageLoadingUsingGlide()
            ImageLoadingUsingLandscapistGlide()
            ImageLoadingUsingLandscapistCoil()
        }
    }
}

//region Landscapist-coil
@Composable
fun ImageLoadingUsingLandscapistCoil() {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = "Using Coil(landscapist-coil)",
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Row(
            modifier = Modifier
                .horizontalScroll(state = rememberScrollState())
                .padding(top = 24.dp)
        ) {
            LoadImageUsingLandscapistCoilURL()
            LoadGifUsingLandscapistCoil()
        }
    }
}

@Composable
fun LoadGifUsingLandscapistCoil() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(text = "Load Gif")
        val context = LocalContext.current
        val imageLoader = ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(coil.decode.GifDecoder.Factory())
                }
            }
            .build()
        CoilImage(
            imageModel = "https://media2.giphy.com/media/aQYR1p8saOQla/giphy.gif?cid=ecf05e4701sln9u63lr3z17lh5f3n3h3owrk54zh1183hqmi&rid=giphy.gif&ct=g",
            imageLoader = { imageLoader },
            modifier = Modifier
                .width(width = getScreenWidth().dp - 30.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )
    }
}

@Composable
fun LoadImageUsingLandscapistCoilURL() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(text = "Using URL")
        CoilImage(
            imageModel = "https://user-images.githubusercontent.com/24237865/75087934-5a53dc00-553e-11ea-94f1-494c1c68a574.jpg",
            modifier = Modifier
                .height(450.dp)
                .width(getScreenWidth().dp - 30.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
            // shows a placeholder ImageBitmap when loading.
            placeHolder = painterResource(R.drawable.hl1),
            // shows an error ImageBitmap when the request failed.
            error = painterResource(id = R.drawable.hl3),
            //for preview only
            previewPlaceholder = R.drawable.hl1,
            contentScale = ContentScale.FillBounds,
            bitmapPalette = BitmapPalette {
                //it.paletteColorList() will get List<Int> color from image
            },
        )
    }
}


//endregion

//region Landscapist-Glide
@Composable
fun ImageLoadingUsingLandscapistGlide() {
    Column {
        Text(
            text = "Using Glide(landscapist-glide)",
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Row(
            modifier = Modifier
                .horizontalScroll(state = rememberScrollState())
                .padding(top = 24.dp)
        ) {
            LoadImageUsingLandscapistGlideURL()
            LoadGifUsingLandscapistGlide()
        }
    }
}

@Composable
fun LoadGifUsingLandscapistGlide() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(text = "Load Gif")
        val context = LocalContext.current
        val requestBuilder = Glide.with(context).asDrawable()
            .load("https://media2.giphy.com/media/aQYR1p8saOQla/giphy.gif?cid=ecf05e4701sln9u63lr3z17lh5f3n3h3owrk54zh1183hqmi&rid=giphy.gif&ct=g")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        GlideImage(
            imageModel = "https://media2.giphy.com/media/aQYR1p8saOQla/giphy.gif?cid=ecf05e4701sln9u63lr3z17lh5f3n3h3owrk54zh1183hqmi&rid=giphy.gif&ct=g",
            requestBuilder = { requestBuilder },
            modifier = Modifier
                .width(width = getScreenWidth().dp - 30.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )
    }
}

@Composable
fun LoadImageUsingLandscapistGlideURL() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(text = "Using URL")
        val context = LocalContext.current
        val requestBuilder =
            Glide.with(context).asDrawable().diskCacheStrategy(DiskCacheStrategy.ALL)
        GlideImage(
            imageModel = "https://whc.unesco.org/uploads/thumbs/site_0252_0008-750-750-20151104113424.jpg",
            modifier = Modifier
                .height(height = 400.dp)
                .width(width = getScreenWidth().dp - 30.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
            // shows a placeholder ImageBitmap when loading.
            placeHolder = painterResource(R.drawable.hl1),
            // shows an error ImageBitmap when the request failed.
            error = painterResource(id = R.drawable.hl3),
            //for preview only
            previewPlaceholder = R.drawable.hl1,
            contentScale = ContentScale.FillBounds,
            bitmapPalette = BitmapPalette {
                //it.paletteColorList() will get List<Int> color from image
            },
            requestBuilder = { requestBuilder }
        )
    }
}

//endregion

//region Glide
@Composable
fun ImageLoadingUsingGlide() {
    Column {
        Text(
            text = "Using Glide",
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Row(
            modifier = Modifier
                .horizontalScroll(state = rememberScrollState())
                .padding(top = 24.dp)
        ) {
            LoadImageUsingGlideURL()
            LoadGifUsingGlide()
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoadImageUsingGlideURL() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(text = "Using URL")
        com.bumptech.glide.integration.compose.GlideImage(
            model = "https://whc.unesco.org/uploads/thumbs/site_0252_0008-750-750-20151104113424.jpg",
            contentDescription = null,
            modifier = Modifier
                .height(height = 400.dp)
                .width(width = getScreenWidth().dp - 30.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoadGifUsingGlide() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(text = "Load Gif")
        com.bumptech.glide.integration.compose.GlideImage(
            model = "https://media2.giphy.com/media/aQYR1p8saOQla/giphy.gif?cid=ecf05e4701sln9u63lr3z17lh5f3n3h3owrk54zh1183hqmi&rid=giphy.gif&ct=g",
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(width = getScreenWidth().dp - 30.dp)
                .height(height = 350.dp) //need to provide height & width here
                .clip(shape = RoundedCornerShape(16.dp))
        )
    }
}
//endregion

@Composable
fun MirrorImage() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Mirror Image")
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
        Text(text = "Image (from Resource) with full dimensions")
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
        Text(text = "Image (from ImageVector) with full dimensions")
        Spacer(modifier = Modifier.width(16.dp))
        Row {
            Text(text = "Icons.Default")
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                imageVector = Icons.Default.AddAPhoto,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row {
            Text(text = "Icons.Filled")
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                imageVector = Icons.Filled.AddAPhoto,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row {
            Text(text = "Icons.Outlined")
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
        Text(text = "Image (with bitmap.bmp extension) with full dimensions\nwe can provide bitmap also")
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


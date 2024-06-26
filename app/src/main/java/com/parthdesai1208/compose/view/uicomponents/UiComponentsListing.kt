package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.view.navigation.UIComponentsListingScreen
import com.parthdesai1208.compose.view.uicomponents.bottomsheet.BottomSheetNavGraph


enum class UIComponentsListingEnumType(
    val buttonTitle: Int,
    val func: @Composable (NavHostController) -> Unit
) {
    TextComponents(R.string.text, { TextComponents("World", it) }),
    EditTextCompose(
        R.string.edittext,
        { EditTextCompose(it, androidx.lifecycle.viewmodel.compose.viewModel()) }),
    ButtonComponents(R.string.button, { ButtonCompose(it) }),
    ImageCompose(R.string.image, { ImageComposeScreen(it) }),
    IconCompose(R.string.icon, { IconComposeScreen(it) }),
    SearchBarComponents(
        R.string.searchbar,
        { SearchBar(it, androidx.lifecycle.viewmodel.compose.viewModel()) }),
    SnackBarComponents(R.string.snackbar, { SnackBarCompose(it) }),
    ScaffoldCompose(R.string.scaffold, { ScaffoldCompose() }),
    ColumnCompose(R.string.column, { ColumnNavGraph() }),
    RowCompose(R.string.row, { RowNavGraph() }),
    BoxCompose(R.string.box, { BoxNavGraph() }),
    CardCompose(R.string.card, { CardCompose() }),
    CollapsableRecyclerviewScreen(R.string.verticallist, { VerticalListNavGraph() }),
    HorizontalListScreen(R.string.horizontallist, { HorizontalListNavGraph() }),
    ConstraintLayoutContent(R.string.constraintlayoutcontent, { ConstraintLayoutContent() }),
    ConstraintLayoutScreen(R.string.runtimeconstraintlayout, { DecoupledConstraintLayout() }),
    ConstraintLayoutClock(R.string.clockusingconstraintlayout, { ClockByConstraintLayout() }),
    BottomSheetScreen(R.string.bottomsheet, { BottomSheetNavGraph() }),
    SwitchCompose(R.string.switch1, { SwitchCompose() }),
    RadioCompose(R.string.radiobutton, { RadioButtonCompose() }),
    CheckBoxCompose(R.string.checkbox, { CheckBoxCompose() }),
    DropdownMenuCompose(R.string.dropdownmenu, { DropdownMenu() }),
    SliderCompose(
        R.string.slider,
        { SliderCompose(androidx.lifecycle.viewmodel.compose.viewModel()) }),
    BadgeCompose(R.string.badge, { BadgeCompose() }),
    DialogCompose(R.string.dialog1, { DialogCompose() }),
    ToolTipCompose(R.string.tooltip, { TooltipOnLongClickExample() }),
    DateTimeCompose(R.string.dateTimePicker, { DateTimeCompose() }),
    ListItemCompose(R.string.listItem, { ListItemCompose() }),
}

@Composable
fun ChildUIComponentsScreen(onClickButtonTitle: Int?, navHostController: NavHostController) {
    enumValues<UIComponentsListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke(
        navHostController
    )
}

@Composable
fun UIComponentsListingScreen(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: UIComponentsListingEnumType
    ) {
        val context = LocalContext.current

        Button(
            onClick = { navController.navigate(UIComponentsListingScreen(pathPostFix = title.buttonTitle)) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(context.getString(title.buttonTitle), textAlign = TextAlign.Center)
        }
    }
    Surface {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }, imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "UI Components Samples",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                enumValues<UIComponentsListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}
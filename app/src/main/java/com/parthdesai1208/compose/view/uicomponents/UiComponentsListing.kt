package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.view.navigation.UIComponentsListingScreen
import com.parthdesai1208.compose.view.uicomponents.bottomsheet.BottomSheetListingScreen


enum class UIComponentsListingEnumType(
    val buttonTitle: Int,
    val func: @Composable (NavHostController) -> Unit
) {
    TextComponents(R.string.text, { TextComponents(R.string.world, it) }),
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
    ScaffoldCompose(R.string.scaffold, { ScaffoldCompose(it) }),
    ColumnCompose(R.string.column, { ColumnListingScreen(it) }),
    RowCompose(R.string.row, { RowListingScreen(it) }),
    BoxCompose(R.string.box, { BoxListingScreen(it) }),
    CardCompose(R.string.card, { CardCompose(it) }),
    CollapsableRecyclerviewScreen(R.string.verticallist, { VerticalListListing(it) }),
    HorizontalListScreen(R.string.horizontallist, { HorizontalList(navController = it) }),
    HorizontalPagerListScreen(R.string.horizontalPagerlist, { HorizontalPagerListScreen(it) }),
    ConstraintLayoutContent(R.string.constraintlayoutcontent, { ConstraintLayoutContent(it) }),
    ConstraintLayoutScreen(R.string.runtimeconstraintlayout, { DecoupledConstraintLayout(it) }),
    ConstraintLayoutClock(R.string.clockusingconstraintlayout, { ClockByConstraintLayout(it) }),
    BottomSheetScreen(R.string.bottomsheet, { BottomSheetListingScreen(it) }),
    SwitchCompose(R.string.switch1, { SwitchCompose(it) }),
    RadioCompose(R.string.radiobutton, { RadioButtonCompose(it) }),
    CheckBoxCompose(R.string.checkbox, { CheckBoxCompose(it) }),
    DropdownMenuCompose(R.string.dropdownmenu, { DropdownMenu(it) }),
    SliderCompose(
        R.string.slider,
        { SliderCompose(androidx.lifecycle.viewmodel.compose.viewModel(), it) }),
    BadgeCompose(R.string.badge, { BadgeCompose(it) }),
    DialogCompose(R.string.dialog1, { DialogCompose(it) }),
    ToolTipCompose(R.string.tooltip, { TooltipOnLongClickExample(it) }),
    DateTimeCompose(R.string.dateTimePicker, { DateTimeCompose(it) }),
    ListItemCompose(R.string.listItem, { ListItemCompose(it) }),
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
    BuildTopBarWithScreen(
        title = stringResource(R.string.ui_components_samples), screen = {
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
        }, onBackIconClick = {
            navController.popBackStack()
        })
}
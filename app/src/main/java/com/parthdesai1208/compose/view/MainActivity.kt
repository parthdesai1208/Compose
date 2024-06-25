package com.parthdesai1208.compose.view

import android.app.Application
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.parthdesai1208.compose.ComposeApp
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.model.UserData
import com.parthdesai1208.compose.view.accessibility.AccessibilityScreen
import com.parthdesai1208.compose.view.animation.AnimationScreen
import com.parthdesai1208.compose.view.anyscreen.AnyScreenListingNavGraph
import com.parthdesai1208.compose.view.custom.BaseLineToTopFun
import com.parthdesai1208.compose.view.custom.ComposableVersusComposed
import com.parthdesai1208.compose.view.custom.CustomLayoutListingScreen
import com.parthdesai1208.compose.view.custom.CustomModifierListingScreen
import com.parthdesai1208.compose.view.custom.RotateAnyComposeDemonstration
import com.parthdesai1208.compose.view.migration.MigrationActivity
import com.parthdesai1208.compose.view.navigation.ComposeSampleChildrenScreen
import com.parthdesai1208.compose.view.navigation.ComposeSamplesScreen
import com.parthdesai1208.compose.view.navigation.NavigationEx1
import com.parthdesai1208.compose.view.navigation.RallyScreen
import com.parthdesai1208.compose.view.navigation.composeDestination.StartForComposeDestination
import com.parthdesai1208.compose.view.networking.NetworkingListNavGraph
import com.parthdesai1208.compose.view.state.MainScreenState
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.view.uicomponents.UIComponentsListingScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewUI()
        }
    }

    @Preview(name = "light", showSystemUi = true)
    @Preview(name = "Dark", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Preview(
        name = "landscape",
        showSystemUi = true,
        device = Devices.AUTOMOTIVE_1024p,
        widthDp = 720,
        heightDp = 360
    )
    @Composable
    fun PreviewUI() {
        ComposeTheme {
            MainActivityNavGraph(application = application)
        }
    }
}

//region for navigation
enum class MainScreenEnumType(
    val buttonTitle: Int,
    val func: @Composable (NavHostController) -> Unit,
    val buttonTitleForAccessibility: Int = buttonTitle,
) {
    //region home screen
    TextComponents(
        R.string.uicomponents, { UIComponentsListingScreen(navController = it) }),
    StateListingScreen(R.string.state, {
        MainScreenState(navController = it)
    }, buttonTitleForAccessibility = R.string.learnstatewithviewmodel),
    CompositionLocal(R.string.learncompositionlocal, { CompositionLocalFun() }),
    CustomModifierListingScreen(
        R.string.custommodifier,
        { CustomModifierListingScreen(navController = it) }),
    CustomLayout(R.string.customlayout, { CustomLayoutListingScreen(it) }),
    AnimationScreen(R.string.animationsamples, { AnimationScreen(it) }),
    NavigationEx1(
        R.string.navigationex1, { NavigationEx1() },
        buttonTitleForAccessibility = R.string.navigationexample
    ),
    ComposeDestination(R.string.composedestination, { StartForComposeDestination() }),
    Accessibility(R.string.accessibility, {}),
    Migration(R.string.migrationToCompose, {}),
    DrawScreen(R.string.drawsamples, { com.parthdesai1208.compose.view.draw.DrawNavGraph() }),
    AnyScreen(R.string.anyscreen, { AnyScreenListingNavGraph() }),
    SlotAPI(R.string.slotapi, { SlotAPI() }),
    Networking(R.string.networking, { NetworkingListNavGraph() }),
    GestureScreen(R.string.gesture, { GestureScreen() }),
    PermissionScreen(R.string.permission, { PermissionListNavGraph() }),
    PictureInPicture(R.string.pictureinpicture, {}),
    //endregion

    //region UIComponentsListing
    TextCompose(R.string.text, {
        com.parthdesai1208.compose.view.uicomponents.TextComponents(
            "World",
            it
        )
    }),
    EditTextCompose(
        R.string.edittext,
        {
            com.parthdesai1208.compose.view.uicomponents.EditTextCompose(
                it,
                androidx.lifecycle.viewmodel.compose.viewModel()
            )
        }),
    ButtonComponents(R.string.button, {
        com.parthdesai1208.compose.view.uicomponents.ButtonCompose(
            it
        )
    }),
    ImageCompose(R.string.image, {
        com.parthdesai1208.compose.view.uicomponents.ImageComposeScreen(
            it
        )
    }),
    IconCompose(
        R.string.icon,
        { com.parthdesai1208.compose.view.uicomponents.IconComposeScreen(it) }),
    SearchBarComponents(
        R.string.searchbar,
        {
            com.parthdesai1208.compose.view.uicomponents.SearchBar(
                it,
                androidx.lifecycle.viewmodel.compose.viewModel()
            )
        }),
    SnackBarComponents(R.string.snackbar, {
        com.parthdesai1208.compose.view.uicomponents.SnackBarCompose(
            it
        )
    }),
    ScaffoldCompose(
        R.string.scaffold,
        { com.parthdesai1208.compose.view.uicomponents.ScaffoldCompose() }),
    ColumnCompose(
        R.string.column,
        { com.parthdesai1208.compose.view.uicomponents.ColumnNavGraph() }),
    RowCompose(R.string.row, { com.parthdesai1208.compose.view.uicomponents.RowNavGraph() }),
    BoxCompose(R.string.box, { com.parthdesai1208.compose.view.uicomponents.BoxNavGraph() }),
    CardCompose(R.string.card, { com.parthdesai1208.compose.view.uicomponents.CardCompose() }),
    CollapsableRecyclerviewScreen(
        R.string.verticallist,
        { com.parthdesai1208.compose.view.uicomponents.VerticalListNavGraph() }),
    HorizontalListScreen(
        R.string.horizontallist,
        { com.parthdesai1208.compose.view.uicomponents.HorizontalListNavGraph() }),
    ConstraintLayoutContent(
        R.string.constraintlayoutcontent,
        { com.parthdesai1208.compose.view.uicomponents.ConstraintLayoutContent() }),
    ConstraintLayoutScreen(
        R.string.runtimeconstraintlayout,
        { com.parthdesai1208.compose.view.uicomponents.DecoupledConstraintLayout() }),
    ConstraintLayoutClock(
        R.string.clockusingconstraintlayout,
        { com.parthdesai1208.compose.view.uicomponents.ClockByConstraintLayout() }),
    BottomSheetScreen(
        R.string.bottomsheet,
        { com.parthdesai1208.compose.view.uicomponents.bottomsheet.BottomSheetNavGraph() }),
    SwitchCompose(
        R.string.switch1,
        { com.parthdesai1208.compose.view.uicomponents.SwitchCompose() }),
    RadioCompose(
        R.string.radiobutton,
        { com.parthdesai1208.compose.view.uicomponents.RadioButtonCompose() }),
    CheckBoxCompose(
        R.string.checkbox,
        { com.parthdesai1208.compose.view.uicomponents.CheckBoxCompose() }),
    DropdownMenuCompose(
        R.string.dropdownmenu,
        { com.parthdesai1208.compose.view.uicomponents.DropdownMenu() }),
    SliderCompose(
        R.string.slider,
        { com.parthdesai1208.compose.view.uicomponents.SliderCompose(androidx.lifecycle.viewmodel.compose.viewModel()) }),
    BadgeCompose(R.string.badge, { com.parthdesai1208.compose.view.uicomponents.BadgeCompose() }),
    DialogCompose(
        R.string.dialog1,
        { com.parthdesai1208.compose.view.uicomponents.DialogCompose() }),
    ToolTipCompose(
        R.string.tooltip,
        { com.parthdesai1208.compose.view.uicomponents.TooltipOnLongClickExample() }),
    DateTimeCompose(
        R.string.dateTimePicker,
        { com.parthdesai1208.compose.view.uicomponents.DateTimeCompose() }),
    ListItemCompose(
        R.string.listItem,
        { com.parthdesai1208.compose.view.uicomponents.ListItemCompose() }),
    //endregion

    //region stateListing
    LearnState(R.string.learnstate, {
        com.parthdesai1208.compose.view.state.TodoActivityScreen(
            it,
            androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }),
    DependentVariableState(
        R.string.dependentVariableState,
        {
            com.parthdesai1208.compose.view.state.DependentVariableState(
                it,
                androidx.lifecycle.viewmodel.compose.viewModel()
            )
        }),
    //endregion

    //region customModifierListing
    BaseLineToTopFun(
        R.string.baseLineToTop, { BaseLineToTopFun() }),
    RotateAnyComposeDemonstration(R.string.rotateComposable, { RotateAnyComposeDemonstration() }),
    ComposableVersusComposed(R.string.composableVersusComposed, { ComposableVersusComposed() }),
    //endregion

    //region custom layout listing
    BottomBarCustomCompose(
        R.string.bottom_bar,
        { com.parthdesai1208.compose.view.custom.BottomBarCustomCompose() }),
    MyOwnColumnFun(R.string.column, { com.parthdesai1208.compose.view.custom.MyOwnColumnFun() }),
    BannerSample(R.string.banner, { com.parthdesai1208.compose.view.custom.BannerSampleScreen() }),
    //endregion

    //region animationSample
    AnimatedVisibilityWithoutParams(
        R.string.animatedvisibility_without_params,
        { com.parthdesai1208.compose.view.animation.AnimatedVisibilityWithoutParams() }),
    AnimatedVisibilityWithParams(
        R.string.animatedvisibility_with_params,
        { com.parthdesai1208.compose.view.animation.AnimatedVisibilityWithParams() }),
    AnimateVisibilityState(
        R.string.animatedvisibility_with_state,
        { com.parthdesai1208.compose.view.animation.AnimateVisibilityState() }),
    AnimateEnterExitChild(
        R.string.enter_exit_visibility_animation,
        { com.parthdesai1208.compose.view.animation.AnimateEnterExitChild() }),
    CrossFade(R.string.crossfade, { com.parthdesai1208.compose.view.animation.CrossFade() }),
    AnimatableOnly(
        R.string.animatableonly,
        { com.parthdesai1208.compose.view.animation.AnimatableOnly() }),
    AnimatedContentSimple(
        R.string.animatedcontentsimple,
        { com.parthdesai1208.compose.view.animation.AnimatedContentSimple() }),
    AnimatedContentWithTransitionSpec1(
        R.string.animatedcontent_with_targetstate_transitionspec_ex_1,
        { com.parthdesai1208.compose.view.animation.AnimatedContentWithTransitionSpec1() }),
    AnimatedContentWithTransitionSpec2(
        R.string.animatedcontent_with_targetstate_transitionspec_ex_2,
        { com.parthdesai1208.compose.view.animation.AnimatedContentWithTransitionSpec2() }),
    AnimatedContentWithTransitionSpec3(
        R.string.animatedcontent_with_targetstate_transitionspec_ex_3,
        { com.parthdesai1208.compose.view.animation.AnimatedContentWithTransitionSpec3() }),
    AnimatedContentSize(
        R.string.animatedcontentsize,
        { com.parthdesai1208.compose.view.animation.AnimatedContentSize() }),
    AnimatedContentSizeTransform(
        R.string.animatedcontentsizetransform,
        { com.parthdesai1208.compose.view.animation.AnimatedContentSizeTransform() }),
    AnimateFloatAsState(
        R.string.animatefloatasstate,
        { com.parthdesai1208.compose.view.animation.AnimateFloatAsState() }),
    AnimateColorAsState(
        R.string.animatecolorasstate,
        { com.parthdesai1208.compose.view.animation.AnimateColorAsState() }),
    AnimateDpAsState(
        R.string.animatedpasstate,
        { com.parthdesai1208.compose.view.animation.AnimateDpAsState() }),
    AnimateSizeAsState(
        R.string.animatesizeasstate,
        { com.parthdesai1208.compose.view.animation.AnimateSizeAsState() }),
    UpdateTransition1(
        R.string.updatetransition_1,
        { com.parthdesai1208.compose.view.animation.UpdateTransitionBasic1() }),
    UpdateTransition2(
        R.string.updatetransition_2,
        { com.parthdesai1208.compose.view.animation.UpdateTransitionBasic2() }),
    UpdateTransitionChild(
        R.string.updatetransitionchild,
        { com.parthdesai1208.compose.view.animation.UpdateTransitionChild() }),
    UpdateTransitionExtension(
        R.string.multiple_anim_updatetransition,
        { com.parthdesai1208.compose.view.animation.UpdateTransitionExtension() }),
    MultipleAnimCoroutineAnimateTo(
        R.string.multiple_anim_coroutine_animateto_rotate_color_change,
        { com.parthdesai1208.compose.view.animation.MultipleAnimCoroutineAnimateTo() }),
    InfiniteColorAnimation(
        R.string.infiniteanimation_color,
        { com.parthdesai1208.compose.view.animation.InfiniteColorAnimation() }),
    InfiniteFloatAnimation(
        R.string.infiniteanimation_float,
        { com.parthdesai1208.compose.view.animation.InfiniteFloatAnimation() }),
    InfiniteOffsetAnimation(
        R.string.infiniteanimation_offset,
        { com.parthdesai1208.compose.view.animation.InfiniteOffsetAnimation() }),
    InfiniteRotation(
        R.string.infinite_rotation,
        { com.parthdesai1208.compose.view.animation.InfiniteRotation() }),
    TargetBasedAnimation(
        R.string.targetbasedanimation,
        { com.parthdesai1208.compose.view.animation.TargetBasedAnimationFun() }),
    Spring(
        R.string.spring,
        { com.parthdesai1208.compose.view.animation.SpringFun() }),
    Tween(R.string.tween, { com.parthdesai1208.compose.view.animation.TweenFun() }), Keyframes(
        R.string.keyframes,
        { com.parthdesai1208.compose.view.animation.KeyFramesFun() }),
    Repeatable(
        R.string.repeatable,
        { com.parthdesai1208.compose.view.animation.RepeatableFun() }),
    InfiniteRepeatable(
        R.string.infiniterepeatable,
        { com.parthdesai1208.compose.view.animation.InfiniteRepeatableFun() }),
    Snap(R.string.snap, { com.parthdesai1208.compose.view.animation.SnapFun() }),
    AnimationVector(
        R.string.animationvector_typeconverter_coroutine,
        { com.parthdesai1208.compose.view.animation.AnimationVectorFun() }),
    AnimationEx1(
        R.string.animationex1,
        { com.parthdesai1208.compose.view.animation.AnimationEx1() }),
    BoxWithIconUpDownAnimation(
        R.string.icon_up_down_animation,
        { com.parthdesai1208.compose.view.animation.BoxWithIconUpDownAnimation() }),
    DuolingoBirdAnimation(
        R.string.duolingo_bird_animation,
        { Surface(Modifier.fillMaxSize()) { com.parthdesai1208.compose.view.animation.DuolingoBird() } }),
    ThreeDCardMoving(
        R.string._3d_card_moving,
        { com.parthdesai1208.compose.view.animation.ThreeDCardMoving() }),
    InstagramLikeParticles(
        R.string.instagram_like_particles,
        { com.parthdesai1208.compose.view.animation.InstagramLikeParticles() }),
    RotatingBorders(
        R.string.rotating_borders,
        { com.parthdesai1208.compose.view.animation.RotatingBorders() }),
    PhysicsBasedAnimation(
        R.string.physics_based_animation,
        { com.parthdesai1208.compose.view.animation.physicsbasedanimation.PhysicsBasedAnimationFun() }),
    ProgressAnimation(
        R.string.progressanimation,
        { com.parthdesai1208.compose.view.animation.ProgressAnimation() }),
    //endregion
}

@Composable
fun MainActivityNavGraph(
    application: Application,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ComposeSamplesScreen) {
        composable<ComposeSamplesScreen> {
            MainScreen(navController = navController)
        }
        composable<ComposeSampleChildrenScreen> { backStackEntry ->
            val arguments = backStackEntry.toRoute<ComposeSampleChildrenScreen>()
            if (arguments.pathPostFix == R.string.accessibility) {
                val container = (application as ComposeApp).container
                AccessibilityScreen(appContainer = container)
            } else {
                ChildScreen(arguments.pathPostFix, navController)
            }
        }

        //region for deep link = https://example.com/task_id=Checking
        composable(
            route = "${RallyScreen.Accounts.name}/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink { uriPattern = "https://example.com/task_id={name}" })
        ) { entry ->
            val accountName = entry.arguments?.getString("name")
            val account = UserData.getAccount(accountName)
            if (account.name.isNotEmpty()) {
                MainScreenEnumType.NavigationEx1.func.invoke(navController)
            }
        }
        //endregion
    }
}

@Composable
fun ChildScreen(onClickButtonTitle: Int?, navController: NavHostController) {
    enumValues<MainScreenEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke(
        navController
    )
}

@Composable
fun MainScreen(navController: NavHostController) {

    @Composable
    fun MyButton(
        title: MainScreenEnumType,
    ) {
        val context = LocalContext.current

        Button(
            onClick = {
                if (title.buttonTitle == R.string.migrationToCompose) {
                    context.startActivity(Intent(context, MigrationActivity::class.java))
                    return@Button
                } else if (title.buttonTitle == R.string.pictureinpicture) {
                    context.startActivity(Intent(context, PIPActivity::class.java))
                    return@Button
                }
                navController.navigate(ComposeSampleChildrenScreen(pathPostFix = title.buttonTitle))
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(context.getString(title.buttonTitle),
                textAlign = TextAlign.Center,
                modifier = Modifier.semantics {
                    contentDescription = context.getString(title.buttonTitleForAccessibility)
                })
        }
    }
    Surface {
        Column {
            Text(
                text = "Compose Samples",
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                enumValues<MainScreenEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}
//endregion
package com.parthdesai1208.compose.view.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.model.Account
import com.parthdesai1208.compose.model.Bill
import com.parthdesai1208.compose.model.UserData
import com.parthdesai1208.compose.view.theme.AlertDialogTheme
import java.text.DecimalFormat
import java.util.*

@Composable
fun NavigationEx1() {
    val allScreens = RallyScreen.values().toList()
    val navController = rememberNavController()
    val backstackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = RallyScreen.fromRoute(backstackEntry.value?.destination?.route)

    Scaffold(
        topBar = {
            HorizontalTabRow(
                allScreens = allScreens,
                onTabSelected = { screen -> navController.navigate(screen.name) },
                currentScreen = currentScreen
            )
        }
    ) {
        RallyNavHost(navController = navController)
    }
}

//region enum class
enum class RallyScreen(
    val icon: ImageVector
) {
    Overview(
        icon = Icons.Filled.PieChart
    ),
    Accounts(
        icon = Icons.Filled.AttachMoney
    ),
    Bills(
        icon = Icons.Filled.MoneyOff
    );

    companion object {
        fun fromRoute(route: String?): RallyScreen =
            when (route?.substringBefore("/")) {
                Accounts.name -> Accounts
                Bills.name -> Bills
                Overview.name -> Overview
                null -> Overview
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
//endregion

//region top bar
@Composable
fun HorizontalTabRow(
    allScreens: List<RallyScreen>,
    onTabSelected: (RallyScreen) -> Unit,
    currentScreen: RallyScreen
) {
    Surface(
        Modifier
            .height(56.dp)
            .fillMaxWidth()
    ) {
        Row(Modifier.selectableGroup()) {
            allScreens.forEach { screen ->
                IndividualTab(
                    text = screen.name,
                    icon = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
private fun IndividualTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean
) {
    val color = MaterialTheme.colors.onSurface
    val durationMillis = if (selected) 150 else 100
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = 100
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = 0.60f),
        animationSpec = animSpec
    )
    Row(
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize()
            .height(56.dp)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .clearAndSetSemantics { contentDescription = text }
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = tabTintColor)
        if (selected) {
            Spacer(Modifier.width(12.dp))
            Text(text.uppercase(Locale.getDefault()), color = tabTintColor)
        }
    }
}
//endregion

//region nav host
@Composable
fun RallyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = RallyScreen.Overview.name,
        modifier = modifier
    ) {
        composable(route = RallyScreen.Overview.name) {
            OverviewScreen(onClickSeeAllAccounts = { navController.navigate(RallyScreen.Accounts.name) },
                onClickSeeAllBills = { navController.navigate(RallyScreen.Bills.name) },
                onClickIndividualAccountRow = { name -> navController.navigate("${RallyScreen.Accounts.name}/$name") },
                onClickIndividualBillRow = { name -> navController.navigate("${RallyScreen.Bills.name}/$name") })
        }

        composable(route = RallyScreen.Accounts.name) {
            AccountsScreen(accounts = UserData.accounts) { name ->
                navController.navigate("${RallyScreen.Accounts.name}/$name")
            }
        }

        composable(
            route = "${RallyScreen.Accounts.name}/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink { uriPattern = "https://example.com/task_id={name}" })
        ) { entry ->
            val accountName = entry.arguments?.getString("name")
            val account = UserData.getAccount(accountName)
            AccountScreenWithSingleRecord(account = account)
        }

        composable(route = RallyScreen.Bills.name) {
            BillsScreen(bills = UserData.bills, onClickIndividualBill = { name ->
                navController.navigate("${RallyScreen.Bills.name}/$name")
            })
        }

        composable(
            route = "${RallyScreen.Bills.name}/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { entry ->
            val billName = entry.arguments?.getString("name")
            val bill = UserData.getBill(billName)
            BillScreenWithSingleRecord(bill = bill)
        }
    }
}
//endregion

//region OverView screen
@Composable
fun OverviewScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onClickIndividualAccountRow: (String) -> Unit = {},
    onClickIndividualBillRow: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" } //for testing purpose only
    ) {
        AlertCard()
        Spacer(Modifier.height(RallyDefaultPadding))
        AccountsCard(
            onClickSeeAllAccounts,
            onClickIndividualAccountRow = onClickIndividualAccountRow
        )
        Spacer(Modifier.height(RallyDefaultPadding))
        BillsCard(
            onClickSeeAll = onClickSeeAllBills,
            onClickIndividualBillRow = onClickIndividualBillRow
        )
        Spacer(Modifier.height(RallyDefaultPadding))
        Text(text = "Use https://example.com/task_id=Checking for deeplink")
    }
}

//region Alert Card
@Composable
private fun AlertCard() {
    var showDialog by remember { mutableStateOf(false) }
    val alertMessage = "Heads up, you've used up 90% of your Shopping budget for this month."

    if (showDialog) {
        AlertDialogFun(
            onDismiss = {
                showDialog = false
            },
            bodyText = alertMessage,
            buttonText = "Dismiss".uppercase(Locale.getDefault())
        )
    }
    Card {
        Column {
            AlertCard1stRow(onClickAlertDialog = { showDialog = true })
            CommonRowDividerForAccountsBills(
                modifier = Modifier.padding(
                    start = RallyDefaultPadding,
                    end = RallyDefaultPadding
                )
            )
            AlertCardBottomRow(alertMessage)
        }
    }
}

@Composable
fun AlertDialogFun(
    onDismiss: () -> Unit,
    bodyText: String,
    buttonText: String
) {
    AlertDialogTheme(content = {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = { Text(bodyText) },
            buttons = {
                Column {
                    Divider(
                        Modifier.padding(horizontal = 12.dp),
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                    )
                    TextButton(
                        onClick = onDismiss,
                        shape = RectangleShape,
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(buttonText)
                    }
                }
            }
        )
    })
}

@Composable
private fun AlertCard1stRow(onClickAlertDialog: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(RallyDefaultPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Alerts",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        TextButton(
            onClick = onClickAlertDialog,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = "AlertDialog",
                style = MaterialTheme.typography.button,
            )
        }
    }
}

@Suppress("SameParameterValue")
@Composable
private fun AlertCardBottomRow(message: String) {
    Row(
        modifier = Modifier
            .padding(RallyDefaultPadding)
            // Regard the whole row as one semantics node. This way each row will receive focus as
            // a whole and the focus bounds will be around the whole row content. The semantics
            // properties of the descendants will be merged. If we'd use clearAndSetSemantics instead,
            // we'd have to define the semantics properties explicitly.
            .semantics(mergeDescendants = true) {},
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            style = MaterialTheme.typography.body2,
            modifier = Modifier.weight(1f),
            text = message
        )
        IconButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.Top)
                .clearAndSetSemantics {}
        ) {
            Icon(Icons.Filled.Sort, contentDescription = null)
        }
    }
}
//endregion

//region Account card
@Suppress("ConvertCallChainIntoSequence", "SimplifiableCallChain")
@Composable
private fun AccountsCard(onClickSeeAll: () -> Unit, onClickIndividualAccountRow: (String) -> Unit) {
    val amount = UserData.accounts.map { account -> account.balance }.sum()
    CommonCardForAccountsBills(
        title = stringResource(R.string.accounts),
        amount = amount,
        onClickSeeAll = onClickSeeAll,
        data = UserData.accounts,
        colors = { it.color },
        values = { it.balance }
    ) { account ->
        AccountRow(
            modifier = Modifier.clickable { onClickIndividualAccountRow(account.name) },
            name = account.name,
            number = account.number,
            amount = account.balance,
            color = account.color
        )
    }
}

@Composable
fun AccountRow(
    modifier: Modifier = Modifier,
    name: String,
    number: Int,
    amount: Float,
    color: Color
) {
    CommonRowForAccountsBills(
        modifier = modifier,
        color = color,
        title = name,
        subtitle = stringResource(R.string.account_redacted) + AccountDecimalFormat.format(number),
        amount = amount,
        negative = false
    )
}
//endregion

//region Bills Card
@Suppress("ConvertCallChainIntoSequence", "SimplifiableCallChain")
@Composable
private fun BillsCard(onClickSeeAll: () -> Unit, onClickIndividualBillRow: (String) -> Unit) {
    val amount = UserData.bills.map { bill -> bill.amount }.sum()
    CommonCardForAccountsBills(
        title = stringResource(R.string.bills),
        amount = amount,
        onClickSeeAll = onClickSeeAll,
        data = UserData.bills,
        colors = { it.color },
        values = { it.amount }
    ) { bill ->
        BillRow(
            name = bill.name,
            due = bill.due,
            amount = bill.amount,
            color = bill.color,
            modifier = Modifier.clickable {
                onClickIndividualBillRow(bill.name)
            }
        )
    }
}

@Composable
fun BillRow(name: String, due: String, amount: Float, color: Color, modifier: Modifier = Modifier) {
    CommonRowForAccountsBills(
        modifier = modifier,
        color = color,
        title = name,
        subtitle = "Due $due",
        amount = amount,
        negative = true
    )
}
//endregion

//region common card for accounts & bills
@Composable
private fun <T> CommonCardForAccountsBills(
    title: String,
    amount: Float,
    onClickSeeAll: () -> Unit,
    values: (T) -> Float,
    colors: (T) -> Color,
    data: List<T>,
    row: @Composable (T) -> Unit
) {
    Card {
        Column {
            Column(Modifier.padding(RallyDefaultPadding)) {
                Text(text = title, style = MaterialTheme.typography.subtitle2)
                val amountText = "$" + formatAmount(amount)
                Text(text = amountText, style = MaterialTheme.typography.h2)
            }
            DividerForAccountsBillsCard(data, values, colors)
            Column(Modifier.padding(start = 16.dp, top = 4.dp, end = 8.dp)) {
                data.take(3).forEach { row(it) }
                CommonSeeAllButton(
                    modifier = Modifier.clearAndSetSemantics {
                        contentDescription = "All $title"
                    },
                    onClick = onClickSeeAll,
                )
            }
        }
    }
}

@Composable
private fun <T> DividerForAccountsBillsCard(
    data: List<T>,
    values: (T) -> Float,
    colors: (T) -> Color
) {
    Row(Modifier.fillMaxWidth()) {
        data.forEach { item: T ->
            Spacer(
                modifier = Modifier
                    .weight(values(item))
                    .height(1.dp)
                    .background(colors(item))
            )
        }
    }
}

@Composable
private fun CommonSeeAllButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .height(44.dp)
            .fillMaxWidth()
    ) {
        Text(stringResource(R.string.see_all))
    }
}

@Composable
private fun CommonRowForAccountsBills(
    modifier: Modifier,
    color: Color,
    title: String,
    subtitle: String,
    amount: Float,
    negative: Boolean
) {
    val dollarSign = if (negative) "–$ " else "$ "
    val formattedAmount = formatAmount(amount)
    Row(
        modifier = modifier
            .height(68.dp)
            .clearAndSetSemantics {
                contentDescription =
                    "$title account ending in ${subtitle.takeLast(4)}, current balance $dollarSign$formattedAmount"
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val typography = MaterialTheme.typography
        IndicatorColorOnEachRow(
            color = color,
            modifier = Modifier
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier) {
            Text(text = title, style = typography.body1)
            Text(
                text = subtitle,
                style = typography.subtitle1,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f) //medium level alpha
            )
        }
        Spacer(Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = dollarSign,
                style = typography.h6,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                text = formattedAmount,
                style = typography.h6,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Spacer(Modifier.width(16.dp))

        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(24.dp),
            tint = MaterialTheme.colors.onSurface.copy(alpha = .5f) //medium level alpha
        )

    }
    CommonRowDividerForAccountsBills()
}

@Composable
fun CommonRowDividerForAccountsBills(modifier: Modifier = Modifier) {
    Divider(color = MaterialTheme.colors.background, thickness = 1.dp, modifier = modifier)
}

@Composable
private fun IndicatorColorOnEachRow(color: Color, modifier: Modifier = Modifier) {
    Spacer(
        modifier
            .size(4.dp, 36.dp)
            .background(color = color)
    )
}
//endregion

private val RallyDefaultPadding = 12.dp

private val AccountDecimalFormat = DecimalFormat("####")
//endregion

//region Account Screen
@Suppress("ConvertCallChainIntoSequence", "SimplifiableCallChain")
@Composable
fun AccountsScreen(
    accounts: List<Account>,
    onClickIndividualAccount: (String) -> Unit = {},
) {
    CommonScreenForAccountsBillsAndSingleAccount(
        modifier = Modifier.semantics { contentDescription = "Accounts Screen" },
        items = accounts,
        amounts = { account -> account.balance },
        colors = { account -> account.color },
        amountsTotal = accounts.map { account -> account.balance }.sum(),
        circleLabel = stringResource(R.string.total),
        rows = { account ->
            AccountRow(
                modifier = Modifier.clickable {
                    onClickIndividualAccount(account.name)
                },
                name = account.name,
                number = account.number,
                amount = account.balance,
                color = account.color
            )
        }
    )
}
//endregion

//region Account Screen with single record
@Composable
fun AccountScreenWithSingleRecord(account: Account) {
    CommonScreenForAccountsBillsAndSingleAccount(
        items = listOf(account),
        colors = { account.color },
        amounts = { account.balance },
        amountsTotal = account.balance,
        circleLabel = account.name,
    ) { row ->
        AccountRow(
            name = row.name,
            number = row.number,
            amount = row.balance,
            color = row.color
        )
    }
}
//endregion

//region Bills Screen
@Suppress("ConvertCallChainIntoSequence", "SimplifiableCallChain")
@Composable
fun BillsScreen(bills: List<Bill>, onClickIndividualBill: (String) -> Unit = {}) {
    CommonScreenForAccountsBillsAndSingleAccount(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Bills" },
        items = bills,
        amounts = { bill -> bill.amount },
        colors = { bill -> bill.color },
        amountsTotal = bills.map { bill -> bill.amount }.sum(),
        circleLabel = stringResource(R.string.due),
        rows = { bill ->
            BillRow(bill.name, bill.due, bill.amount, bill.color, modifier = Modifier.clickable {
                onClickIndividualBill(bill.name)
            })
        }
    )
}
//endregion

//region Bill Screen with single record
@Composable
fun BillScreenWithSingleRecord(bill: Bill) {
    CommonScreenForAccountsBillsAndSingleAccount(
        items = listOf(bill),
        colors = { bill.color },
        amounts = { bill.amount },
        amountsTotal = bill.amount,
        circleLabel = bill.name
    ) { billRow ->
        BillRow(billRow.name, billRow.due, billRow.amount, billRow.color)
    }
}
//endregion

//region Common Screen For Accounts, Bills And Single Account
@Composable
fun <T> CommonScreenForAccountsBillsAndSingleAccount(
    modifier: Modifier = Modifier,
    items: List<T>,
    colors: (T) -> Color,
    amounts: (T) -> Float,
    amountsTotal: Float,
    circleLabel: String,
    rows: @Composable (T) -> Unit
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Box(Modifier.padding(16.dp)) {
            val accountsProportion = items.extractProportions { amounts(it) }
            val circleColors = items.map { colors(it) }
            AnimatedCircle(
                accountsProportion,
                circleColors,
                Modifier
                    .height(300.dp)
                    .align(Alignment.Center)
                    .fillMaxWidth()
            )
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = circleLabel,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = formatAmount(amountsTotal),
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        Card {
            Column(modifier = Modifier.padding(12.dp)) {
                items.forEach { item ->
                    rows(item)
                }
            }
        }
    }
}

@Composable
fun AnimatedCircle(
    proportions: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    val currentState = remember {
        MutableTransitionState(AnimatedCircleProgress.START)
            .apply { targetState = AnimatedCircleProgress.END }
    }
    val stroke = with(LocalDensity.current) { Stroke(5.dp.toPx()) }
    val transition = updateTransition(currentState, label = "animated-1")
    val angleOffset by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = 300,
                durationMillis = 700,
                easing = LinearOutSlowInEasing
            )
        }, label = "animate for angle"
    ) { progress ->
        if (progress == AnimatedCircleProgress.START) {
            0f
        } else {
            360f
        }
    }
    val shift by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = 500,
                durationMillis = 900,
                easing = CubicBezierEasing(0f, 0.75f, 0.35f, 0.85f)
            )
        }, label = "animate for shift"
    ) { progress ->
        if (progress == AnimatedCircleProgress.START) {
            0f
        } else {
            30f
        }
    }

    Canvas(modifier) {
        val innerRadius = (size.minDimension - stroke.width) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(
            halfSize.width - innerRadius,
            halfSize.height - innerRadius
        )
        val size = Size(innerRadius * 2, innerRadius * 2)
        var startAngle = shift - 90f
        proportions.forEachIndexed { index, proportion ->
            val sweep = proportion * angleOffset
            drawArc(
                color = colors[index],
                startAngle = startAngle + DividerLengthInDegrees / 2,
                sweepAngle = sweep - DividerLengthInDegrees,
                topLeft = topLeft,
                size = size,
                useCenter = false,
                style = stroke
            )
            startAngle += sweep
        }
    }
}

private const val DividerLengthInDegrees = 1.8f

private enum class AnimatedCircleProgress { START, END }


fun formatAmount(amount: Float): String {
    return AmountDecimalFormat.format(amount)
}

private val AmountDecimalFormat = DecimalFormat("#,###.##")

/**
 * Used with accounts and bills to create the animated circle.
 */

fun <E> List<E>.extractProportions(selector: (E) -> Float): List<Float> {
    val total = this.sumOf { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}
//endregion
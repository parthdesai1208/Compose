package com.parthdesai1208.compose.view.accessibility

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.model.accessibility.InterestsRepository
import com.parthdesai1208.compose.model.accessibility.TopicSelection
import com.parthdesai1208.compose.model.accessibility.TopicsMap
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.view.theme.ComposeTheme
import kotlinx.coroutines.launch

/**
 * Stateful InterestsScreen that handles the interaction with the repository
 *
 * @param interestsRepository data source for this screen
 * @param openDrawer (event) request opening the app drawer
 * @param scaffoldState (state) state for screen Scaffold
 */
@Composable
fun InterestsScreen(
    interestsRepository: InterestsRepository,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    // Returns a [CoroutineScope] that is scoped to the lifecycle of [InterestsScreen]. When this
    // screen is removed from composition, the scope will be cancelled.
    val coroutineScope = rememberCoroutineScope()

    // collectAsState will read a [Flow] in Compose
    val selectedTopics by interestsRepository.observeTopicsSelected().collectAsState(setOf())
    val onTopicSelect: (TopicSelection) -> Unit = {
        coroutineScope.launch { interestsRepository.toggleTopicSelection(it) }
    }
    InterestsScreen(
        topics = interestsRepository.topics,
        selectedTopics = selectedTopics,
        onTopicSelect = onTopicSelect,
        openDrawer = openDrawer,
        scaffoldState = scaffoldState
    )
}

/**
 * Stateless interest screen displays the topics the user can subscribe to
 *
 * @param topics (state) topics to display, mapped by section
 * @param selectedTopics (state) currently selected topics
 * @param onTopicSelect (event) request a topic selection be changed
 * @param openDrawer (event) request opening the app drawer
 * @param scaffoldState (state) the state for the screen's [Scaffold]
 */
@Composable
fun InterestsScreen(
    topics: TopicsMap,
    selectedTopics: Set<TopicSelection>,
    onTopicSelect: (TopicSelection) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            InsetAwareTopAppBar(
                title = { Text(stringResource(id = R.string.interests)) },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(
                            painter = painterResource(R.drawable.ic_jetnews_logo),
                            contentDescription = stringResource(R.string.cd_open_navigation_drawer)
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier
            .padding(it)
            .navigationBarsPadding()) {
            topics.forEach { (section, topics) ->
                item {
                    Text(
                        text = section,
                        modifier = Modifier
                            .padding(16.dp),
                        style = MaterialTheme.typography.subtitle1
                    )
                }
                items(topics) { topic ->
                    TopicItem(
                        itemTitle = topic,
                        selected = selectedTopics.contains(TopicSelection(section, topic))
                    ) { onTopicSelect(TopicSelection(section, topic)) }
                    TopicDivider()
                }
            }
        }
    }
}

/**
 * Display a full-width topic item
 *
 * @param itemTitle (state) topic title
 * @param selected (state) is topic currently selected
 * @param onToggle (event) toggle selection for topic
 */
@Composable
private fun TopicItem(itemTitle: String, selected: Boolean, onToggle: () -> Unit) {
    val image = painterResource(R.drawable.placeholder_1_1)
    // Step 8: State descriptions
    val stateNotSubscribed = stringResource(R.string.state_not_subscribed)
    val stateSubscribed = stringResource(R.string.state_subscribed)
    Row(
        modifier = Modifier
            // Step 8: State descriptions
            .semantics {
                stateDescription = if (selected) {
                    stateSubscribed
                } else {
                    stateNotSubscribed
                }
            }
            // Step 7: Switches and Checkboxes
            .toggleable(
                value = selected,
                onValueChange = { _ -> onToggle() },
                role = Role.Checkbox
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(56.dp, 56.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Text(
            text = itemTitle,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(16.dp),
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(Modifier.weight(1f))
        Checkbox(
            checked = selected,
            // Step 7: Switches and Checkboxes
            onCheckedChange = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

/**
 * Full-width divider for topics
 */
@Composable
private fun TopicDivider() {
    Divider(
        modifier = Modifier.padding(start = 90.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
    )
}

@Phone
@Composable
fun PreviewInterestsScreen() {
    ComposeTheme {
        InterestsScreen(
            interestsRepository = InterestsRepository(),
            openDrawer = {}
        )
    }
}

package com.parthdesai1208.compose

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.parthdesai1208.compose.view.MainActivity
import com.parthdesai1208.compose.view.MainActivityNavGraph
import com.parthdesai1208.compose.view.theme.ComposeTheme
import org.junit.Rule
import org.junit.Test

class TestUI {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testLauncherScreen() {
        composeTestRule.activity.setContent {
            ComposeTheme {
                MainActivityNavGraph(application = composeTestRule.activity.application)
            }
        }
        //region go to TextCompose screen & verify is it displayed
        //region check whether button displayed & should be clickable
        val uIComponentButtonString = composeTestRule.activity.getString(R.string.uicomponents)
        val uIComponentsButton = composeTestRule.onNodeWithText(uIComponentButtonString)
        uIComponentsButton.assertIsDisplayed()
        uIComponentsButton.performClick()
        //endregion
        //region check whether text button displayed & should be clickable
        val textString = composeTestRule.activity.getString(R.string.text)
        val textButton = composeTestRule.onNodeWithText(textString)
        textButton.assertIsDisplayed()
        textButton.performClick()
        //endregion
        val singleClickTextString = composeTestRule.activity.getString(R.string.singleClickText)
        val singleClickText = composeTestRule.onNodeWithText(singleClickTextString)
        singleClickText.assertIsDisplayed()
        //endregion

    }

}
package com.dinesh.android.home

import androidx.compose.runtime.Composable

data class RvData(
    val title: String,
    val description: String,
    val screen: @Composable (() -> Unit)? = null,
)

val rvDataList = listOf(
    RvData("AutoComplete", "Basic AutoComplete (Spinner)") { com.dinesh.android.basic.autocomplete.AutoComplete() },
    RvData("Scaffold Layout v0", "Basic - TopBAr, BottomBar, FAB") { com.dinesh.android.scaffold_layout.v0.MyScaffoldLayout() },
    RvData("Scaffold Layout v1", "Scaffold Rec") { com.dinesh.android.scaffold_layout.v1.MyLayoutView() },
    RvData("TopAppBar", "Scaffold - TopAppBar") { com.dinesh.android.scaffold_layout.top_app_bar.MyLayoutView() },
    RvData("Tab Layout", "Scaffold - Tab Layout with Pager") { com.dinesh.android.scaffold_layout.tab.MyLayoutView() },
    RvData("Bottom Navigation", "Scaffold - Bottom Navigation with icon v1") { com.dinesh.android.scaffold_layout.bottom_navigation.v1.MyLayoutView() },
    RvData("Bottom Navigation", "Scaffold - Bottom Navigation with icon v2") { com.dinesh.android.scaffold_layout.bottom_navigation.v2.MyLayoutView() },
    RvData("BottomAppBar", "Scaffold - MyBottomAppBarLayout") { com.dinesh.android.scaffold_layout.bottom_app_bar.MyLayoutView() },
    RvData("Search Filter v0", "Basic - MySearchLayout (without Scaffold)") { com.dinesh.android.basic.search.v0.MyLayoutView() },
    RvData("Button", "Basic - Button") { com.dinesh.android.basic.button.MyLayoutView() },
    RvData("TextField", "Basic - TextField") { com.dinesh.android.basic.text_field.MyLayoutView() },
    RvData("SwipeCard - Pager", "Animated SwipingCard") { com.dinesh.android.pager.swipe_card.v0.MySwipeCardMainScreen() },
    RvData("Manage State using State Holder", "PreviewForm") { com.dinesh.android.state.state_holder.PreviewForm() },
    RvData("Manage State using ViewModel", "PreviewForm") { com.dinesh.android.state.view_model.MyLayoutView() },
    RvData("StickyHeader", "Basic - StickyHeader") { com.dinesh.android.sticky_headers.v0.MyStickyHeaders() },
    RvData("Card", "Basic - Card") { com.dinesh.android.basic.card.MyLayoutView() },

    /*
    RvData( "Scaffold", "Basic - ") {MyLayoutView()},
    */
).sortedBy { it.title }



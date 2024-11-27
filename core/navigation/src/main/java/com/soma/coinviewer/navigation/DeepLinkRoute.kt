package com.soma.coinviewer.navigation

import android.content.Context
import androidx.annotation.IdRes
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions

sealed class DeepLinkRoute(
    val addressRes: Int,
    val params: Map<String, Any> = emptyMap(),
) {
    data object Home : DeepLinkRoute(
        addressRes = R.string.deeplink_home,
    )

    data object Setting : DeepLinkRoute(
        addressRes = R.string.deeplink_setting,
    )
}

fun NavController.deepLinkNavigateTo(
    context: Context,
    deepLinkRoute: DeepLinkRoute,
    @IdRes popUpTo: Int? = null,
) {
    val deepLinkRequest = buildDeepLinkRequest(context, deepLinkRoute)
    val navOptions = popUpTo?.let {
        NavOptions.Builder()
            .apply { setPopUpTo(it, true) }
            .build()
    }

    navigate(deepLinkRequest, navOptions)
}

private fun buildDeepLinkRequest(
    context: Context,
    destination: DeepLinkRoute
): NavDeepLinkRequest {
    return NavDeepLinkRequest.Builder
        .fromUri(destination.getDeepLink(context).toUri())
        .build()
}

private fun DeepLinkRoute.getDeepLink(context: Context): String {
    val baseUrl = context.getString(this.addressRes)

    return params.entries.fold(baseUrl) { acc, param ->
        acc.replace("{${param.key}}", param.value.toString())
    }
}
package com.hanamin.weather.module

import android.content.Context
import com.hanamin.weather.ui.view.customs.KitToast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class UiModule {

    @Provides
    fun kitToastProvider(@ActivityContext context: Context): KitToast {
        return KitToast(context)
    }


}

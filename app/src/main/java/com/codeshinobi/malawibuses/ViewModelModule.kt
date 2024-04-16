package com.codeshinobi.malawibuses

import BusViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {
    @Provides
    fun provideUserViewModel():BusViewModel{
        return BusViewModel()
    }
}
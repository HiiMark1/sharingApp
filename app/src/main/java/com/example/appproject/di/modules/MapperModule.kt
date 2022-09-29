package com.example.appproject.di.modules

import com.example.appproject.features.main.data.mappers.ItemMapper
import dagger.Module
import dagger.Provides

@Module
class MapperModule {
    @Provides
    fun providePostMapper(): ItemMapper = ItemMapper()
}
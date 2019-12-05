package com.example.rental.di

import com.example.rental.ui.fragments.CarListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun bindCarListFragment(): CarListFragment
}
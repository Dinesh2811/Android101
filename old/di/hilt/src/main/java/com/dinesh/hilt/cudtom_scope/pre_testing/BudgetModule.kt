//package com.dinesh.hilt.cudtom_scope.pre_testing
//
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.FragmentComponent
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Named
//import javax.inject.Scope
//import javax.inject.Singleton
//
//@Scope
//@Retention(AnnotationRetention.RUNTIME)
//annotation class CustomScope
//
//@Module
//@InstallIn(SingletonComponent::class)
//object BudgetModule {
//
////    @Provides
////    @Named("FragmentScoped")
////    fun providesFragmentScopedViewModel(repository: BudgetRepository): BudgetViewModel {
////        return BudgetViewModel(repository)
////    }
//
//    @Singleton
//    @Provides
//    @Named("Singleton")
//    fun providesBudgetViewModel(): BudgetViewModel {
//        return BudgetViewModel()
//    }
//
//}
//
//@Module
//@InstallIn(FragmentComponent::class)
//object FragmentBudgetModule {
//    @CustomScope
//    @Provides
//    @Named("CustomScoped")
//    fun providesCustomScopedViewModel(): BudgetViewModel {
//        return BudgetViewModel()
//    }
//}
//
///*
//
//@EntryPoint
//@InstallIn(CustomComponent::class)
//interface CustomComponentEntryPoint {
//    fun getCustomScopedViewModel(): BudgetViewModel
//}
//
//@CustomScope
//@DefineComponent(parent = SingletonComponent::class)
//interface CustomComponent {
//}
//
//@Scope
//@Retention(AnnotationRetention.RUNTIME)
//annotation class CustomScope
//
//@Module
//@InstallIn(CustomComponent::class)
//object FragmentBudgetModule {
//    @CustomScope
//    @Provides
//    @Named("CustomScoped")
//    fun providesCustomScopedViewModel(repository: BudgetRepository): BudgetViewModel {
//        return BudgetViewModel(repository)
//    }
//}
//
//@Module
//@InstallIn(SingletonComponent::class)
//object BudgetModule {
//    @Singleton
//    @Provides
//    fun providesBudgetRepository(apiService: ApiService): BudgetRepository {
//        return BudgetRepositoryImpl(apiService)
//    }
//
////    @Provides
////    @Named("FragmentScoped")
////    fun providesFragmentScopedViewModel(repository: BudgetRepository): BudgetViewModel {
////        return BudgetViewModel(repository)
////    }
//
//    @Singleton
//    @Provides
//    @Named("Singleton")
//    fun providesBudgetViewModel(repository: BudgetRepository): BudgetViewModel {
//        return BudgetViewModel(repository)
//    }
//
//}
//
//
////@Module
////@InstallIn(SingletonComponent::class)
////object BudgetModule {
////
////    @Singleton
////    @Provides
////    fun providesBudgetRepository(apiService: ApiService): BudgetRepository {
////        return BudgetRepositoryImpl(apiService)
////    }
////
//////    @Singleton
//////    @Provides
//////    fun provideViewModelFactory(): ViewModelProvider.Factory {
//////        return object : ViewModelProvider.Factory {
//////            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//////                throw IllegalArgumentException("Unknown ViewModel class")
//////            }
//////        }
//////
//////
////////    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
////////    private val budgetViewModel: BudgetViewModel by lazy {
////////        ViewModelProvider(this, viewModelFactory)[BudgetViewModel::class.java]
////////    }
//////
//////
//////    }
////
////    @Singleton
////    @Provides
////    @Named("Singleton")
////    fun providesBudgetViewModel(repository: BudgetRepository): BudgetViewModel {
////        return BudgetViewModel(repository)
////    }
////}
// */
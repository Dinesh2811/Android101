//package com.dinesh.hilt.cudtom_scope.pre_testing.fragment
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.dinesh.hilt.cudtom_scope.pre_testing.BudgetViewModel
//import com.dinesh.hilt.databinding.BudgetMainBinding
//import dagger.hilt.android.AndroidEntryPoint
//import javax.inject.Inject
//import javax.inject.Named
//
//@AndroidEntryPoint
//class MyBudgetFragment: Fragment() {
//    private val TAG = "log_" + MyBudgetFragment::class.java.name.split(MyBudgetFragment::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]
//
//    private var _binding: BudgetMainBinding? = null
//    private var shouldPopBackStack: Boolean = false
//
//    @Inject
//    @Named("Singleton")
//    lateinit var budgetViewModel: BudgetViewModel
//
////    @Inject
////    @Named("FragmentScoped")
////    lateinit var viewModel: BudgetViewModel
//
//
//////    @CustomScope
////    @Named("CustomScoped")
////    @Inject
////    lateinit var budgetViewModel: BudgetViewModel
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
//        _binding = BudgetMainBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
//
//    private val binding get() = _binding!!
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//}
//

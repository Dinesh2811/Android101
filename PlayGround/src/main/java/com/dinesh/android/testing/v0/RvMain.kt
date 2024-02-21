//package com.dinesh.android.testing.v0
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.lifecycleScope
//import androidx.lifecycle.repeatOnLifecycle
//import androidx.lifecycle.viewModelScope
//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.PagingData
//import androidx.paging.PagingDataAdapter
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import androidx.paging.cachedIn
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.DividerItemDecoration
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.dinesh.android.R
//import com.dinesh.android.databinding.RvBasicListBinding
//import dagger.hilt.android.AndroidEntryPoint
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.flow.distinctUntilChanged
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//import javax.inject.Named
//
//private val TAG = "log_" + RvMain::class.java.name.split(RvMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]
//
//
//class MyPagingSource(): PagingSource<Int, RvModel>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RvModel> {
//        return try {
//            val data: List<RvModel> = // ToDO: How to get data List?
//            val start = params.key ?: 1
//            return LoadResult.Page(
//                data = data,
//                prevKey = (start - 1).takeIf { it > 0 },
//                nextKey = data.takeIf { it?.isNotEmpty() == true }?.let { start + 1 }
//            )
//        } catch (e: Exception) {
//            e.printStackTrace()
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, RvModel>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1)?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//}
//
//@HiltViewModel
//class RvViewModel @Inject constructor(
//    pager: Pager<Int, RvModel>
//): ViewModel() {
////@HiltViewModel
////class RvViewModel @Inject constructor(private val repository: RvRepository): ViewModel() {
//    private val _rvData: MutableStateFlow<ApiState<List<RvModel>>> = MutableStateFlow(ApiState.Loading(false))
//    val rvData: StateFlow<ApiState<List<RvModel>>> = _rvData.asStateFlow()
//    fun updateToken(newValue: ApiState<List<RvModel>>) {
//        viewModelScope.launch { _rvData.emit(newValue) }
//    }
//    fun fetchData() {
//        viewModelScope.launch {
//            repository.fetchData().collect { apiState ->
//                _rvData.value = apiState
//            }
//        }
//    }
//}
//
//@AndroidEntryPoint
//class RvMain: AppCompatActivity() {
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var rvAdapter: RvAdapter
//    @Inject lateinit var viewModel: RvViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.rv_basic_main)
//        recyclerView = findViewById(R.id.recyclerView)
//
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.rvData.collect { state ->
//                    when (state) {
//                        is ApiState.Loading -> {
//                            Log.d(TAG, "Loading")
//                        }
//                        is ApiState.Success -> {
//                            val list: List<RvModel> = state.data
//                            Log.d(TAG, "Success")
//                        }
//                        is ApiState.Error -> {
//                            Log.d(TAG, "Error")
//                        }
//                    }
//                }
//            }
//        }
//
//        rvAdapter = RvAdapter(::onItemClick)
//        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        recyclerView.adapter = rvAdapter
//        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
//
//        val pager: Flow<PagingData<RvModel>> = Pager(PagingConfig(pageSize = 10)) { MyPagingSource() }.flow.distinctUntilChanged().cachedIn(lifecycleScope)
//        lifecycleScope.launch {
//            pager.collectLatest { pagingData ->
//                rvAdapter.submitData(pagingData)
//            }
//        }
//    }
//
//    private fun fetchData(): MutableList<RvModel> {
//        val rvModelList = mutableListOf<RvModel>()
//        for (i in 0..50) {
//            rvModelList.add(RvModel(name = "User " + (i + 1)))
//        }
//        return rvModelList
//    }
//
//    private fun onItemClick(view: View?, position: Int) {
//        Log.d(TAG, "onItemClick: ${position}")
//    }
//}
//
//class RvAdapter(val onItemClick: (View?, Int) -> Unit) : PagingDataAdapter<RvModel, RvAdapter.MyViewHolder>(RvModel.diffCallback) {
//
//    inner class MyViewHolder(val binding: RvBasicListBinding): RecyclerView.ViewHolder(binding.root) {
//        init {
//            itemView.setOnClickListener { onItemClick(it, bindingAdapterPosition) }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        return MyViewHolder(RvBasicListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val rvModel = getItem(position)
//        holder.binding.apply {
//            tvName.text = rvModel?.name
//            tvPosition.text = position.toString()
//        }
//    }
//}
//
//
//data class RvModel(val name: String) {
//    companion object {
//        val diffCallback = object: DiffUtil.ItemCallback<RvModel>() {
//            override fun areItemsTheSame(oldItem: RvModel, newItem: RvModel): Boolean {
//                return oldItem.name == newItem.name
//            }
//
//            override fun areContentsTheSame(oldItem: RvModel, newItem: RvModel): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//}
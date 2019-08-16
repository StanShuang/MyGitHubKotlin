package com.stan.mygithub.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.commonrecycler.BindSuperAdapter
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.commonrecycler.listener.OnItemClickListener
import com.shuyu.commonrecycler.listener.OnLoadingListener
import com.stan.mygithub.base.binding.autoCleared
import com.stan.mygithub.base.holder.*
import com.stan.mygithub.been.EmptyUIModel
import javax.inject.Inject

/**
 * Author: Stan
 * Date: 2019/8/8 17:40
 * Description: 基本列表
 */
abstract class BaseListFragment<T: ViewDataBinding,R: BaseViewModel>: BaseFragment<T>(),OnLoadingListener,OnItemClickListener{
    protected var normalAdapterManager by autoCleared<BindingDataRecyclerManager>()
    var adapter by autoCleared<BindSuperAdapter>()
    private lateinit var baseViewModel: R

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(view: View?) {
        normalAdapterManager = BindingDataRecyclerManager()
        baseViewModel = ViewModelProviders.of(this,viewModelFactory).get(getViewModelClass())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        getViewModel().loading.observe(this, Observer {
            when(it){
                LoadState.RefreshDone -> {
                    //刷新列表
                    refreshComplete()

                }
                LoadState.LoadMoreDone -> {
                    //刷新列表
                    loadMoreComplete()
                }

            }
        })
        getViewModel().dataList.observe(this, Observer {
            it.apply {
                if(this.size > 0){
                    if(getViewModel().isFirstData()){
                        adapter?.dataList?.clear()
                    }
                    val currentSize = adapter?.dataList?.size ?: 0
                    if(currentSize == 0){
                        notifyChanged()
                    }else{
                        notifyInsert(currentSize, this.size)
                    }
                }else{
                    if(getViewModel().isFirstData()){
                        adapter?.dataList?.clear()
                        notifyChanged()
                    }
                }
            }
        })
        getViewModel().needMore.observe(this, Observer {
            it.apply {
                normalAdapterManager?.setNoMore(it)
            }
        })
        showRefresh()

    }

    open fun showRefresh() {
        normalAdapterManager?.setRefreshing(true)
    }

    open fun loadMoreComplete() {
        normalAdapterManager?.loadMoreComplete()
    }

    open fun refreshComplete() {
        normalAdapterManager?.refreshComplete()
    }
    open fun notifyChanged(){
        adapter?.notifyDataSetChanged()
    }
    open fun  notifyInsert(position: Int,count: Int){
        adapter?.notifyItemRangeInserted(position + adapter!!.absFirstPosition(), count)
    }
    /**
     * viewModel Class
     */
    abstract fun getViewModelClass(): Class<R>
    /**
     * viewModel
     */
    open fun getViewModel(): R = baseViewModel

    /**
     * 获取当前reclerView，为空不走@link initList()的初始化
     */
    abstract fun getRecyclerView(): RecyclerView?

    /**
     * 是否需要下拉刷新
     */
    open fun enableRefresh(): Boolean = false
    /**
     * 是否需要上拉加载
     */
    open fun enableLoadMore(): Boolean = false
    /**
     * item点击
     */
    override fun onItemClick(context: Context, position: Int) {

    }
    /**
     * 刷新
     */
    override fun onRefresh() {
       getViewModel().onRefresh()
    }
    /**
     * 加载更多
     */
    override fun onLoadMore() {
        getViewModel().onLoadMore()
    }
    /**
     * 绑定Item
     */
    abstract fun bindHolder(manager: BindSuperAdapterManager)
    /**
     * 初始化RecyclerManager
     */
    private fun initList() {
        if(activity != null && getRecyclerView() != null){
            normalAdapterManager?.apply {
                setPullRefreshEnabled(enableRefresh())
                setLoadingMoreEnabled(enableLoadMore())
                setOnItemClickListener(this@BaseListFragment)
                setLoadingListener(this@BaseListFragment)
                setRefreshHeader(BindCustomRefreshHeader(activity!!))
                setFootView(BindCustomLoadMoreFooter(activity!!))
                setLoadingMoreEmptyEnabled(false)
                bindEmpty(EmptyUIModel(),EmptyHolder.ID,EmptyHolder::class.java)
                bindHolder(this)
                adapter = BindSuperAdapter(activity as Context,this, arrayListOf())
                getRecyclerView()?.layoutManager = LinearLayoutManager(activity!!)
                getRecyclerView()?.adapter = adapter
            }
        }

    }
}
package com.stan.mygithub.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.stan.mygithub.R
import com.stan.mygithub.adapter.ListDropDownAdapter
import com.stan.mygithub.base.BaseListFragment
import com.stan.mygithub.base.holder.ReposHolder
import com.stan.mygithub.databinding.FragmentTrendBinding
import com.stan.mygithub.module.TrendViewModel
import com.stan.mygithub.module.data.ReposUIModel
import kotlinx.android.synthetic.main.fragment_trend.*


/**
 * Author: Stan
 * Date: 2019/8/5 10:22
 * Description: 推荐
 */
class TrendFragment : BaseListFragment<FragmentTrendBinding, TrendViewModel>(){
    private lateinit var recyclerView: RecyclerView
    override fun getViewModelClass(): Class<TrendViewModel> = TrendViewModel::class.java
    override fun getRecyclerView(): RecyclerView? = recyclerView
    override fun getLayoutId(): Int = R.layout.fragment_trend
    override fun enableRefresh(): Boolean = true
    override fun enableLoadMore(): Boolean = false
    override fun bindHolder(manager: BindSuperAdapterManager) {
        manager.bind(ReposUIModel::class.java,ReposHolder.ID,ReposHolder::class.java)
    }

    override fun onCreateView(view: View?) {
        super.onCreateView(view)
        recyclerView = RecyclerView(activity!!)
        recyclerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDropLists(context)
    }

    private fun initDropLists(context: Context?) {
        val sortData: List<List<String>> = getViewModel().sortData
        val sortValue = getViewModel().srotValue
        val dropMap = HashMap<String,View>()
        for(i in 0 until sortData.size){
            val dropList = ListView(context)
            dropList.dividerHeight = 0
            val sinceListAdapter = ListDropDownAdapter(context!!,sortData[i])
            dropList.adapter = sinceListAdapter
            dropMap[sortData[i][0]] = dropList
            dropList.setOnItemClickListener { adapterView, _, p, _ ->
                (adapterView.adapter as ListDropDownAdapter).setCheckItem(p)
                trend_drop_menu.setTabText(sortData[i][p])
                trend_drop_menu.closeMenu()
                getViewModel().sortType[i] = sortValue[i][p]
                //更新数据
            }

        }
        trend_drop_menu.setDropDownMenu(dropMap.keys.toList(),dropMap.values.toList(),recyclerView)

    }

}
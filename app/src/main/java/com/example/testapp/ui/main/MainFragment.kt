package com.example.testapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.adapter.ViewAdapter
import com.example.testapp.model.CakeModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var sortedList:List<CakeModel>? = null

    private val viewModel: MainViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    fun getCakeList() : List<CakeModel>? {
        return sortedList
    }

    private fun initViews(view:View) {
        recyclerView = view.findViewById(R.id.recyclerview)
        fab = view.findViewById(R.id.fab)
        fab.setOnClickListener{
            sortedList = null
            fetchCakes()
        }
        val layoutManager = LinearLayoutManager(this@MainFragment.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@MainFragment.context,
                layoutManager.orientation
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View =  inflater.inflate(R.layout.fragment_main, container, false)
        fetchCakes()
        initViews(view)
        return view
    }



    private fun fetchCakes() {
        if (sortedList == null) {
            val compositeDisposable = CompositeDisposable()
            compositeDisposable.add(viewModel.fetchCakeList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response -> onResponse(viewModel.removeDuplicatesAndSort(response)) }, { t -> onFailure(t) })
            )
        } else {
            onResponse(sortedList!!)
        }
    }

    private fun onFailure(t: Throwable) {
        handleNetwork(viewModel.isOnline(requireContext()))
    }

    fun onResponse(sortedList: List<CakeModel>) {
        val rvAdapter = ViewAdapter(sortedList, requireContext())
        rvAdapter.setOnItemClickListener(object : ViewAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                if (position>=0)
                    popupDescription(sortedList[position].desc)
            }
        })
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = rvAdapter

        }
    }

    private fun popupDescription (desc:String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Details")
        builder.setNeutralButton("Okay") { dialog, which ->
        }
        builder.setMessage(desc)
        builder.show()
    }

    private fun handleNetwork(isOnline:Boolean) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Data Fetch Failure")
        if (isOnline) {
            builder.setPositiveButton("Retry") { dialog, which ->
                sortedList = null
                fetchCakes()
            }
        }
        builder.setNeutralButton("Okay") { dialog, which ->
            requireActivity().finish()
        }
        if(!isOnline)  builder.setMessage("Not connected to internet") else builder.setMessage("Server error")
        builder.show()
    }
}
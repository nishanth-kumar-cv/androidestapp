package com.example.testapp

import com.example.testapp.api.ApiHelper
import com.example.testapp.model.CakeModel
import com.example.testapp.repository.CakesRepository
import com.example.testapp.ui.main.MainViewModel
import io.reactivex.rxjava3.core.Observable
import org.junit.Test
import org.junit.Assert.*


class MainViewModelTest {

    val apiHelper:ApiHelper = object : ApiHelper {
        override fun getCakes(): Observable<ArrayList<CakeModel>> {
            TODO("Not yet implemented")
        }

    }
    private val fakeCakesRepository = CakesRepository(apiHelper)
    private val viewModel = MainViewModel(fakeCakesRepository)

    @Test
    fun mainViewModel_removeDuplicatesAndSort() {
        val cakeList = mockCakeList()
        // Remove duplicates
        val set: HashSet<CakeModel> = HashSet<CakeModel>(cakeList)
        var sortedList:List<CakeModel> = ArrayList<CakeModel>(set)
        sortedList = sortedList.sortedWith(compareBy { it.title })

        val viewModelSortedList = viewModel.removeDuplicatesAndSort(cakeList)

        System.out.println("sortedList  "+ sortedList.toString())
        System.out.println("viewModelList" + viewModelSortedList.toString())

        assertTrue(sortedList.size == viewModelSortedList.size)

        sortedList.forEachIndexed { index, cakeModel ->
            assertTrue (cakeModel.title.equals(viewModelSortedList.get(index).title))
        }
    }

    private fun mockCakeList():ArrayList<CakeModel> {
        val cakeList = ArrayList<CakeModel>(8)
        cakeList.add(CakeModel("mango", "mango fruit", "https://mock"))
        cakeList.add(CakeModel("zango", "zango fruit", "https://mock"))
        cakeList.add(CakeModel("tango", "tango fruit", "https://mock"))
        cakeList.add(CakeModel("banana", "banana fruit", "https://mock"))
        cakeList.add(CakeModel("congo", "congo fruit", "https://mock"))
        cakeList.add(CakeModel("zango", "zango fruit", "https://mock"))
        cakeList.add(CakeModel("tango", "tango fruit", "https://mock"))
        cakeList.add(CakeModel("banana", "banana fruit", "https://mock"))
        return cakeList
    }

}
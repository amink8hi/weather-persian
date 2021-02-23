package com.hanamin.weather.ui.view.activities

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hanamin.weather.R
import com.hanamin.weather.data.db.room.CityListDataBase
import com.hanamin.weather.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var cityListDataBase: CityListDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)


        // set start destination nav graph
        GlobalScope.launch(Dispatchers.Main) {
            val list = withContext(Dispatchers.IO) {
                cityListDataBase.cityListDao()?.getList()
            }
            if (list == null) {
                navGraph.startDestination = R.id.addFragment
            } else {
                navGraph.startDestination = R.id.weatherFragment
            }
            navController.graph = navGraph
        }

    }

}
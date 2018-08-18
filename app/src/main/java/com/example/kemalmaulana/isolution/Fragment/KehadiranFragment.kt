package com.example.kemalmaulana.isolution.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kemalmaulana.isolution.Adapter.KehadiranAdapter
import com.example.kemalmaulana.isolution.Helper.DummyData
import com.example.kemalmaulana.isolution.Helper.UserSession
import com.example.kemalmaulana.isolution.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class KehadiranFragment : Fragment() {

    private lateinit var adapter: KehadiranAdapter
    private lateinit var currentNis: String

    companion object {
        fun newInstance(): KehadiranFragment {
            return KehadiranFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_kehadiran, container, false)
        val prefs: SharedPreferences = rootView.context.getSharedPreferences(UserSession.PREF_NAME, Context.MODE_PRIVATE)
        currentNis = prefs.getString(getString(R.string.nis), null)

        usePieChart(rootView)
        recyclerViewAdapter(rootView)
        return rootView
    }

    fun usePieChart(rootView: View) {
        //set mutable color for dataset
        val colors: MutableList<Int> = mutableListOf(
                Color.rgb(64, 89, 128),
                Color.rgb(149, 165, 124),
                Color.rgb(217, 184, 162),
                Color.rgb(191, 134, 134),
                Color.rgb(179, 48, 80))
        //Setting dataset
        val pieChart: PieChart = rootView.findViewById(R.id.pieChart)
        val entries: ArrayList<PieEntry> = arrayListOf()
        entries.add(PieEntry(8f, "Hadir", 0))
        entries.add(PieEntry(1f, "Tidak Masuk", 1))
        entries.add(PieEntry(2f, "Sakit", 2))

        val dataset = PieDataSet(entries, null)
        val data = PieData(dataset)

        //Setting the permission
//        data.setValueFormatter(PercentFormatter) //for make the value percentage
        pieChart.data = data
        val desc = Description()
        desc.text = "Diagram kehadiran siswa $currentNis"
        pieChart.description = desc
        pieChart.isDrawHoleEnabled = true
        pieChart.transparentCircleRadius = 58f
        pieChart.holeRadius = 58f
        dataset.colors = colors //Color References
//        dataset.colors = ColorTemplate.JOYFUL_COLORS
        data.setValueTextSize(13f)
        data.setValueTextColor(Color.BLACK)
    }

    fun recyclerViewAdapter(rootView: View) {
        val listLastRecord: RecyclerView = rootView.findViewById<RecyclerView>(R.id.listLastRecord)
        adapter = KehadiranAdapter(rootView.context, DummyData.daftarKehadiran)
        listLastRecord.adapter = adapter

        val layoutManager = LinearLayoutManager(rootView.context)
        listLastRecord.layoutManager = layoutManager
        listLastRecord.setHasFixedSize(true)
    }
}

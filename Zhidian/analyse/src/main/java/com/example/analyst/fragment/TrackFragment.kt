/*
 *    @Copyright 2023 EricMoin
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.example.analyst.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.analyst.GroupActivity
import com.example.analyst.R
import com.example.analyst.adapter.TrackItemCardAdapter
import com.example.analyst.card.AnalystCard
import com.example.analyst.model.AnalystManager
import com.example.analyst.model.AnalystViewModel

class TrackFragment : Fragment() {
    companion object {
        fun newInstance() = TrackFragment()
        const val LABEL = "*"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_track, container, false)
    }
    val viewModel by lazy{ ViewModelProvider(this)[AnalystViewModel::class.java] }
    private lateinit var mainView:View
    private lateinit var adapter: TrackItemCardAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainView = view
        viewModel.loadData()
        initTrackItemCardList()
        initTrackAddPanel()
    }
    private fun initTrackAddPanel() {
        val masterTrackAddPanel = mainView.findViewById<LinearLayout>(R.id.trackAddPanel)
        masterTrackAddPanel.setOnClickListener {
            val intent = Intent(activity,GroupActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initTrackItemCardList() {
        val trackRecyclerView = mainView.findViewById<RecyclerView>(R.id.trackRecyclerView)
        val layoutManager = LinearLayoutManager(activity)
        trackRecyclerView.layoutManager = layoutManager
        val label = arguments?.getString(GroupFragment.LABEL)?:""
        adapter = TrackItemCardAdapter(requireActivity(),viewModel.getListByLabel(label))
        trackRecyclerView.adapter = adapter
        val trackNullInformation = mainView.findViewById<TextView>(R.id.trackNullInformation)
        if(adapter.analystCardList.isNotEmpty()){
            trackRecyclerView.visibility = VISIBLE
            trackNullInformation.visibility = GONE
        }
        adapter.notifyDataSetChanged()
    }
}
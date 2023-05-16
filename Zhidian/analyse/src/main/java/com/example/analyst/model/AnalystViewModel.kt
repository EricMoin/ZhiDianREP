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

package com.example.analyst.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.analyst.card.AnalystCard

class AnalystViewModel : ViewModel(){
    private val _liveData = MutableLiveData< ArrayList<AnalystCard> >()
    val data get() = _liveData
    val cardList = ArrayList<AnalystCard>()
    fun loadData() = TrackRepository.loadData()
    var isSelectAll = false
    fun getListByLabel(label:String):ArrayList<AnalystCard>{
        val list = TrackRepository.getListByLabel(label)
        cardList.clear()
        cardList.addAll(list)
        _liveData.value = cardList
        return cardList
    }
}
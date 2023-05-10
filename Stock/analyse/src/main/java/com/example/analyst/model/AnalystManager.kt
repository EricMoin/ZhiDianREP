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

import com.example.analyst.card.AnalystCard
import kotlin.random.Random

object AnalystManager {
    const val ANALYST = "analyst"
    const val MASTER = "master"
    const val FRIEND = "friend"
    private val analystList = ArrayList<AnalystCard>()
    init{
        loadData()
    }
    fun loadData(){
        repeat(10){
            val card=AnalystCard(
                id = Random.nextInt().toString(),
                label = ANALYST,
                name="小强",
                data="114.514",
                priority = 0,
                isChecked = false,
                isFocused = false,
                avatarIconPath = "",
                buyInDate = "06-20",
                buyInPrice = "16.66",
                stateRange = "16.28%"
            )
            analystList.add(card)
        }
    }
    fun getAnalystListByLabel(label:String) :ArrayList<AnalystCard> {
        val list = ArrayList<AnalystCard>()
        for(analyst in analystList){
            if(analyst.label == label) list.add(analyst)
        }
        return list
    }
    fun deleteAnalystByLabel(label:String){
        for (i in analystList.indices){
            val analyst = analystList[i]
            if(analyst.label ==  label) analystList.removeAt(i)
        }
    }
}
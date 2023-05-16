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

object TrackDao {
    private val analystList = ArrayList<AnalystCard>()
    private val masterList = ArrayList<AnalystCard>()
    private val friendList = ArrayList<AnalystCard>()
    fun getAnalystList() = analystList
    fun getMasterList() = masterList
    fun getFriendList() = friendList
    init{
        makeData()
    }

    private fun makeData() {
        analystList.add(
            AnalystCard(
                id="",
                label="analyst",
                name="小明",
                data="114.514",
                priority = 0,
                isChecked = false,
                isFocused = true,
                avatarIconPath = "",
                buyInDate = "2022-03-20",
                buyInPrice = "224.28",
                stateRange = "16%",
            )
        )
    }
    fun loadData(){
        analystList.clear()
        masterList.clear()
        friendList.clear()
        makeData()
    }
}
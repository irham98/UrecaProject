package com.example.urecaproject.factory

import com.example.urecaproject.model.AdjustModel

class AdjustModelFactory {

    fun getAdjustModel(editType: String) : AdjustModel? {
        for (item in AdjustModel.list) {
            if (item.text.equals(editType, true))
                return item
        }
        return null
    }
}
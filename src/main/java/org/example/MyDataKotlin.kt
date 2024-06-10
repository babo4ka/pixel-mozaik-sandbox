package org.example

class MyDataKotlin {

    private val d: MyData = MyData()

    fun setData(data:Int){
        d.a = data
    }

    fun printData(){
        println(d.a)
    }
}
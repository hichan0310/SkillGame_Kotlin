package skillgame

import java.util.Random




open class Cell(val x:Int, val y:Int, var itemBox:ItemBox?, var player:Player?){
    constructor(x:Int, y:Int):this(x, y, null, null){}
    open fun exe(target:Player){}
}



object Map{
    private val mapSize:Int=30
    private val rand = Random()
    var cells=Array(mapSize, {i->Array(mapSize, {j->Cell(i, j)})})
    fun summonSkill
}
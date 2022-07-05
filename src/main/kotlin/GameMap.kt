package skillgame


open class Cell(val x:Int, val y:Int, var itemBox:ItemBox?, var player:Player?){
    constructor(x:Int, y:Int):this(x, y, null, null){}
    open fun exe(target:Player){}
}

object Map{
    var cells=Array(10, {i->Array(10, {j->Cell(i, j)})})
}
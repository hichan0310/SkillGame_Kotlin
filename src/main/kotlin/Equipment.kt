package skillgame
/* 장비 구현 부분(장비는 인간 전용) */

object parts{
    val weapon:Int=0
    val armor:Int=1
    val sheoes:Int=2
    val helmet:Int=3
    val jewel:Int=4
}

open class Equipment(var name: String, var star:Int, var enforce:Int, var addStatement:Statement, var equipperson:Person?, val type:Int) {
    open fun equip(target: Person) {}
    open fun unEquip(target: Person) {}
    open fun reinforce(target: Person) {}
    open fun getEffect(): Statement {
        return Statement()
    }
}




class FlameSword(var _name:String, var _enforce:Int, var _addStatement:Statement, var _equipperson:Person?):Equipment(_name, 5, _enforce, _addStatement, _equipperson, parts.weapon) {
    constructor() : this("Flame_Sword", 1, Statement(0, 0, 40, 0, 0), null) {}
    override fun toString(): String {
        return "${this.name} lv$enforce ${this.addStatement}"
    }
    override fun equip(target: Person) {
        this.equipperson = target
        target.statement+=this.addStatement
        target.inventory[parts.weapon]=this
    }
    override fun unEquip(target: Person) {
        this.equipperson = null
        target.statement-=this.addStatement
        target.inventory[parts.weapon]=null
    }
    override fun reinforce(target: Person) {
        this.enforce++
        this.addStatement.attack += 5
    }
    override fun getEffect(): Statement {
        return this.addStatement
    }
}

class FlameArmor(var _name:String, var _enforce:Int, var _addStatement:Statement, var _equipperson:Person?):Equipment(_name, 5, _enforce, _addStatement, _equipperson, parts.armor) {
    constructor() : this("Flame_Armor", 1, Statement(-10, 10, 10, 0, -10), null) {}
    override fun toString(): String {
        return "${this.name} lv$enforce ${this.addStatement}"
    }
    override fun equip(target: Person) {
        this.equipperson = target
        target.statement+=this.addStatement
        target.inventory[parts.armor]=this
    }
    override fun unEquip(target: Person) {
        this.equipperson = null
        target.statement-=this.addStatement
        target.inventory[parts.armor]=null
    }
    override fun reinforce(target: Person) {
        this.enforce++
        this.addStatement.shield += 5
    }
    override fun getEffect(): Statement {
        return this.addStatement
    }
}

class FlameSheoes(var _name:String, var _enforce:Int, var _addStatement:Statement, var _equipperson:Person?):Equipment(_name, 5, _enforce, _addStatement, _equipperson, parts.sheoes) {
    constructor() : this("Flame_Sheoes", 1, Statement(5, 5, 0, 10, 0), null) {}
    override fun toString(): String {
        return "${this.name} lv$enforce ${this.addStatement}"
    }
    override fun equip(target: Person) {
        this.equipperson = target
        target.statement+=this.addStatement
        target.inventory[parts.sheoes]=this
    }
    override fun unEquip(target: Person) {
        this.equipperson = null
        target.statement-=this.addStatement
        target.inventory[parts.sheoes]=null
    }
    override fun reinforce(target: Person) {
        this.enforce++
        this.addStatement.shield += 5
    }
    override fun getEffect(): Statement {
        return this.addStatement
    }
}

class FlameHelmet(var _name:String, var _enforce:Int, var _addStatement:Statement, var _equipperson:Person?):Equipment(_name, 5, _enforce, _addStatement, _equipperson, parts.helmet) {
    constructor() : this("Flame_Helmet", 1, Statement(30, 5, 0, 3, 0), null) {}
    override fun toString(): String {
        return "${this.name} lv$enforce ${this.addStatement}"
    }
    override fun equip(target: Person) {
        this.equipperson = target
        target.statement+=this.addStatement
        target.inventory[parts.helmet]=this
    }
    override fun unEquip(target: Person) {
        this.equipperson = null
        target.statement-=this.addStatement
        target.inventory[parts.helmet]=null
    }
    override fun reinforce(target: Person) {
        this.enforce++
        this.addStatement.shield += 5
    }
    override fun getEffect(): Statement {
        return this.addStatement
    }
}

class FlameStone(var _name:String, var _enforce:Int, var _addStatement:Statement, var _equipperson:Person?):Equipment(_name, 5, _enforce, _addStatement, _equipperson, parts.jewel) {
    constructor() : this("Flame_Stone", 1, Statement(0, 0, 30, 0, -10), null) {}
    override fun toString(): String {
        return "${this.name} lv$enforce ${this.addStatement}"
    }
    override fun equip(target: Person) {
        this.equipperson = target
        target.statement+=this.addStatement
        target.inventory[parts.jewel]=this
    }
    override fun unEquip(target: Person) {
        this.equipperson = null
        target.statement-=this.addStatement
        target.inventory[parts.jewel]=null
    }
    override fun reinforce(target: Person) {
        this.enforce++
        this.addStatement.shield += 5
    }
    override fun getEffect(): Statement {
        return this.addStatement
    }
}

/*
class IceSword(var _name:String, var _enforce:Int, var _addStatement:Statement, var _equipperson:Person?):Equipment(_name, 5, _enforce, _addStatement, _equipperson, parts.weapon){
    constructor():this("Ice_Sword", 1, Statement(0, 10, 25, 5, -5), null){}

    override fun toString(): String {

    }
    override fun unEquip(target: Person) {

    }
}*/
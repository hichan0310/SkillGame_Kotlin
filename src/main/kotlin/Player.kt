package skillgame
/* player(팀) 구현 부분 */

class Statement(var hp:Int, var shield:Int, var attack:Int, var evasion:Int, var energy:Int) {
    constructor():this(0, 0, 0, 0, 0){}
    override fun toString(): String {
        return "[(hp : $hp), (보호막 : $shield), (공격력 : $attack), (회피 : $evasion), (에너지 : $energy)]"
    }
    operator fun plusAssign(other:Statement){
        this.hp+=other.hp
        this.attack+=other.attack
        this.energy+=other.energy
        this.shield+=other.shield
        this.evasion+=other.evasion
    }
    operator fun minusAssign(other:Statement){
        this.hp-=other.hp
        this.attack-=other.attack
        this.energy-=other.energy
        this.shield-=other.shield
        this.evasion-=other.evasion
    }
}

open class Player(var name:String, var statement:Statement, var die:Boolean) {
    constructor(_name: String, _statement: Statement) : this(_name, _statement, false) {}

    var skills: SkillHandler = SkillHandler()
    open fun introduce(params: Any?) {}
    fun hurt(damage: Int): Int {
        val hp: Int = this.statement.hp
        if (this.statement.shield >= damage) {
            this.statement.shield -= damage
        } else {
            this.statement.shield = 0
            val dmg: Int = damage - this.statement.shield
            if (this.statement.hp < dmg) {
                this.statement.hp = 0
                this.die = true
            } else {
                this.statement.hp -= dmg
            }
        }
        return hp - this.statement.hp
    }

    fun addSkill(index:Int, skill: Skill) {
        if (die) {
            println("아웃된 사람에게는 스킬을 추가할 수 없음")
            return
        }
        skills.addSkill(index, skill)
    }
    fun addSkill(skill: Skill) {
        if (die) {
            println("아웃된 사람에게는 스킬을 추가할 수 없음")
            return
        }
        skills.addSkill(skill)
    }

    fun useSkill(target: Player, index: Int) {
        skills.castSkill(this, target, index - 1)
    }

    open fun addEquipment(equipment: Equipment){}
    open fun loseEquipment(part: Int){}
}




class Person(var _name:String, var inventory:ArrayList<Equipment?>, var ingredient:ArrayList<Ingredient>):Player(_name, Statement(500, 10, 40, 20, 100)) {
    constructor(name: String) : this(name, arrayListOf<Equipment?>(null, null, null, null, null), ArrayList<Ingredient>()) {}     //[0]=무기, [1]=갑옷, [2]=신발, [3]=투구, [4]=증폭기(보석)

    override fun addEquipment(equipment: Equipment){
        if(inventory[equipment.type]!=null)
            inventory[equipment.type]!!.unEquip(this)
        inventory[equipment.type]=equipment
    }

    override fun loseEquipment(part:Int){
        if(inventory[part]!=null)
            inventory[part]!!.unEquip(this)
    }

    init {
        for (i in 0..4) {
            if (inventory[i] == null)
                continue
            inventory[i]!!.equip(this)
        }
    }

    override fun introduce(params: Any?): Unit {
        println("==============================================================")
        if (params == null) println("인간")
        else println("인간 ($params)")
        println("이름 : $name")
        if (die) println("상태 : out")
        else {
            println("상태 : $statement")
            println("---------------------- 스킬 목록 ----------------------")
            skills.showAllSkill()
            println("---------------------- 장비 목록 ----------------------")
            for (i in 0..4) {
                if (inventory[i] == null)
                    continue
                println("${i + 1}. ${inventory[i]} ")
            }
        }
        println("==============================================================\n")
    }
}


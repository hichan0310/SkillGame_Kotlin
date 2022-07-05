package skillgame
/* 스킬 구현 부분 */

class SkillHandler(var skills:ArrayList<Skill?>, var maxIndex:Int) {
    constructor() : this(arrayListOf<Skill?>(null, null, null, null, null, null), 6) {}
    fun addSkill(index:Int, skill: Skill) {
        this.skills[index]=skill
    }
    fun addSkill(skill: Skill) {
        var index:Int?=null
        for (i in 0..maxIndex-1)
            if(skills[i] == null) {
                index = i
                break
            }
        if(index==null) {
            var lowStarIndex:Int=0
            for (i in 1..maxIndex - 1)
                if(skills[i]!!.star<skills[lowStarIndex]!!.star)
                    lowStarIndex=i
            index=lowStarIndex
        }
        this.skills[index!!]=skill
    }
    fun showAllSkill() {
        for (i in 0..maxIndex-1)
            if(skills[i]!=null)
                println("${i + 1}. ${skills[i]}")
    }
    fun castSkill(caster: Player, target: Player, index: Int) {
        if(caster.die){
            println("아웃된 사람은 스킬을 시전할 수 없습니다. \n")
            return
        }

        if (index < 0 || index >= skills.size)
            return

        val skillResult: SkillLog
        if(skills[index]!=null)
            skillResult= skills[index]!!.useIt(caster, target)
        else return
        if (skillResult.damage == null) {
            println("에너지가 부족하여 공격하지 못하였다. ")
        } else {
            //println("${caster.name} : ${target.name} 에게 ${skillResult.damage}의 피해를 입혔다. (에너지 사용 : ${skillResult.skill.energy})")
        }
        println()
    }
}

class SkillLog(val caster: Player, val target: Player, val skill: Skill, val damage:Int?) {
    override fun toString(): String {
        return "[(caster : ${caster.name}), (target : ${target.name}), (skill : ${skill.name}), (damage : ${damage})]"
    }
} // damage에 null저장 : 에너지 부족




//스킬을 만들고 싶다면 이 클래스를 상속하여 constructor() toString(), useIt()을 구현하자. SkillHandler가 알아서 처리해줄 것이다.
//constructor : 스킬의 기본 능력치 설정
//toString : 스킬 설명에서 출력될 문자열
//useIt : 스킬 실행

abstract class Skill(val name:String, var energy:Int, var explaination:String, var star:Int) {
    abstract fun useIt(caster: Player, target: Player): SkillLog
    protected fun starString(star: Int): String? {
        return when (this.star) {
            1 -> "☆　　　　 "
            2 -> "☆☆　　　 "
            3 -> "☆☆☆　　 "
            4 -> "☆☆☆☆　 "
            5 -> "☆☆☆☆☆ "
            6 -> "★★★★★ "
            else -> null
        }
    }                               // toString의 간편한 구현을 위한 별 문자열 출력 함수
    protected fun checkEnergy(caster: Player, skill: Skill): Boolean {
        if (caster.statement.energy < skill.energy) {
            caster.statement.energy = 0
            return false
        }
        caster.statement.energy -= skill.energy
        return true
    }             // useIt의 간편한 구현을 위한 에너지 감소시키고 체크해주는 함수
}


//에너지파 : 10마나, 아래에 설명 있음.
class EnergyBlast(val _name:String, var _energy:Int, var coefficient:Int, var hitNum:Int, var _explaination:String, var _star:Int):Skill(_name, _energy, _explaination, _star) {
    constructor(star: Int) : this("Energy_Blast", 10, 0, 0, " ", star) {      //1성 에너지파, 2성 에너지파...6성 에너지파
        when (star) {
            1 -> {
                hitNum = 3;coefficient = 40;explaination = "평범한 에너지파이다. 40%의 피해를 3번 입힌다. "
            }
            2 -> {
                hitNum = 3;coefficient = 50;explaination = "에너지를 조금 더 강화한 에너지파이다. 50%의 피해를 3번 입힌다. "
            }
            3 -> {
                hitNum = 5;coefficient = 40;explaination = "에너지가 연속적으로 방출되는 것 같다. 40%의 피해를 5번 입힌다. "
            }
            4 -> {
                hitNum = 10;coefficient = 30;explaination = "특이점이 찾아온 에너지파이다. 30%의 피해를 10번 입힌다. "
            }
            5 -> {
                hitNum = 1;coefficient = 400;explaination = "인생은 한 방이다. 400%의 피해를 입힌다. "
            }
            6 -> {
                hitNum = 7;coefficient = 90;explaination = "에너지파가 매우 강력해졌다. 90%의 피해를 7번 입힌다. "
            }
            else -> {
                hitNum = 0;coefficient = 0;explaination = "뭔가 입력이 잘못되었다. 에너지파는 1성~6성까지 존재한다. "
            }
        }
    }
    override fun toString(): String {
        var result: String = ""
        if (starString(star) == null)
            return "???"
        result += starString(star)!!
        result += "$name[(소비 에너지 : $energy), (피해량 : $coefficient% * $hitNum)] : $explaination"
        return result
    }
    override fun useIt(caster: Player, target: Player): SkillLog {
        if (!checkEnergy(caster, this))
            return SkillLog(caster, target, this, null)
        val damage: Int = caster.statement.attack * this.coefficient / 100

        var sumOfDamage: Int = 0
        for (i in 1..this.hitNum) {
            if (rand.nextInt(100) >= target.statement.evasion)
                sumOfDamage += target.hurt(damage)
        }

        println("${caster.name} : ${this.name}을 이용하여 ${sumOfDamage}의 피해를 입혔다. (에너지 사용 : ${this.energy})")

        return SkillLog(caster, target, this, sumOfDamage)
    }
}

//평타 : 0마나, 아래에 설명 있음
class NormalAttack(val _name:String, var coefficient:Int, var _explaination: String, var _star: Int):Skill(_name, 0, _explaination, _star) {
    constructor(star: Int) : this("Simple_Attack", 0, " ", star) {
        when (star) {
            1 -> {
                coefficient = 50;explaination = "약한 때리기이다. 50%의 피해를 입힌다. "
            }
            2 -> {
                coefficient = 75;explaination = "약한 때리기이다. 75%의 피해를 입힌다. "
            }
            3 -> {
                coefficient = 100;explaination = "평범한 때리기이다. 100%의 피해를 입힌다. "
            }
            4 -> {
                coefficient = 125;explaination = "조금 강화된 때리기이다. 125%의 피해를 입힌다. "
            }
            5 -> {
                coefficient = 150;explaination = "강력해진 때리기이다. 150%의 피해를 입힌다. "
            }
            6 -> {
                coefficient = 200;explaination = "이게 평타가 맞나? 200%의 피해를 입힌다. "
            }
        }
    }
    override fun toString(): String {
        var result: String = ""
        if (starString(star) == null)
            return "???"
        result += starString(star)!!
        result += "$name[(소비 에너지 : $energy), (피해량 : $coefficient%)] : $explaination"
        return result
    }
    override fun useIt(caster: Player, target: Player): SkillLog {
        if (!checkEnergy(caster, this))
            return SkillLog(caster, target, this, null)
        val damage: Int = caster.statement.attack * this.coefficient / 100

        var sumOfDamage: Int = 0
        if (rand.nextInt(100) >= target.statement.evasion)
            sumOfDamage += target.hurt(damage)

        println("${caster.name} : ${this.name}을 이용하여 ${sumOfDamage}의 피해를 입혔다. (에너지 사용 : ${this.energy})")

        return SkillLog(caster, target, this, sumOfDamage)
    }
}

//흡혈 공격 : 30마나, 상대방 hp의 20%에 해당하는 피해를 입히고, hp를 채운다. 등급이 늘어날수록 흡혈향이 늘어난다. 각성 시에는 피해량이 40%로 늘어난다.
class HpSteal(val _name:String, var decreaseHp:Int, var hpSteal:Int, var _explaination:String, var _star:Int):Skill(_name, 30, _explaination, _star){
    constructor(star:Int):this("Hp_Steal", 20, 0, " ", star){
        when(star){
            1->{hpSteal=60;explaination="상대방의 Hp를 20% 감소시키고, 피해량의 60%를 회복한다. "}
            2->{hpSteal=70;explaination="상대방의 Hp를 20% 감소시키고, 피해량의 70%를 회복한다. "}
            3->{hpSteal=80;explaination="상대방의 Hp를 20% 감소시키고, 피해량의 80%를 회복한다. "}
            4->{hpSteal=100;explaination="상대방의 Hp를 20% 감소시키고, 피해량의 100%를 회복한다. "}
            5->{hpSteal=150;explaination="상대방의 Hp를 20% 감소시키고, 피해량의 150%를 회복한다. "}
            6->{decreaseHp=40;hpSteal=200;explaination="상대방의 Hp를 40% 감소시키고, 피해량의 200%만큼 회복한다. "}
            else->{decreaseHp=0;hpSteal=0;explaination=" "}
        }
    }
    override fun toString(): String {
        var result: String = ""
        if (starString(star) == null)
            return "???"
        result += starString(star)!!
        result += "$name[(소비 에너지 : $energy), (피해량 : 상대방 체력의 $decreaseHp%), (흡혈량 : 피해량의 $hpSteal%)] : $explaination"
        return result
    }
    override fun useIt(caster: Player, target: Player): SkillLog {
        if (!checkEnergy(caster, this))
            return SkillLog(caster, target, this, null)

        val damage: Int = target.statement.hp*this.decreaseHp/100

        if(rand.nextInt(100) >= target.statement.evasion) {
            println("${caster.name} : ${this.name}을 이용하여 ${damage}의 피해를 입혔다. ${damage*this.hpSteal/100}의 체력을 회복했다. (에너지 사용 : ${this.energy})")
            caster.statement.hp+=damage*this.hpSteal/100
            return SkillLog(caster, target, this, target.hurt(damage))
        }
        println("${caster.name} : ${this.name}을 이용하여 ${0}의 피해를 입혔다. ${0}의 체력을 회복했다. (에너지 사용 : ${this.energy})")
        return SkillLog(caster, target, this, 0)
    }
}
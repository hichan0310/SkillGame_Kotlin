package skillgame
/* main함수 */

import java.util.Random
val rand=Random()


/* 나도 모르겠는데 뭔가 게임이 되어 가는 중
 * 아직 결과만 출력하도록 했는데 입력까지 받는다면 진짜 게임처럼 될 듯
 *
 * (추가할 것들 : 현재 만들어진 것들)
 * 장비들 : 불의 검, 불의 갑옷, 불의 신발, 불의 헬멧, 불의 돌
 * 스킬들 : 에너지 폭발, 평타, 흡혈
 * 팀 이름 : 인간
 * 게임 맵
 * 강화 시스템
 */


/* main 함수 구현 부분 */

fun main() {
    var player1:Person=Person("hichan", arrayListOf<Equipment?>(FlameSword(), FlameArmor(), FlameSheoes(), FlameHelmet(), FlameStone()))
    var player2 = Person("woong")

    player1.addSkill(EnergyBlast(6))
    player1.addSkill(HpSteal(6))
    player1.addSkill(NormalAttack(6))

    player2.addSkill(EnergyBlast(3))

    player1.loseEquipment(parts.sheoes)
    player1.loseEquipment(parts.jewel)

    player1.introduce(null)
    player2.introduce(null)
    println("\n\n")

    player2.useSkill(player1, 1)
    for (i in 6 downTo 1)
        player1.useSkill(player2, i)
    player2.useSkill(player1, 1)
    player2.useSkill(player1, 1)

    player1.introduce(null)
    player2.introduce(null)
}
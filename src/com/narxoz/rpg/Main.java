package com.narxoz.rpg;

import java.util.*;

import com.narxoz.rpg.battle.*;
import com.narxoz.rpg.bridge.*;
import com.narxoz.rpg.composite.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 4 Demo: Bridge + Composite ===\n");

        HeroUnit warrior = new HeroUnit("Warrior", 140, 30);
        HeroUnit mage = new HeroUnit("Mage", 90, 40);
        HeroUnit priest = new HeroUnit("Priest", 100, 18);
        HeroUnit rogue = new HeroUnit("Rogue", 95, 34);
        EnemyUnit goblin = new EnemyUnit("Goblin", 70, 20);
        EnemyUnit orc = new EnemyUnit("Orc", 120, 25);
        EnemyUnit warlock = new EnemyUnit("Warlock", 85, 28);
        EnemyUnit troll = new EnemyUnit("Troll", 110, 22);

        PartyComposite vanguard = new PartyComposite("Vanguard");
        vanguard.add(warrior);
        vanguard.add(rogue);

        PartyComposite backline = new PartyComposite("Backline");
        backline.add(mage);
        backline.add(priest);

        RaidGroup heroes = new RaidGroup("Alliance Raid");
        heroes.add(vanguard);
        heroes.add(backline);

        PartyComposite frontline = new PartyComposite("Frontline");
        frontline.add(goblin);
        frontline.add(orc);

        PartyComposite ritualCircle = new PartyComposite("Ritual Circle");
        ritualCircle.add(warlock);
        ritualCircle.add(troll);

        RaidGroup monsterNest = new RaidGroup("Monster Nest");
        monsterNest.add(frontline);
        monsterNest.add(ritualCircle);

        RaidGroup enemies = new RaidGroup("Enemy Raid");
        enemies.add(monsterNest);

        System.out.println("--- Team Structures ---");
        heroes.printTree("");
        enemies.printTree("");
        System.out.println();

        Skill slashFire = new SingleTargetSkill("Slash", 20, new FireEffect());
        Skill slashIce = new SingleTargetSkill("Slash", 20, new IceEffect());
        Skill stormFire = new AreaSkill("Storm", 15, new IceEffect());
        Skill shockwaveFire = new AreaSkill("Shockwave", 15, new FireEffect());
        Skill piercePhysical = new SingleTargetSkill("Pierce", 18, new PhysicalEffect());
        Skill curseShadow = new SingleTargetSkill("Curse Spear", 17, new ShadowEffect());

        List<Skill> heroSkills = Arrays.asList(slashFire, slashIce, stormFire, shockwaveFire, piercePhysical, curseShadow);
        List<Skill> enemySkills = Arrays.asList(slashFire, slashIce, stormFire, shockwaveFire, piercePhysical, curseShadow);

        System.out.println("--- Bridge Preview ---");
        System.out.println("Same skill, different effects:");
        System.out.println("- " + slashFire.getSkillName() + " using " + slashFire.getEffectName());
        System.out.println("- " + slashIce.getSkillName() + " using " + slashIce.getEffectName());

        System.out.println("Same effect, different skills:");
        System.out.println("- " + stormFire.getSkillName() + " using " + stormFire.getEffectName());
        System.out.println("- " + shockwaveFire.getSkillName() + " using " + shockwaveFire.getEffectName());
        System.out.println("Additional combo:");
        System.out.println("- " + piercePhysical.getSkillName() + " using " + piercePhysical.getEffectName());
        System.out.println("- " + curseShadow.getSkillName() + " using " + curseShadow.getEffectName());

        RaidEngine engine = new RaidEngine().setRandomSeed(67L);
        RaidResult result = engine.runRaid(heroes, enemies, heroSkills, enemySkills);

        System.out.println("--- Raid Result ---");
        System.out.println();

        for (String line : result.getLog()) System.out.println(line);
        System.out.println();

        System.out.println("--- Final Team Structures ---");
        heroes.printTree("");
        enemies.printTree("");

        System.out.println("=== Demo Complete ===");
    }
}

package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.*;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, List<Skill> teamASkills, List<Skill> teamBSkills) {
        RaidResult result = new RaidResult();
        result.addLine("Raid started: " + teamA.getName() + " vs " + teamB.getName());

        int rounds = 0;
        while (teamA.isAlive() && teamB.isAlive()) {
            rounds++;
            result.addLine("Round " + rounds + ":");

            if (!teamA.isAlive() || teamA.getAttackPower() <= 0) {
                result.addLine(teamA.getName() + " cant attack.");
            } 

            else {
                int healthBefore = teamB.getHealth();
                Skill teamASkill = teamASkills.get(random.nextInt(teamASkills.size()));
                teamASkill.cast(teamB);
                int totalDamage = healthBefore - teamB.getHealth();

                result.addLine(teamA.getName() + " uses " + teamASkill.getSkillName() + " (" + teamASkill.getEffectName() + ") on " + teamB.getName() + " for " + totalDamage + " damage.");

                if (!teamB.isAlive()) {
                    result.addLine(teamB.getName() + " has been defeated.");
                }
            }

            if (teamB.isAlive()) {
                if (!teamB.isAlive() || teamB.getAttackPower() <= 0) {
                    result.addLine(teamB.getName() + " cant attack.");
                } 
                
                else {
                    int healthBefore = teamA.getHealth();
                    Skill teamBSkill = teamBSkills.get(random.nextInt(teamBSkills.size()));
                    teamBSkill.cast(teamA);
                    int totalDamage = healthBefore - teamA.getHealth();

                    result.addLine(teamB.getName() + " uses " + teamBSkill.getSkillName() + " (" + teamBSkill.getEffectName() + ") on " + teamA.getName() + " for " + totalDamage + " damage.");

                    if (!teamA.isAlive()) {
                        result.addLine(teamA.getName() + " has been defeated.");
                    }
                }
            }

            result.addLine("Status: " + teamA.getName() + " HP = " + teamA.getHealth() + ", " + teamB.getName() + " HP=" + teamB.getHealth());
        }

        result.setRounds(rounds);
        if (teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner(teamA.getName());
        } 
        
        else if (teamB.isAlive() && !teamA.isAlive()) {
            result.setWinner(teamB.getName());
        } 
        
        else if (!teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner("Draw");
        } 
        
        else if (teamA.getHealth() == teamB.getHealth()) {
            result.setWinner("Draw");
        } 
        
        else {
            result.setWinner(teamA.getHealth() > teamB.getHealth() ? teamA.getName() : teamB.getName());
        }

        System.out.println();
        result.addLine("Battle finished. Winner: " + result.getWinner());
        result.addLine("Total rounds: " + rounds);
        return result;
    }
}

package com.narxoz.rpg.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyComposite implements CombatNode {
    private final String name;
    private final List<CombatNode> children = new ArrayList<>();

    public PartyComposite(String name) {
        this.name = name;
    }

    public void add(CombatNode node) {
        children.add(node);
    }

    public void remove(CombatNode node) {
        children.remove(node);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        int totalHealth = 0;
        for (CombatNode child : children) {
            totalHealth += child.getHealth();
        }
        return totalHealth;
    }

    @Override
    public int getAttackPower() {
        int totalAttack = 0;
        for (CombatNode child : children) {
            totalAttack += child.getAttackPower();
        }
        return totalAttack;
    }

    @Override
    public void takeDamage(int amount) {
        int damage = Math.max(0, amount);
        List<CombatNode> aliveChildren = getAliveChildren();
        if (damage == 0 || aliveChildren.isEmpty()) {
            return;
        }

        int sharedDamage = damage / aliveChildren.size();
        int remainder = damage % aliveChildren.size();

        for (CombatNode child : aliveChildren) {
            int childDamage = sharedDamage;
            if (remainder > 0) {
                childDamage++;
                remainder--;
            }
            child.takeDamage(childDamage);
        }
    }

    @Override
    public boolean isAlive() {
        for (CombatNode child : children) {
            if (child.isAlive()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<CombatNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + "+ " + name + " [HP=" + getHealth() + ", ATK=" + getAttackPower() + "]");
        String childIndent = indent + "  ";
        for (CombatNode child : children) {
            child.printTree(childIndent);
        }
    }

    private List<CombatNode> getAliveChildren() {
        List<CombatNode> aliveChildren = new ArrayList<>();
        for (CombatNode child : children) {
            if (child.isAlive()) {
                aliveChildren.add(child);
            }
        }
        return aliveChildren;
    }
}

package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;

public class AreaSkill extends Skill {
    public AreaSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    private void applyAOE(CombatNode node, int damage) {
        if (node.getChildren().isEmpty()) {
            node.takeDamage(damage);
            return;
        }

        for (CombatNode child : node.getChildren()) {
            applyAOE(child, damage);
        }
    }

    @Override
    public void cast(CombatNode target) {
        if (target == null || !target.isAlive()) {
            return;
        }

        applyAOE(target, resolvedDamage());
    }
}

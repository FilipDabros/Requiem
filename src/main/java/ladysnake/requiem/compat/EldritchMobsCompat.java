/*
 * Requiem
 * Copyright (C) 2017-2022 Ladysnake
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses>.
 *
 * Linking this mod statically or dynamically with other
 * modules is making a combined work based on this mod.
 * Thus, the terms and conditions of the GNU General Public License cover the whole combination.
 *
 * In addition, as a special exception, the copyright holders of
 * this mod give you permission to combine this mod
 * with free software programs or libraries that are released under the GNU LGPL
 * and with code included in the standard release of Minecraft under All Rights Reserved (or
 * modified versions of such code, with unchanged license).
 * You may copy and distribute such a system following the terms of the GNU GPL for this mod
 * and the licenses of the other code concerned.
 *
 * Note that people who make modified versions of this mod are not obligated to grant
 * this special exception for their modified versions; it is their choice whether to do so.
 * The GNU General Public License gives permission to release a modified version without this exception;
 * this exception also makes it possible to release a modified version which carries forward this exception.
 */
package ladysnake.requiem.compat;

import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import ladysnake.requiem.Requiem;
import ladysnake.requiem.api.v1.annotation.CalledThroughReflection;
import ladysnake.requiem.api.v1.event.requiem.PossessionStartCallback;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;

public final class EldritchMobsCompat implements PossessionStartCallback {
    @CalledThroughReflection
    public static void init() {
        PossessionStartCallback.EVENT.register(Requiem.id("eldritch_mobs"), new EldritchMobsCompat());
    }

    @Override
    public Result onPossessionAttempted(MobEntity target, PlayerEntity possessor, boolean simulate) {
        ComponentProvider t = ComponentProvider.fromEntity(target);
        if (EldritchMobsMod.isEldritch(t) || EldritchMobsMod.isElite(t) || EldritchMobsMod.isUltra(t)) {
            if (!possessor.world.isClient) possessor.sendMessage(new TranslatableText("requiem:possess.incompatible_body"), true);
            return Result.DENY;
        }
        return Result.PASS;
    }
}

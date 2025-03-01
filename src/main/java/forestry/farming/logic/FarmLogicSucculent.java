/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser Public License v3 which accompanies this distribution, and is available
 * at http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to: SirSengir (original work), CovertJaguar, Player, Binnie,
 * MysteriousAges
 ******************************************************************************/
package forestry.farming.logic;

import java.util.Collection;
import java.util.Stack;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.farming.FarmDirection;
import forestry.api.farming.Farmables;
import forestry.api.farming.ICrop;
import forestry.api.farming.IFarmHousing;
import forestry.api.farming.IFarmable;
import forestry.core.utils.ItemStackUtil;
import forestry.core.utils.vect.Vect;

public class FarmLogicSucculent extends FarmLogic {

    private final IFarmable[] germlings;

    public FarmLogicSucculent(IFarmHousing housing) {
        super(housing);
        Collection<IFarmable> farmables = Farmables.farmables.get(FarmableReference.Succulentes.get());
        germlings = farmables.toArray(new IFarmable[farmables.size()]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon() {
        return Items.dye.getIconFromDamage(2);
    }

    @Override
    public String getName() {
        if (isManual) {
            return "Manual Succulent Farm";
        } else {
            return "Managed Succulent Farm";
        }
    }

    @Override
    public int getFertilizerConsumption() {
        return 10;
    }

    @Override
    public int getWaterConsumption(float hydrationModifier) {
        return 1;
    }

    @Override
    public boolean isAcceptedResource(ItemStack itemstack) {
        if (isManual) {
            return false;
        }

        return ItemStackUtil.equals(Blocks.sand, itemstack);
    }

    @Override
    public boolean isAcceptedGermling(ItemStack itemstack) {
        if (isManual) {
            return false;
        }

        return ItemStackUtil.equals(Blocks.cactus, itemstack);
    }

    @Override
    public boolean isAcceptedWindfall(ItemStack stack) {
        return false;
    }

    @Override
    public Collection<ItemStack> collect() {
        return null;
    }

    @Override
    public boolean cultivate(int x, int y, int z, FarmDirection direction, int extent) {
        return false;
    }

    @Override
    public Collection<ICrop> harvest(int x, int y, int z, FarmDirection direction, int extent) {
        World world = getWorld();

        Stack<ICrop> crops = new Stack<>();
        for (int i = 0; i < extent; i++) {
            Vect position = translateWithOffset(x, y + 1, z, direction, i);
            for (IFarmable seed : germlings) {
                ICrop crop = seed.getCropAt(world, position.x, position.y, position.z);
                if (crop != null) {
                    crops.push(crop);
                }
            }
        }
        return crops;
    }
}

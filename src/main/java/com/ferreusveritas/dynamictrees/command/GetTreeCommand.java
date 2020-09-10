package com.ferreusveritas.dynamictrees.command;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.worldgen.JoCode;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public final class GetTreeCommand extends SubCommand {

    public GetTreeCommand () {
        this.takesCoordinates = true;
    }

    @Override
    protected String getName () {
        return CommandConstants.GET_TREE;
    }

    @Override
    protected int execute (CommandContext<CommandSource> context) {
        // TODO: Find way to get block player is looking at both server-side and client-side.
        this.sendMessage(context, new TranslationTextComponent("commands.dynamictrees.nocoords", "tree"));
        return 1;
    }

    @Override
    protected int executeWithCoords(CommandContext<CommandSource> context, World worldIn, BlockPos blockPos) {
        Species species = TreeHelper.getBestGuessSpecies(worldIn, blockPos);

        if (species == Species.NULLSPECIES) {
            this.sendMessage(context, new TranslationTextComponent("commands.dynamictrees.gettree.failure"));
            return 0;
        }

        String code = TreeHelper.getJoCode(worldIn, blockPos).map(JoCode::toString).orElse("?");

        this.sendMessage(context, new TranslationTextComponent("commands.dynamictrees.gettree.success", species.toString(), code));
        return 1;
    }

}

package com.ryderbelserion.map.marker.mobs;

import com.ryderbelserion.map.Pl3xMapExtras;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.image.IconImage;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Mob;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Strider;
import org.bukkit.entity.TraderLlama;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.function.Function;
import java.util.logging.Level;

public enum Icon {

    ALLAY(EntityType.ALLAY),
    ARMADILLO(EntityType.ARMADILLO),
    AXOLOTL_BLUE(EntityType.AXOLOTL, Icon.<Axolotl>predicate(mob -> mob.getVariant() == Axolotl.Variant.BLUE)),
    AXOLOTL_BROWN(EntityType.AXOLOTL, Icon.<Axolotl>predicate(mob -> mob.getVariant() == Axolotl.Variant.WILD)),
    AXOLOTL_CYAN(EntityType.AXOLOTL, Icon.<Axolotl>predicate(mob -> mob.getVariant() == Axolotl.Variant.CYAN)),
    AXOLOTL_GOLD(EntityType.AXOLOTL, Icon.<Axolotl>predicate(mob -> mob.getVariant() == Axolotl.Variant.GOLD)),
    AXOLOTL_LEUCISTIC(EntityType.AXOLOTL, Icon.<Axolotl>predicate(mob -> mob.getVariant() == Axolotl.Variant.LUCY)),
    BAT(EntityType.BAT),
    BEE(EntityType.BEE, Icon.<Bee>predicate(mob -> mob.getAnger() <= 0)),
    BEE_ANGRY(EntityType.BEE, Icon.<Bee>predicate(mob -> mob.getAnger() > 0)),
    BLAZE(EntityType.BLAZE),
    BOGGED(EntityType.BOGGED),
    CAMEL(EntityType.CAMEL),
    CAT_BLACK(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.BLACK)),
    CAT_BRITISH_SHORTHAIR(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.BRITISH_SHORTHAIR)),
    CAT_CALICO(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.CALICO)),
    CAT_GINGER(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.RED)),
    CAT_JELLIE(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.JELLIE)),
    CAT_PERSIAN(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.PERSIAN)),
    CAT_RAGDOLL(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.RAGDOLL)),
    CAT_SIAMESE(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.SIAMESE)),
    CAT_TABBY(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.TABBY)),
    CAT_TUXEDO(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.ALL_BLACK)),
    CAT_WHITE(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.WHITE)),
    BREEZE(EntityType.BREEZE),
    CAVE_SPIDER(EntityType.CAVE_SPIDER),
    CHICKEN(EntityType.CHICKEN),
    COD(EntityType.COD),
    COW(EntityType.COW),
    CREEPER(EntityType.CREEPER, Icon.<Creeper>predicate(mob -> !mob.isPowered())),
    CREEPER_POWERED(EntityType.CREEPER, Icon.predicate(Creeper::isPowered)),
    DOLPHIN(EntityType.DOLPHIN),
    DONKEY(EntityType.DONKEY),
    DROWNED(EntityType.DROWNED),
    ELDER_GUARDIAN(EntityType.ELDER_GUARDIAN),
    ENDERMAN(EntityType.ENDERMAN),
    ENDERMITE(EntityType.ENDERMITE),
    ENDER_DRAGON(EntityType.ENDER_DRAGON),
    EVOKER(EntityType.EVOKER),
    FOX(EntityType.FOX, Icon.<Fox>predicate(mob -> mob.getFoxType() == Fox.Type.RED)),
    FOX_SNOW(EntityType.FOX, Icon.<Fox>predicate(mob -> mob.getFoxType() == Fox.Type.SNOW)),
    FROG_COLD(EntityType.FROG, Icon.<Frog>predicate(mob -> mob.getVariant() == Frog.Variant.COLD)),
    FROG_TEMPERATE(EntityType.FROG, Icon.<Frog>predicate(mob -> mob.getVariant() == Frog.Variant.TEMPERATE)),
    FROG_WARM(EntityType.FROG, Icon.<Frog>predicate(mob -> mob.getVariant() == Frog.Variant.WARM)),
    GHAST(EntityType.GHAST),
    GIANT(EntityType.GIANT),
    GLOW_SQUID(EntityType.GLOW_SQUID),
    GOAT(EntityType.GOAT),
    GUARDIAN(EntityType.GUARDIAN),
    HOGLIN(EntityType.HOGLIN),
    //HORSE(EntityType.HORSE),

    HORSE_BROWN(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BROWN)),
    HORSE_BROWN_WHITE_FIELD(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BROWN && mob.getStyle() == Horse.Style.WHITEFIELD)),
    HORSE_BROWN_WHITE_SPOTTED(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BROWN && mob.getStyle() == Horse.Style.WHITE_DOTS)),

    HORSE_CREAMY(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.CREAMY)),
    HORSE_CREAMY_WHITE_FIELD(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.CREAMY && mob.getStyle() == Horse.Style.WHITEFIELD)),
    HORSE_CREAMY_WHITE_SPOTTED(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.CREAMY && mob.getStyle() == Horse.Style.WHITE_DOTS)),

    HORSE_GRAY(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.GRAY)),
    HORSE_GRAY_WHITE_FIELD(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.GRAY && mob.getStyle() == Horse.Style.WHITEFIELD)),
    HORSE_GRAY_WHITE_SPOTTED(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.GRAY && mob.getStyle() == Horse.Style.WHITE_DOTS)),

    HORSE_WHITE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.WHITE)),
    HORSE_WHITE_WHITE_FIELD(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.WHITE && mob.getStyle() == Horse.Style.WHITEFIELD)),
    HORSE_WHITE_WHITE_SPOTTED(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.WHITE && mob.getStyle() == Horse.Style.WHITE_DOTS)),

    HORSE_BLACK(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BLACK)),
    HORSE_BLACK_WHITE_FIELD(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BLACK && mob.getStyle() == Horse.Style.WHITEFIELD)),
    HORSE_BLACK_WHITE_SPOTTED(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BLACK && mob.getStyle() == Horse.Style.WHITE_DOTS)),

    HORSE_CHESTNUT(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.CHESTNUT)),
    HORSE_CHESTNUT_WHITE_FIELD(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.CHESTNUT && mob.getStyle() == Horse.Style.WHITEFIELD)),
    HORSE_CHESTNUT_WHITE_SPOTTED(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.CHESTNUT && mob.getStyle() == Horse.Style.WHITE_DOTS)),

    HORSE_DARK_BROWN(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.DARK_BROWN)),
    HORSE_DARK_BROWN_WHITE_FIELD(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.DARK_BROWN && mob.getStyle() == Horse.Style.WHITEFIELD)),
    HORSE_DARK_BROWN_WHITE_SPOTTED(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.DARK_BROWN && mob.getStyle() == Horse.Style.WHITE_DOTS)),

    HUSK(EntityType.HUSK),
    ILLUSIONER(EntityType.ILLUSIONER),
    IRON_GOLEM(EntityType.IRON_GOLEM),
    LLAMA_BROWN(EntityType.LLAMA, Icon.<Llama>predicate(mob -> mob.getColor() == Llama.Color.BROWN)),
    LLAMA_CREAMY(EntityType.LLAMA, Icon.<Llama>predicate(mob -> mob.getColor() == Llama.Color.CREAMY)),
    LLAMA_GREY(EntityType.LLAMA, Icon.<Llama>predicate(mob -> mob.getColor() == Llama.Color.GRAY)),
    LLAMA_WHITE(EntityType.LLAMA, Icon.<Llama>predicate(mob -> mob.getColor() == Llama.Color.WHITE)),
    MAGMA_CUBE(EntityType.MAGMA_CUBE),
    MULE(EntityType.MULE),
    MUSHROOM_COW(EntityType.COW, Icon.<MushroomCow>predicate(mob -> mob.getVariant() == MushroomCow.Variant.RED)),
    MUSHROOM_COW_BROWN(EntityType.COW, Icon.<MushroomCow>predicate(mob -> mob.getVariant() == MushroomCow.Variant.BROWN)),
    OCELOT(EntityType.OCELOT),
    PANDA_AGGRESSIVE(EntityType.PANDA, Icon.<Panda>predicate(mob -> getTrait(mob) == Panda.Gene.AGGRESSIVE)),
    PANDA_BROWN(EntityType.PANDA, Icon.<Panda>predicate(mob -> getTrait(mob) == Panda.Gene.BROWN)),
    PANDA_LAZY(EntityType.PANDA, Icon.<Panda>predicate(mob -> getTrait(mob) == Panda.Gene.LAZY)),
    PANDA_NORMAL(EntityType.PANDA, Icon.<Panda>predicate(mob -> getTrait(mob) == Panda.Gene.NORMAL)),
    PANDA_PLAYFUL(EntityType.PANDA, Icon.<Panda>predicate(mob -> getTrait(mob) == Panda.Gene.PLAYFUL)),
    PANDA_WEAK(EntityType.PANDA, Icon.<Panda>predicate(mob -> getTrait(mob) == Panda.Gene.WEAK)),
    PANDA_WORRIED(EntityType.PANDA, Icon.<Panda>predicate(mob -> getTrait(mob) == Panda.Gene.WORRIED)),
    PARROT_BLUE(EntityType.PARROT, Icon.<Parrot>predicate(mob -> mob.getVariant() == Parrot.Variant.BLUE)),
    PARROT_CYAN(EntityType.PARROT, Icon.<Parrot>predicate(mob -> mob.getVariant() == Parrot.Variant.CYAN)),
    PARROT_GREEN(EntityType.PARROT, Icon.<Parrot>predicate(mob -> mob.getVariant() == Parrot.Variant.GREEN)),
    PARROT_GREY(EntityType.PARROT, Icon.<Parrot>predicate(mob -> mob.getVariant() == Parrot.Variant.GRAY)),
    PARROT_RED(EntityType.PARROT, Icon.<Parrot>predicate(mob -> mob.getVariant() == Parrot.Variant.RED)),
    PHANTOM(EntityType.PHANTOM),
    PIG(EntityType.PIG),
    PIGLIN(EntityType.PIGLIN),
    PIGLIN_BRUTE(EntityType.PIGLIN_BRUTE),
    PILLAGER(EntityType.PILLAGER),
    POLAR_BEAR(EntityType.POLAR_BEAR),
    PUFFERFISH(EntityType.PUFFERFISH),
    RABBIT_BLACK(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> mob.getRabbitType() == Rabbit.Type.BLACK)),
    RABBIT_BROWN(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> mob.getRabbitType() == Rabbit.Type.BROWN)),
    RABBIT_CREAM(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> mob.getRabbitType() == Rabbit.Type.GOLD)),
    RABBIT_KILLER(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> mob.getRabbitType() == Rabbit.Type.THE_KILLER_BUNNY)),
    RABBIT_SALT(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> mob.getRabbitType() == Rabbit.Type.SALT_AND_PEPPER)),
    RABBIT_SPOTTED(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> mob.getRabbitType() == Rabbit.Type.BLACK_AND_WHITE)),
    @SuppressWarnings("deprecation")
    RABBIT_TOAST(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> "Toast".equals(mob.getCustomName()))),
    RABBIT_WHITE(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> mob.getRabbitType() == Rabbit.Type.WHITE)),
    RAVAGER(EntityType.RAVAGER),
    SALMON(EntityType.SALMON),
    SHEEP_BLACK(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.BLACK)),
    SHEEP_BLUE(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.BLUE)),
    SHEEP_BROWN(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.BROWN)),
    SHEEP_CYAN(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.CYAN)),
    SHEEP_GREY(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.GRAY)),
    SHEEP_GREEN(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.GREEN)),
    SHEEP_LIGHT_BLUE(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.LIGHT_BLUE)),
    SHEEP_LIGHT_GREY(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.LIGHT_GRAY)),
    SHEEP_LIME(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.LIME)),
    SHEEP_MAGENTA(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.MAGENTA)),
    SHEEP_ORANGE(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.ORANGE)),
    SHEEP_PINK(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.PINK)),
    SHEEP_PURPLE(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.PURPLE)),
    SHEEP_RED(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.RED)),
    SHEEP_WHITE(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.WHITE)),
    SHEEP_YELLOW(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.YELLOW)),
    SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == null)),
    SHULKER_BLACK(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.BLACK)),
    SHULKER_BLUE(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.BLUE)),
    SHULKER_BROWN(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.BROWN)),
    SHULKER_CYAN(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.CYAN)),
    SHULKER_GREY(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.GRAY)),
    SHULKER_GREEN(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.GREEN)),
    SHULKER_LIGHT_BLUE(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.LIGHT_BLUE)),
    SHULKER_LIGHT_GREY(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.LIGHT_GRAY)),
    SHULKER_LIME(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.LIME)),
    SHULKER_MAGENTA(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.MAGENTA)),
    SHULKER_ORANGE(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.ORANGE)),
    SHULKER_PINK(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.PINK)),
    SHULKER_PURPLE(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.PURPLE)),
    SHULKER_RED(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.RED)),
    SHULKER_WHITE(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.WHITE)),
    SHULKER_YELLOW(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.YELLOW)),
    SILVERFISH(EntityType.SILVERFISH),
    SKELETON(EntityType.SKELETON),
    SKELETON_HORSE(EntityType.SKELETON_HORSE),
    SLIME(EntityType.SLIME),
    SNIFFER(EntityType.SNIFFER),
    SNOW_GOLEM(EntityType.SNOW_GOLEM, Icon.<Snowman>predicate(mob -> !mob.isDerp())),
    SNOW_GOLEM_SHEARED(EntityType.SNOW_GOLEM, Icon.predicate(Snowman::isDerp)),
    SPIDER(EntityType.SPIDER),
    SQUID(EntityType.SQUID),
    STRAY(EntityType.STRAY),
    STRIDER_COLD(EntityType.STRIDER, Icon.predicate(Strider::isShivering)),
    STRIDER_WARM(EntityType.STRIDER, Icon.<Strider>predicate(mob -> !mob.isShivering())),
    TADPOLE(EntityType.TADPOLE),
    TRADER_LLAMA_BROWN(EntityType.TRADER_LLAMA, Icon.<TraderLlama>predicate(mob -> mob.getColor() == Llama.Color.BROWN)),
    TRADER_LLAMA_CREAMY(EntityType.TRADER_LLAMA, Icon.<TraderLlama>predicate(mob -> mob.getColor() == Llama.Color.CREAMY)),
    TRADER_LLAMA_GREY(EntityType.TRADER_LLAMA, Icon.<TraderLlama>predicate(mob -> mob.getColor() == Llama.Color.GRAY)),
    TRADER_LLAMA_WHITE(EntityType.TRADER_LLAMA, Icon.<TraderLlama>predicate(mob -> mob.getColor() == Llama.Color.WHITE)),
    TROPICAL_FISH(EntityType.TROPICAL_FISH),
    TURTLE(EntityType.TURTLE),
    VEX(EntityType.VEX),

    VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.NONE)),

    VILLAGER_TUNDRA(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getVillagerType() == Villager.Type.SNOW)),
    VILLAGER_SAVANNA(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getVillagerType() == Villager.Type.SAVANNA)),
    VILLAGER_DESERT(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getVillagerType() == Villager.Type.DESERT)),

    VILLAGER_ARMORER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.ARMORER)),
    VILLAGER_BUTCHER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.BUTCHER)),
    VILLAGER_CLERIC(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.CLERIC)),
    //VILLAGER_CARTOGRAPHER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.CARTOGRAPHER)),
    VILLAGER_FARMER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.FARMER)),
    VILLAGER_FISHERMAN(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.FISHERMAN)),
    VILLAGER_FLETCHER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.FLETCHER)),
    //VILLAGER_LEATHERWORKER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.LEATHERWORKER)),
    VILLAGER_LIBRARIAN(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.LIBRARIAN)),
    //VILLAGER_MASON(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.MASON)),
    //VILLAGER_NITWIT(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.NITWIT)),
    VILLAGER_SHEPHERD(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.SHEPHERD)),
    //VILLAGER_TOOLSMITH(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.TOOLSMITH)),
    VILLAGER_WEAPONSMITH(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.WEAPONSMITH)),
    VINDICATOR(EntityType.VINDICATOR),
    WANDERING_TRADER(EntityType.WANDERING_TRADER),
    WARDEN(EntityType.WARDEN),
    WITCH(EntityType.WITCH),
    WITHER(EntityType.WITHER),
    WITHER_SKELETON(EntityType.WITHER_SKELETON),

    WOLF_ASHEN(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.ASHEN && !mob.isTamed())),
    WOLF_BLACK(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.BLACK && !mob.isTamed())),
    WOLF_CHESTNUT(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.CHESTNUT && !mob.isTamed())),
    WOLF_PALE(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.PALE && !mob.isTamed())),
    WOLF_RUSTY(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.RUSTY && !mob.isTamed())),
    WOLF_SNOWY(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.SNOWY && !mob.isTamed())),
    WOLF_SPOTTED(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.SPOTTED && !mob.isTamed())),
    WOLF_STRIPED(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.STRIPED && !mob.isTamed())),
    WOLF_WOODS(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.WOODS && !mob.isTamed())),

    WOLF_ANGRY_ASHEN(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.ASHEN && mob.isAngry())),
    WOLF_ANGRY_BLACK(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.BLACK && mob.isAngry())),
    WOLF_ANGRY_CHESTNUT(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.CHESTNUT && mob.isAngry())),
    WOLF_ANGRY_PALE(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.PALE && mob.isAngry())),
    WOLF_ANGRY_RUSTY(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.RUSTY && mob.isAngry())),
    WOLF_ANGRY_SNOWY(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.SNOWY && mob.isAngry())),
    WOLF_ANGRY_SPOTTED(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.SPOTTED && mob.isAngry())),
    WOLF_ANGRY_STRIPED(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.STRIPED && mob.isAngry())),
    WOLF_ANGRY_WOODS(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.WOODS && mob.isAngry())),

    WOLF_TAMED(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.isTamed() && !mob.isAngry())),

    WOLF_TAME_ASHEN(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.ASHEN && mob.isTamed())),
    WOLF_TAME_BLACK(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.BLACK && mob.isTamed())),
    WOLF_TAME_CHESTNUT(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.CHESTNUT && mob.isTamed())),
    WOLF_TAME_PALE(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.PALE && mob.isTamed())),
    WOLF_TAME_RUSTY(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.RUSTY && mob.isTamed())),
    WOLF_TAME_SNOWY(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.SNOWY && mob.isTamed())),
    WOLF_TAME_SPOTTED(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.SPOTTED && mob.isTamed())),
    WOLF_TAME_STRIPED(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.STRIPED && mob.isTamed())),
    WOLF_TAME_WOODS(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.WOODS && mob.isTamed())),

    ZOGLIN(EntityType.ZOGLIN),
    ZOMBIE(EntityType.ZOMBIE),
    ZOMBIE_HORSE(EntityType.ZOMBIE_HORSE),
    ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER),
    ZOMBIFIED_PIGLIN(EntityType.ZOMBIFIED_PIGLIN);

    private final String name;
    private final String key;
    private final EntityType type;
    private final Function<Mob, Boolean> predicate;

    Icon(@NotNull EntityType type) {
        this(type, null);
    }

    @SuppressWarnings("unchecked")
    <T extends Mob> Icon(@NotNull EntityType type, @Nullable Function<T, Boolean> predicate) {
        this.name = name().toLowerCase(Locale.ROOT);
        this.key = String.format("pl3xmap_%s_mob", name);
        this.type = type;
        this.predicate = (Function<Mob, Boolean>) predicate;
    }

    public @NotNull String getKey() {
        return this.key;
    }

    public @NotNull EntityType getType() {
        return this.type;
    }

    public static @NotNull Icon get(@NotNull Mob mob) {
        for (Icon icon : values()) {
            if (icon.getType() == mob.getType()) {
                if (icon.predicate == null || icon.predicate.apply(mob)) {
                    return icon;
                }
            }
        }

        if (mob instanceof Wolf wolf) {
            Bukkit.getLogger().warning("Variant: " + wolf.getVariant().getKey().getKey());
        }

        throw new IllegalStateException();
    }

    private static <T extends Mob> @NotNull Function<T, Boolean> predicate(@NotNull Function<T, Boolean> predicate) {
        return predicate;
    }

    private static @NotNull Horse.Style getHorse(@NotNull Horse horse) {
        return horse.getStyle();
    }

    private static @NotNull Panda.Gene getTrait(@NotNull Panda panda) {
        Panda.Gene mainGene = panda.getMainGene();

        if (!mainGene.isRecessive()) {
            return mainGene;
        }

        return switch (mainGene) {
            case BROWN -> Panda.Gene.BROWN;
            case WEAK -> Panda.Gene.WEAK;
            default -> Panda.Gene.NORMAL;
        };
    }

    public static void register() {
        Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

        Path iconFolder = plugin.getDataFolder().toPath().resolve("mobs");

        for (Icon icon : values()) {
            String fileName = String.format("icons%s%s.png", File.separator, icon.name);

            File file = iconFolder.resolve(fileName).toFile();

            try {
                Pl3xMap.api().getIconRegistry().register(new IconImage(icon.key, ImageIO.read(file), "png"));
            } catch (IOException e) {
                plugin.getLogger().log(Level.WARNING,"Failed to register icon (" + icon.name + ") " + fileName, e);
            }
        }
    }
}
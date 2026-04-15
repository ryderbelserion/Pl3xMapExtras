package com.ryderbelserion.map.marker.mobs;

import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.map.Pl3xMapExtras;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.image.IconImage;
import net.pl3x.map.core.registry.IconRegistry;
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
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

public enum Icon {

    ALLAY(EntityType.ALLAY),

    BLUE_AXOLOTL(EntityType.AXOLOTL, Icon.<Axolotl>predicate(mob -> mob.getVariant() == Axolotl.Variant.BLUE), "axolotl"),
    WILD_AXOLOTL(EntityType.AXOLOTL, Icon.<Axolotl>predicate(mob -> mob.getVariant() == Axolotl.Variant.WILD), "axolotl"),
    CYAN_AXOLOTL(EntityType.AXOLOTL, Icon.<Axolotl>predicate(mob -> mob.getVariant() == Axolotl.Variant.CYAN), "axolotl"),
    GOLD_AXOLOTL(EntityType.AXOLOTL, Icon.<Axolotl>predicate(mob -> mob.getVariant() == Axolotl.Variant.GOLD), "axolotl"),
    PINK_AXOLOTL(EntityType.AXOLOTL, Icon.<Axolotl>predicate(mob -> mob.getVariant() == Axolotl.Variant.LUCY), "axolotl"),

    POLAR_BEAR(EntityType.POLAR_BEAR, "bear"),

    ANGRY_BEE(EntityType.BEE, Icon.<Bee>predicate(mob -> mob.getAnger() > 0), "bee"),
    BEE(EntityType.BEE, Icon.<Bee>predicate(mob -> mob.getAnger() <= 0), "bee"),

    BREEZE(EntityType.BREEZE),

    CAMEL(EntityType.CAMEL),

    BLACK_CAT(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.BLACK), "cat"),
    BRITISH_SHORTHAIR_CAT(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.BRITISH_SHORTHAIR), "cat"),
    CALICO_CAT(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.CALICO), "cat"),
    RED_CAT(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.RED), "cat"),
    JELLIE_CAT(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.JELLIE), "cat"),
    PERSIAN_CAT(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.PERSIAN), "cat"),
    RAGDOLL_CAT(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.RAGDOLL), "cat"),
    SIAMESE_CAT(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.SIAMESE), "cat"),
    TABBY_CAT(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.TABBY), "cat"),
    ALL_BLACK_CAT(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.ALL_BLACK), "cat"),
    WHITE_CAT(EntityType.CAT, Icon.<Cat>predicate(mob -> mob.getCatType() == Cat.Type.WHITE), "cat"),

    COW(EntityType.COW),
    MOOSHROOM(EntityType.MOOSHROOM, Icon.<MushroomCow>predicate(mob -> mob.getVariant() == MushroomCow.Variant.RED), "cow"),
    BROWN_MOOSHROOM(EntityType.MOOSHROOM, Icon.<MushroomCow>predicate(mob -> mob.getVariant() == MushroomCow.Variant.BROWN), "cow"),

    CREEPER(EntityType.CREEPER, Icon.<Creeper>predicate(mob -> !mob.isPowered())),
    CHARGED_CREEPER(EntityType.CREEPER, Icon.predicate(Creeper::isPowered), "creeper"),

    ENDER_DRAGON(EntityType.ENDER_DRAGON, "enderdragon"),
    ENDERMAN(EntityType.ENDERMAN),

    TROPICAL_FISH(EntityType.TROPICAL_FISH, "fish"),
    PUFFERFISH(EntityType.PUFFERFISH, "fish"),
    SALMON(EntityType.SALMON, "fish"),
    COD(EntityType.COD, "fish"),

    FOX(EntityType.FOX, Icon.<Fox>predicate(mob -> mob.getFoxType() == Fox.Type.RED)),
    SNOW_FOX(EntityType.FOX, Icon.<Fox>predicate(mob -> mob.getFoxType() == Fox.Type.SNOW), "fox"),

    TEMPERATE_FROG(EntityType.FROG, Icon.<Frog>predicate(mob -> mob.getVariant() == Frog.Variant.TEMPERATE), "frog"),
    COLD_FROG(EntityType.FROG, Icon.<Frog>predicate(mob -> mob.getVariant() == Frog.Variant.COLD), "frog"),
    WARM_FROG(EntityType.FROG, Icon.<Frog>predicate(mob -> mob.getVariant() == Frog.Variant.WARM), "frog"),

    GHAST(EntityType.GHAST),

    GOAT(EntityType.GOAT),

    HOGLIN(EntityType.HOGLIN),
    ZOMBIFIED_HOGLIN(EntityType.ZOGLIN, "hoglin"),

    SKELETON_HORSE(EntityType.SKELETON_HORSE, "horse"),
    ZOMBIE_HORSE(EntityType.ZOMBIE_HORSE, "horse"),
    DONKEY(EntityType.DONKEY, "horse"),
    HORSE(EntityType.HORSE),
    MULE(EntityType.MULE, "horse"),

    BLACK_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BLACK), "horse/black"),
    BLACK_WHITE_FIELD_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BLACK && mob.getStyle() == Horse.Style.WHITEFIELD), "horse/black"),

    BLACK_BLACK_SPOTTED_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BLACK && mob.getStyle() == Horse.Style.WHITE_DOTS), "horse/black"), // 16x16
    BLACK_WHITE_SPOTTED_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.WHITE && mob.getStyle() == Horse.Style.WHITE_DOTS), "horse/black"),

    BROWN_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BROWN), "horse/brown"),
    BROWN_WHITE_FIELD_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BROWN && mob.getStyle() == Horse.Style.WHITEFIELD), "horse/brown"),

    BROWN_BLACK_SPOTTED_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BLACK && mob.getStyle() == Horse.Style.WHITE_DOTS), "horse/brown"), // 16x16
    BROWN_WHITE_SPOTTED_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BROWN && mob.getStyle() == Horse.Style.WHITE_DOTS), "horse/brown"),

    CHESTNUT_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.CHESTNUT), "horse/chestnut"),
    CHESTNUT_WHITE_FIELD_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.CHESTNUT && mob.getStyle() == Horse.Style.WHITEFIELD), "horse/chestnut"),

    CHESTNUT_BLACK_SPOTTED_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BLACK && mob.getStyle() == Horse.Style.WHITE_DOTS), "horse/chestnut"), // 16x16
    CHESTNUT_WHITE_SPOTTED_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.CHESTNUT && mob.getStyle() == Horse.Style.WHITE_DOTS), "horse/chestnut"),

    CREAMY_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.CREAMY), "horse/creamy"),
    CREAMY_WHITE_FIELD_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.CREAMY && mob.getStyle() == Horse.Style.WHITEFIELD), "horse/creamy"),

    CREAMY_BLACK_SPOTTED_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BLACK && mob.getStyle() == Horse.Style.WHITE_DOTS), "horse/creamy"), // 16x16
    CREAMY_WHITE_SPOTTED_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.CREAMY && mob.getStyle() == Horse.Style.WHITE_DOTS), "horse/creamy"),

    DARK_BROWN_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.DARK_BROWN), "horse/dark_brown"),
    DARK_BROWN_WHITE_FIELD_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.DARK_BROWN && mob.getStyle() == Horse.Style.WHITEFIELD), "horse/dark_brown"),

    DARK_BROWN_SPOTTED_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.DARK_BROWN && mob.getStyle() == Horse.Style.WHITE_DOTS), "horse/dark_brown"), // 16x16
    DARK_BROWN_WHITE_SPOTTED_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.DARK_BROWN && mob.getStyle() == Horse.Style.WHITE_DOTS), "horse/dark_brown"),

    GRAY_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.GRAY), "horse/gray"),
    GRAY_WHITE_FIELD_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.GRAY && mob.getStyle() == Horse.Style.WHITEFIELD), "horse/gray"),

    GRAY_BLACK_SPOTTED_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.BLACK && mob.getStyle() == Horse.Style.WHITE_DOTS), "horse/gray"), // 16x16
    GRAY_WHITE_SPOTTED_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.GRAY && mob.getStyle() == Horse.Style.WHITE_DOTS), "horse/gray"),

    WHITE_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.WHITE), "horse/white"),
    WHITE_WHITE_FIELD_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.WHITE && mob.getStyle() == Horse.Style.WHITEFIELD), "horse/white"),

    WHITE_WHITE_SPOTTED_HORSE(EntityType.HORSE, Icon.<Horse>predicate(mob -> mob.getColor() == Horse.Color.WHITE && mob.getStyle() == Horse.Style.WHITE_DOTS), "horse/white"),

    EVOKER(EntityType.EVOKER, "illager"),
    ILLUSIONER(EntityType.ILLUSIONER, "illager"),
    VINDICATOR(EntityType.VINDICATOR, "illager"),
    RAVAGER(EntityType.RAVAGER, "illager"),
    PILLAGER(EntityType.PILLAGER, "illager"),
    VEX(EntityType.VEX, "illager"),

    IRON_GOLEM(EntityType.IRON_GOLEM),

    BROWN_TRADER_LLAMA(EntityType.TRADER_LLAMA, Icon.<Llama>predicate(mob -> mob.getColor() == Llama.Color.BROWN), "llama"),
    BROWN_LLAMA(EntityType.LLAMA, Icon.<Llama>predicate(mob -> mob.getColor() == Llama.Color.BROWN), "llama"),

    CREAMY_TRADER_LLAMA(EntityType.TRADER_LLAMA, Icon.<Llama>predicate(mob -> mob.getColor() == Llama.Color.CREAMY), "llama"),
    CREAMY_LLAMA(EntityType.LLAMA, Icon.<Llama>predicate(mob -> mob.getColor() == Llama.Color.CREAMY), "llama"),

    GREY_TRADER_LLAMA(EntityType.TRADER_LLAMA, Icon.<Llama>predicate(mob -> mob.getColor() == Llama.Color.GRAY), "llama"),
    GREY_LLAMA(EntityType.LLAMA, Icon.<Llama>predicate(mob -> mob.getColor() == Llama.Color.GRAY), "llama"),

    WHITE_TRADER_LLAMA(EntityType.TRADER_LLAMA, Icon.<Llama>predicate(mob -> mob.getColor() == Llama.Color.WHITE), "llama"),
    WHITE_LLAMA(EntityType.LLAMA, Icon.<Llama>predicate(mob -> mob.getColor() == Llama.Color.WHITE), "llama"),

    PANDA(EntityType.PANDA),
    AGGRESSIVE_PANDA(EntityType.PANDA, Icon.<Panda>predicate(mob -> getTrait(mob) == Panda.Gene.AGGRESSIVE), "panda"),
    BROWN_PANDA(EntityType.PANDA, Icon.<Panda>predicate(mob -> getTrait(mob) == Panda.Gene.BROWN), "panda"),
    LAZY_PANDA(EntityType.PANDA, Icon.<Panda>predicate(mob -> getTrait(mob) == Panda.Gene.LAZY), "panda"),
    PLAYFUL_PANDA(EntityType.PANDA, Icon.<Panda>predicate(mob -> getTrait(mob) == Panda.Gene.PLAYFUL), "panda"),
    WEAK_PANDA(EntityType.PANDA, Icon.<Panda>predicate(mob -> getTrait(mob) == Panda.Gene.WEAK), "panda"),
    WORRIED_PANDA(EntityType.PANDA, Icon.<Panda>predicate(mob -> getTrait(mob) == Panda.Gene.WORRIED), "panda"),

    OCELOT(EntityType.OCELOT, "cat"),

    BLUE_PARROT(EntityType.PARROT, Icon.<Parrot>predicate(mob -> mob.getVariant() == Parrot.Variant.BLUE), "parrot"),
    CYAN_PARROT(EntityType.PARROT, Icon.<Parrot>predicate(mob -> mob.getVariant() == Parrot.Variant.CYAN), "parrot"),

    GREEN_PARROT(EntityType.PARROT, Icon.<Parrot>predicate(mob -> mob.getVariant() == Parrot.Variant.GREEN), "parrot"),
    GREY_PARROT(EntityType.PARROT, Icon.<Parrot>predicate(mob -> mob.getVariant() == Parrot.Variant.GRAY), "parrot"),

    RED_PARROT(EntityType.PARROT, Icon.<Parrot>predicate(mob -> mob.getVariant() == Parrot.Variant.RED), "parrot"),

    PIG(EntityType.PIG),

    PIGLIN(EntityType.PIGLIN),
    PIGLIN_BRUTE(EntityType.PIGLIN_BRUTE, "piglin"),
    ZOMBIFIED_PIGLIN(EntityType.ZOMBIFIED_PIGLIN, "piglin"),

    BLACK_RABBIT(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> mob.getRabbitType() == Rabbit.Type.BLACK), "rabbit"),
    BROWN_RABBIT(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> mob.getRabbitType() == Rabbit.Type.BROWN), "rabbit"),
    GOLD_RABBIT(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> mob.getRabbitType() == Rabbit.Type.GOLD), "rabbit"),
    KILLER_RABBIT(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> mob.getRabbitType() == Rabbit.Type.THE_KILLER_BUNNY), "rabbit"),
    SALT_RABBIT(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> mob.getRabbitType() == Rabbit.Type.SALT_AND_PEPPER), "rabbit"),
    SPOTTED_RABBIT(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> mob.getRabbitType() == Rabbit.Type.BLACK_AND_WHITE), "rabbit"),
    @SuppressWarnings("deprecation")
    TOAST_RABBIT(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> "Toast".equals(mob.getCustomName())), "rabbit"),
    WHITE_RABBIT(EntityType.RABBIT, Icon.<Rabbit>predicate(mob -> mob.getRabbitType() == Rabbit.Type.WHITE), "rabbit"),

    BLACK_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.BLACK), "sheep"),
    BLUE_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.BLUE), "sheep"),
    BROWN_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.BROWN), "sheep"),
    CYAN_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.CYAN), "sheep"),
    GREY_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.GRAY), "sheep"),
    GREEN_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.GREEN), "sheep"),
    LIGHT_BLUE_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.LIGHT_BLUE), "sheep"),
    LIGHT_GREY_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.LIGHT_GRAY), "sheep"),
    LIME_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.LIME), "sheep"),
    MAGENTA_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.MAGENTA), "sheep"),
    ORANGE_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.ORANGE), "sheep"),
    PINK_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.PINK), "sheep"),
    PURPLE_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.PURPLE), "sheep"),
    RED_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.RED), "sheep"),
    WHITE_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.WHITE), "sheep"),
    YELLOW_SHEEP(EntityType.SHEEP, Icon.<Sheep>predicate(mob -> mob.getColor() == DyeColor.YELLOW), "sheep"),

    SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == null)),
    BLACK_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.BLACK), "shulker"),
    BLUE_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.BLUE), "shulker"),
    BROWN_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.BROWN), "shulker"),
    CYAN_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.CYAN), "shulker"),
    GREY_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.GRAY), "shulker"),
    GREEN_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.GREEN), "shulker"),
    LIGHT_BLUE_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.LIGHT_BLUE), "shulker"),
    LIGHT_GREY_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.LIGHT_GRAY), "shulker"),
    LIME_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.LIME), "shulker"),
    MAGENTA_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.MAGENTA), "shulker"),
    ORANGE_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.ORANGE), "shulker"),
    PINK_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.PINK), "shulker"),
    PURPLE_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.PURPLE), "shulker"),
    RED_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.RED), "shulker"),
    WHITE_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.WHITE), "shulker"),
    YELLOW_SHULKER(EntityType.SHULKER, Icon.<Shulker>predicate(mob -> mob.getColor() == DyeColor.YELLOW), "shulker"),

    WITHER_SKELETON(EntityType.WITHER_SKELETON, "skeleton"),
    SKELETON(EntityType.SKELETON),
    BOGGED(EntityType.BOGGED, "skeleton"),
    STRAY(EntityType.STRAY, "skeleton"),

    SLIME_OPAQUE(EntityType.SLIME, "slime"),
    MAGMA_CUBE(EntityType.MAGMA_CUBE, "slime"),

    SNIFFER(EntityType.SNIFFER),

    CAVE_SPIDER(EntityType.CAVE_SPIDER, "spider"),
    SPIDER(EntityType.SPIDER),

    GLOW_SQUID(EntityType.GLOW_SQUID, "squid"),
    SQUID(EntityType.SQUID),

    STRIDER_COLD(EntityType.STRIDER, Icon.predicate(Strider::isShivering), "strider"),
    STRIDER_WARM(EntityType.STRIDER, Icon.<Strider>predicate(mob -> !mob.isShivering()), "strider"),

    TADPOLE(EntityType.TADPOLE),

    TURTLE(EntityType.TURTLE),

    TRADER_LLAMA_BROWN(EntityType.TRADER_LLAMA, Icon.<TraderLlama>predicate(mob -> mob.getColor() == Llama.Color.BROWN), "llama"),
    TRADER_LLAMA_CREAMY(EntityType.TRADER_LLAMA, Icon.<TraderLlama>predicate(mob -> mob.getColor() == Llama.Color.CREAMY), "llama"),
    TRADER_LLAMA_GREY(EntityType.TRADER_LLAMA, Icon.<TraderLlama>predicate(mob -> mob.getColor() == Llama.Color.GRAY), "llama"),
    TRADER_LLAMA_WHITE(EntityType.TRADER_LLAMA, Icon.<TraderLlama>predicate(mob -> mob.getColor() == Llama.Color.WHITE), "llama"),

    VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.NONE)),

    TUNDRA_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getVillagerType() == Villager.Type.SNOW), "villager"),
    SAVANNA_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getVillagerType() == Villager.Type.SAVANNA), "villager"),
    DESERT_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getVillagerType() == Villager.Type.DESERT), "villager"),

    SWAMP_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getVillagerType() == Villager.Type.SWAMP), "villager"), // 16x16

    ARMORER_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.ARMORER), "villager"),
    BUTCHER_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.BUTCHER), "villager"),
    CLERIC_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.CLERIC), "villager"),
    //CARTOGRAPHER_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.CARTOGRAPHER), "villager"),
    FARMER_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.FARMER), "villager"),
    FISHERMAN_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.FISHERMAN), "villager"),
    FLETCHER_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.FLETCHER), "villager"),
    //LEATHERWORKER_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.LEATHERWORKER), "villager"),
    LIBRARIAN_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.LIBRARIAN), "villager"),
    //MASON_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.MASON), "villager"),
    //NITWIT_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.NITWIT), "villager"),
    SHEPHERD_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.SHEPHERD), "villager"),
    //TOOLSMITH_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.TOOLSMITH), "villager"),
    WEAPONSMITH_VILLAGER(EntityType.VILLAGER, Icon.<Villager>predicate(mob -> mob.getProfession() == Villager.Profession.WEAPONSMITH), "villager"),

    WARDEN(EntityType.WARDEN),

    WITHER(EntityType.WITHER),
    WITHER_SKULL(EntityType.WITHER_SKULL, "wither"),
    BLUE_WITHER_SKULL(EntityType.WITHER_SKULL, Icon.predicate(Wither::isCharged), "wither"),

    WOLF(EntityType.WOLF),
    ANGRY_WOLF(EntityType.WOLF, Icon.predicate(Wolf::isAngry), "wolf"),
    TAMED_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.isTamed() && !mob.isAngry()), "wolf"),
    
    TAME_ASHEN_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.ASHEN && mob.isTamed()), "wolf/ashen"),
    ANGRY_ASHEN_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.ASHEN && mob.isAngry()), "wolf/ashen"),
    ASHEN_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.ASHEN && !mob.isTamed()), "wolf/ashen"),

    TAME_BLACK_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.BLACK && mob.isTamed()), "wolf/black"),
    ANGRY_BLACK_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.BLACK && mob.isAngry()), "wolf/black"),
    BLACK_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.BLACK && !mob.isTamed()), "wolf/black"),

    TAME_CHESTNUT_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.CHESTNUT && mob.isTamed()), "wolf/chestnut"),
    ANGRY_CHESTNUT_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.CHESTNUT && mob.isAngry()), "wolf/chestnut"),
    CHESTNUT_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.CHESTNUT && !mob.isTamed()), "wolf/chestnut"),
    
    TAME_PALE_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.PALE && mob.isTamed()), "wolf/pale"),
    ANGRY_PALE_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.PALE && mob.isAngry()), "wolf/pale"),
    PALE_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.PALE && !mob.isTamed()), "wolf/pale"),

    TAME_RUSTY_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.RUSTY && mob.isTamed()), "wolf/rusty"),
    ANGRY_RUSTY_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.RUSTY && mob.isAngry()), "wolf/rusty"),
    RUSTY_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.RUSTY && !mob.isTamed()), "wolf/rusty"),

    TAME_SNOWY_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.SNOWY && mob.isTamed()), "wolf/snowy"),
    ANGRY_SNOWY_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.SNOWY && mob.isAngry()), "wolf/snowy"),
    SNOWY_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.SNOWY && !mob.isTamed()), "wolf/snowy"),

    TAME_SPOTTED_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.SPOTTED && mob.isTamed()), "wolf/spotted"),
    ANGRY_SPOTTED_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.SPOTTED && mob.isAngry()), "wolf/spotted"),
    SPOTTED_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.SPOTTED && !mob.isTamed()), "wolf/spotted"),

    TAME_STRIPED_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.STRIPED && mob.isTamed()), "wolf/striped"),
    ANGRY_STRIPED_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.STRIPED && mob.isAngry()), "wolf/striped"),
    STRIPED_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.STRIPED && !mob.isTamed()), "wolf/striped"),
    
    TAME_WOODS_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.WOODS && mob.isTamed()), "wolf/woods"),
    ANGRY_WOODS_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.WOODS && mob.isAngry()), "wolf/woods"),
    WOODS_WOLF(EntityType.WOLF, Icon.<Wolf>predicate(mob -> mob.getVariant() == Wolf.Variant.WOODS && !mob.isTamed()), "wolf/woods"),

    DROWNED(EntityType.DROWNED, "zombie"),
    HUSK(EntityType.HUSK, "zombie"),
    ZOMBIE(EntityType.ZOMBIE),
    GIANT(EntityType.GIANT, "zombie"),

    ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER),

    //TUNDRA_ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerType() == Villager.Type.SNOW), "zombie_villager/type"),
    //SAVANNA_ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerType() == Villager.Type.SAVANNA), "zombie_villager/type"),
    //DESERT_ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerType() == Villager.Type.DESERT), "zombie_villager/type"),

    //SWAMP_ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerType() == Villager.Type.SWAMP), "zombie_villager/type"), // 16x16

    ARMORER_ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerProfession() == Villager.Profession.ARMORER), "zombie_villager/profession"),
    BUTCHER_ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerProfession() == Villager.Profession.BUTCHER), "zombie_villager/profession"),
    CLERIC_ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerProfession() == Villager.Profession.CLERIC), "zombie_villager/profession"),
    //CARTOGRAPHER_VILLAGER(EntityType.VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerProfession() == Villager.Profession.CARTOGRAPHER), "zombie_villager/profession"),
    FARMER_ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerProfession() == Villager.Profession.FARMER), "zombie_villager/profession"),
    FISHERMAN_ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerProfession() == Villager.Profession.FISHERMAN), "zombie_villager/profession"),
    FLETCHER_ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerProfession() == Villager.Profession.FLETCHER), "zombie_villager/profession"),
    //LEATHERWORKER_VILLAGER(EntityType.VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerProfession() == Villager.Profession.LEATHERWORKER), "zombie_villager/profession"),
    LIBRARIAN_ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerProfession() == Villager.Profession.LIBRARIAN), "zombie_villager/profession"),
    //MASON_VILLAGER(EntityType.VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerProfession() == Villager.Profession.MASON), "zombie_villager/profession"),
    //NITWIT_VILLAGER(EntityType.VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerProfession() == Villager.Profession.NITWIT), "zombie_villager/profession"),
    SHEPHERD_ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerProfession() == Villager.Profession.SHEPHERD), "zombie_villager/profession"),
    //TOOLSMITH_VILLAGER(EntityType.VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerProfession() == Villager.Profession.TOOLSMITH), "zombie_villager/profession"),
    WEAPONSMITH_ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, Icon.<ZombieVillager>predicate(mob -> mob.getVillagerProfession() == Villager.Profession.WEAPONSMITH), "zombie_villager/profession"),

    ARMADILLO(EntityType.ARMADILLO, "uncategorized"),

    BAT(EntityType.BAT, "uncategorized"),

    BLAZE(EntityType.BLAZE, "uncategorized"),

    CHICKEN(EntityType.CHICKEN, "uncategorized"),
    DOLPHIN(EntityType.DOLPHIN, "uncategorized"),

    ELDER_GUARDIAN(EntityType.ELDER_GUARDIAN, "uncategorized"),

    ENDERMITE(EntityType.ENDERMITE, "uncategorized"),

    GUARDIAN(EntityType.GUARDIAN, "uncategorized"),

    PHANTOM(EntityType.PHANTOM, "uncategorized"),

    PUMPKIN_GOLEM(EntityType.SNOW_GOLEM, Icon.<Snowman>predicate(mob -> !mob.isDerp()), "uncategorized"),
    SHEARED_GOLEM(EntityType.SNOW_GOLEM, Icon.predicate(Snowman::isDerp), "uncategorized"),

    SILVER_FISH(EntityType.SILVERFISH, "uncategorized"),

    WANDERING_TRADER(EntityType.WANDERING_TRADER, "uncategorized"),

    WITCH(EntityType.WITCH, "uncategorized");

    private static @NotNull final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    private static @NotNull final FusionPaper fusion = plugin.getFusion();

    private static @NotNull final Path path = plugin.getDataPath();

    private static @NotNull final ComponentLogger logger = plugin.getComponentLogger();

    private final String name;
    private final String key;
    private final EntityType type;
    private final Function<Mob, Boolean> predicate;

    private final String directory;

    Icon(@NotNull final EntityType type) {
        this(type, null, type.name().toLowerCase(Locale.ROOT));
    }

    Icon(@NotNull final EntityType type, final String directory) {
        this(type, null, directory);
    }

    @SuppressWarnings("unchecked")
    <T extends Mob> Icon(@NotNull final EntityType type, @Nullable final Function<T, Boolean> predicate) {
        this.name = name().toLowerCase(Locale.ROOT);
        this.key = String.format("pl3xmap_%s_mob", name);
        this.type = type;
        this.predicate = (Function<Mob, Boolean>) predicate;
        this.directory = this.name;
    }

    @SuppressWarnings("unchecked")
    <T extends Mob> Icon(@NotNull final EntityType type, @Nullable final Function<T, Boolean> predicate, final String directory) {
        this.name = name().toLowerCase(Locale.ROOT);
        this.key = String.format("pl3xmap_%s_mob", name);
        this.type = type;
        this.predicate = (Function<Mob, Boolean>) predicate;
        this.directory = directory;
    }


    public @NotNull final String getKey() {
        return this.key;
    }

    public @NotNull final String getDirectory() {
        return this.directory;
    }

    public @NotNull final EntityType getType() {
        return this.type;
    }

    public static @NotNull Optional<Icon> get(@NotNull final Mob mob) {
        final EntityType type = mob.getType();

        Optional<Icon> safeIcon = Optional.empty();

        for (final Icon icon : values()) {
            if (icon.getType() != type) continue;

            if (icon.predicate == null || icon.predicate.apply(mob)) {
                safeIcon = Optional.of(icon);

                break;
            }
        }

        if (safeIcon.isEmpty()) {
            fusion.log("warn", "{} is a mob that I can recognize.", type.getKey().asString());
        }

        return safeIcon;
    }

    private static <T extends Mob> @NotNull Function<T, Boolean> predicate(@NotNull final Function<T, Boolean> predicate) {
        return predicate;
    }

    private static @NotNull Horse.Style getHorse(@NotNull final Horse horse) {
        return horse.getStyle();
    }

    private static @NotNull Panda.Gene getTrait(@NotNull final Panda panda) {
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
        @NotNull final Path iconFolder = path.resolve("mobs");

        @NotNull final IconRegistry registry = Pl3xMap.api().getIconRegistry();

        for (final Icon icon : values()) {
            // icons/directory_name/8x8/file_name
            final String fileName = String.format("icons%s%s%s%s%s%s.png", File.separator, icon.getDirectory(), File.separator, "8x8", File.separator, icon.name);

            final File file = iconFolder.resolve(fileName).toFile();

            try {
                if (file.exists()) {
                    registry.register(new IconImage(icon.key, ImageIO.read(file), "png"));
                }
            } catch (final IOException exception) {
                logger.warn("Failed to register icon ({}) {}", icon.type, fileName, exception);
            }
        }
    }
}
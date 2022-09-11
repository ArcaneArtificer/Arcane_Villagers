package net.ArcaneArtificer.arcanevillagers.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.ArcaneArtificer.arcanecore.block.Gem_and_Ore_Blocks;
import net.ArcaneArtificer.arcanecore.config.ArcaneCoreCommonConfigs;
import net.ArcaneArtificer.arcanecore.enchantment.ArcanicEnchant;
import net.ArcaneArtificer.arcanecore.item.Gems;
import net.ArcaneArtificer.arcanevillagers.ArcaneVillagers;
import net.ArcaneArtificer.arcanevillagers.villager.ModVillagers;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Mod.EventBusSubscriber(modid = ArcaneVillagers.MOD_ID)
public class ModEvents {
    private static final Map<VillagerProfession, Int2ObjectMap<VillagerTrades.ItemListing[]>> VANILLA_TRADES = new HashMap<>();
    private static final float CHEAP_TO_EXPENSIVE_THRESHOLD = 56f / 64f;
    private static final Integer[][] SCALED_QUANTITIES = new Integer[65][65];
    private static final float[] BIAS = { -1f, -0.7f, -0.4f, -0.15f, 0f, 0.1f, 0.18f, 0.25f, 0.32f, 0.4f};
    private static final Map<String, VillagerProfession> VANILLA_PROFESSIONS = new HashMap<>();
    private static final Map<String, RegistryObject<VillagerProfession>> ARCANE_PROFESSIONS = new HashMap<>();
    private static ArrayList<Enchantment> ALL_ENCHANTS = new ArrayList<>();
    private static Map<VillagerProfession, ArrayList<Enchantment>> LVL_1_PROFESSION_TO_ENCHANTMENTS = new HashMap<>();
    private static Map<VillagerProfession, ArrayList<Enchantment>> LVL_2_PROFESSION_TO_ENCHANTMENTS = new HashMap<>();
    private static Map<VillagerProfession, ArrayList<Enchantment>> LVL_3_PROFESSION_TO_ENCHANTMENTS = new HashMap<>();
    private static Map<VillagerProfession, ArrayList<Enchantment>> LVL_4_PROFESSION_TO_ENCHANTMENTS = new HashMap<>();
    private static Map<VillagerProfession, ArrayList<Enchantment>> LVL_5_PROFESSION_TO_ENCHANTMENTS = new HashMap<>();

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        //
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        VillagerProfession villager = event.getType();// Default behavior

        //Remove all vanilla trades from all professions
        if (ArcaneCoreCommonConfigs.CLEAR_VANILLA_TRADES.get()) {
            Int2ObjectMap<VillagerTrades.ItemListing[]> prof_trades = VANILLA_TRADES.get(villager);
            prof_trades.forEach((key, value) -> {
                for (VillagerTrades.ItemListing curr_trade : value) {
                    trades.get(key).remove(curr_trade);
                }
            });
            // Replace all trade options for villagers
            if (VillagerProfession.ARMORER.equals(villager)) {

            } else if (VillagerProfession.BUTCHER.equals(villager)) {

            } else if (VillagerProfession.CARTOGRAPHER.equals(villager)) {

            } else if (VillagerProfession.CLERIC.equals(villager)) {

            } else if (VillagerProfession.FARMER.equals(villager)) {

            } else if (VillagerProfession.FISHERMAN.equals(villager)) {

            } else if (VillagerProfession.FLETCHER.equals(villager)) {

            } else if (VillagerProfession.LEATHERWORKER.equals(villager)) {

            } else if (VillagerProfession.LIBRARIAN.equals(villager)) {

            } else if (VillagerProfession.MASON.equals(villager)) {

            } else if (VillagerProfession.SHEPHERD.equals(villager)) {

            } else if (VillagerProfession.TOOLSMITH.equals(villager)) {

            } else if (VillagerProfession.WEAPONSMITH.equals(villager)) {

            }
        }
        // Custom Villager Trades
        if (event.getType() == ModVillagers.WIZARD.get()){
            // Wizard level 1 trades
            trades.get(1).add((trader, rand) -> generateTrade(
                    new tradeItems(SCALED_QUANTITIES[10][25], Items.EMERALD, Items.EMERALD_BLOCK, new int[] {5, 3}),
                    new tradeItems(SCALED_QUANTITIES[10][15], pickRandomItem(new ItemLike[] {Items.COAL_BLOCK, Items.COAL, Items.COAL_ORE}), null, new int[] {5, 3})
            ));

            trades.get(1).add((trader, rand) -> generateTrade(
                    new tradeItems(SCALED_QUANTITIES[15][20], Items.EMERALD, Items.EMERALD_BLOCK, new int[] {8, 5}),
                    new tradeItems(1, Items.BOOK, null, new int[] {0, 0}),
                    getRandomEnchant(ModVillagers.WIZARD.get(), 1)
            ));

            trades.get(1).add((trader, rand) -> generateTrade(
                    new tradeItems(10, 3, Items.EMERALD, Gems.NETHER_BERYL.get(), new int[] {2, 2}),
                    new tradeItems(10, 4,  Gems.NETHER_BERYL.get(), null),
                    10, 8
            ));

            // Wizard level 2 trades
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.GOLD_BLOCK, ThreadLocalRandom.current().nextInt(1, 4)), new ItemStack(Items.IRON_CHESTPLATE, 1),
                    10, 8, 0.02F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND_BLOCK, ThreadLocalRandom.current().nextInt(1, 4)), new ItemStack(Items.IRON_LEGGINGS, 1),
                    10, 8, 0.02F));

            // Wizard level 3 trades
            trades.get(3).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.LAPIS_BLOCK, ThreadLocalRandom.current().nextInt(1, 4)), new ItemStack(Items.COAL_BLOCK, 10),
                    10, 8, 0.02F));
            trades.get(3).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.LAPIS_LAZULI, 1), new ItemStack(Items.COAL, ThreadLocalRandom.current().nextInt(5, 20)),
                    10, 8, 0.02F));

            // Wizard level 4 trades
            trades.get(4).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Gem_and_Ore_Blocks.NETHER_BERYL_BLOCK.get(), ThreadLocalRandom.current().nextInt(1, 4)), new ItemStack(Items.COAL_BLOCK, 10),
                    10, 8, 0.02F));
            trades.get(4).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Gem_and_Ore_Blocks.NETHER_BERYL_BLOCK.get(), 1), new ItemStack(Items.COAL, ThreadLocalRandom.current().nextInt(5, 20)),
                    10, 8, 0.02F));

            // Wizard level 5 trades
            trades.get(5).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Gem_and_Ore_Blocks.END_TANZANITE_BLOCK.get(), ThreadLocalRandom.current().nextInt(1, 4)), new ItemStack(Items.COAL_BLOCK, 10),
                    10, 8, 0.02F));
            trades.get(5).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Gem_and_Ore_Blocks.END_TANZANITE_BLOCK.get(), 1), new ItemStack(Items.COAL, ThreadLocalRandom.current().nextInt(5, 20)),
                    10, 8, 0.02F));
        } else if (event.getType() == ModVillagers.JEWELER.get()){
            // Villager level 1 trades
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, ThreadLocalRandom.current().nextInt(5, 10)), new ItemStack(Items.COAL_BLOCK, 10),
                    10, 2, 0.02F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.COAL, ThreadLocalRandom.current().nextInt(20, 40)), new ItemStack(Items.LAPIS_BLOCK, ThreadLocalRandom.current().nextInt(2, 5)),
                    10, 4, 0.02F));

            // Villager level 2 trades
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.GOLD_BLOCK, ThreadLocalRandom.current().nextInt(1, 4)), new ItemStack(Items.IRON_CHESTPLATE, 1),
                    10, 8, 0.02F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND_BLOCK, ThreadLocalRandom.current().nextInt(1, 4)), new ItemStack(Items.IRON_LEGGINGS, 1),
                    10, 8, 0.02F));

            // Villager level 3 trades
            trades.get(3).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.LAPIS_BLOCK, ThreadLocalRandom.current().nextInt(1, 4)), new ItemStack(Items.COAL_BLOCK, 10),
                    10, 8, 0.02F));
            trades.get(3).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.LAPIS_LAZULI, 1), new ItemStack(Items.COAL, ThreadLocalRandom.current().nextInt(5, 20)),
                    10, 8, 0.02F));

            // Villager level 4 trades
            trades.get(4).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Gem_and_Ore_Blocks.NETHER_BERYL_BLOCK.get(), ThreadLocalRandom.current().nextInt(1, 4)), new ItemStack(Items.COAL_BLOCK, 10),
                    10, 8, 0.02F));
            trades.get(4).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Gem_and_Ore_Blocks.NETHER_BERYL_BLOCK.get(), 1), new ItemStack(Items.COAL, ThreadLocalRandom.current().nextInt(5, 20)),
                    10, 8, 0.02F));

            // Villager level 5 trades
            trades.get(5).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Gem_and_Ore_Blocks.END_TANZANITE_BLOCK.get(), ThreadLocalRandom.current().nextInt(1, 4)), new ItemStack(Items.COAL_BLOCK, 10),
                    10, 8, 0.02F));
            trades.get(5).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Gem_and_Ore_Blocks.END_TANZANITE_BLOCK.get(), 1), new ItemStack(Items.COAL, ThreadLocalRandom.current().nextInt(5, 20)),
                    10, 8, 0.02F));
        }
    }

    /*******************************************************************************************************************
     * Trade generation helper methods
     ******************************************************************************************************************/
    private static MerchantOffer generateTrade(tradeItems itemA, tradeItems itemR) {
        return generateTrade(itemA,null, itemR, 12, 8);
    }

    private static MerchantOffer generateTrade(tradeItems itemA, tradeItems itemR, int maxUses, int xp) {
        return generateTrade(itemA,null, itemR, maxUses, xp);
    }

    private static MerchantOffer generateTrade(tradeItems itemA, tradeItems itemB, tradeItems itemR) {
        return generateTrade(itemA, itemB, itemR, 12, 8);
    }

    private static MerchantOffer generateTrade(tradeItems itemA, tradeItems itemB, tradeItems itemR, int maxUses, int xp) {
        if (itemB == null) {
            return new MerchantOffer(itemA.generateStack(), ItemStack.EMPTY, itemR.generateStack(),
                    maxUses, xp, scalePriceMultiplier(itemA.getPriceMultiplier()));
        } else {
            return new MerchantOffer(itemA.generateStack(), itemB.generateStack(), itemR.generateStack(),
                    maxUses, xp, scalePriceMultiplier(itemA.getPriceMultiplier()));
        }
    }

    // Methods used for enchanted book trades, since the return itemstack is non-standard vs all other trades.
    private static MerchantOffer generateTrade(tradeItems itemA, tradeItems itemB, ItemStack itemR) {
        return generateTrade(itemA, itemB, itemR, 12, 8);
    }

    private static MerchantOffer generateTrade(tradeItems itemA, tradeItems itemB, ItemStack itemR, int maxUses, int xp) {
        if (itemB == null) {
            return new MerchantOffer(itemA.generateStack(), new ItemStack(Items.BOOK), itemR,
                    maxUses, xp, scalePriceMultiplier(itemA.getPriceMultiplier()));
        } else {
            return new MerchantOffer(itemA.generateStack(), itemB.generateStack(), itemR,
                    maxUses, xp, scalePriceMultiplier(itemA.getPriceMultiplier()));
        }
    }

    /*******************************************************************************************************************
     * Random Enchantment Selection
     ******************************************************************************************************************/
    private static ItemStack getRandomEnchant(VillagerProfession profession, int level) {
        updateEnchants();
        // Select enchantment randomly for profession and level
        ArrayList<Enchantment> curr_enchants;
        switch (level) {
            case 1 -> {curr_enchants = LVL_1_PROFESSION_TO_ENCHANTMENTS.get(profession);}
            case 2 -> {curr_enchants = LVL_2_PROFESSION_TO_ENCHANTMENTS.get(profession);}
            case 3 -> {curr_enchants = LVL_3_PROFESSION_TO_ENCHANTMENTS.get(profession);}
            case 4 -> {curr_enchants = LVL_4_PROFESSION_TO_ENCHANTMENTS.get(profession);}
            case 5 -> {curr_enchants = LVL_5_PROFESSION_TO_ENCHANTMENTS.get(profession);}
            default -> {return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.BINDING_CURSE, 1));}
        }
        if (curr_enchants != null && !curr_enchants.isEmpty()) {
            int idx = ThreadLocalRandom.current().nextInt(0, curr_enchants.size());
            return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(curr_enchants.get(idx), scaleEnchantLevel(curr_enchants.get(idx).getMaxLevel())));
        } else {
            return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.BINDING_CURSE, 1));
        }
    }

    private static int scaleEnchantLevel(int maxLevel) {
        if (maxLevel > 1 && !ArcaneCoreCommonConfigs.MAX_LEVEL_ENCHANTMENTS.get()) {
            return Math.round(skewedRand(maxLevel, 1,true));
        } else {
            return maxLevel;
        }
    }

    /*******************************************************************************************************************
     * Random Item Selection helper methods
     ******************************************************************************************************************/

    private static ItemLike pickRandomItem(ItemLike[] items) {
        int[] weights = new int[items.length];
        Arrays.fill(weights, 1);
        return pickRandomItem(items, weights, false);
    }

    private static ItemLike pickRandomItem(ItemLike[] items, boolean skew) {
        int[] weights = new int[items.length];
        Arrays.fill(weights, 1);
        return pickRandomItem(items, weights, skew);
    }

    private static ItemLike pickRandomItem(ItemLike[] items, int[] weights) {
        return pickRandomItem(items, weights, false);
    }

    private static ItemLike pickRandomItem(ItemLike[] items, int[] weights, boolean skew) {
        float rand = skewedRand(Arrays.stream(weights).sum(), 0, skew);
        int[] orderedIndexes = sortIndexes(weights);
        int index = 0;
        int curr_weight = weights[orderedIndexes[0]];
        while (rand >= curr_weight && index < items.length) {
            curr_weight += weights[orderedIndexes[index + 1]];
            index++;
        }
        return items[orderedIndexes[index]];
    }

    /*******************************************************************************************************************
     * Misc. number manipulation
     ******************************************************************************************************************/
    private static float skewedRand(int maxValue, int minValue, boolean difficultySkew) {
        float rand =  ThreadLocalRandom.current().nextFloat(0, 1);
        if (difficultySkew) {
            int difficulty = ArcaneCoreCommonConfigs.DIFFICULTY.get();
            float bias = BIAS[difficulty];
            rand = ((float)(maxValue - minValue) * (rand * bias) / (rand * (bias - 1) + 1)) + (float)minValue;
        } else {
            rand = rand * (float)(maxValue - minValue) + (float)minValue;
        }
        return rand;
    }

    private static int[] sortIndexes(int[] weights) {
        int[] indexes = new int[weights.length];
        Arrays.fill(indexes, -1);
        Integer[] sorted = Arrays.stream(weights).boxed().toArray(Integer[]::new);
        Arrays.sort(sorted, Collections.reverseOrder());
        for (int n = 0; n < sorted.length; n++) {
            for (int m = 0; m < weights.length; m++) {
                // find first non-used occurrence
                if (sorted[n] == weights[m] && !ArrayUtils.contains(indexes, m)) {
                    indexes[n] = m;
                    break;
                }
            }
        }
        return indexes;
    }

    // Helper method for generating the 2D cost scaling array.
    private static int scaleQuadratic(int minDiffVal, int typDiffVal) {
        int difficulty = ArcaneCoreCommonConfigs.DIFFICULTY.get();
        int typDiff = 5;
        /*
         Curve fit adjCost = A*difficulty^2 + b*difficulty + c
         Assume slope = 0 @ difficulty 1 (min cost)
         Solving gives adjCost = A*difficulty^2 - 2A*difficulty + minCost + A
         to get A, (typDiffVal - minDiffVal) / (typDiff^2 - 2*typDiff + 1)
        */
        double A = ((float)(typDiffVal - minDiffVal)) / ((float)(typDiff^2 - 2 * typDiff + 1));
        return (int) Math.round(A * (difficulty^2) - 2 * A * difficulty + minDiffVal + A);
    }

    private static float scalePriceMultiplier(float priceMultiplier) {
        // multiplier * 100 corresponds to the reduction in price of the first input item and HotV gives an extra 55% off
        float minMultVal = 0.50f;
        float typMultVal = 1.00f;
        int difficulty = ArcaneCoreCommonConfigs.DIFFICULTY.get();
        int typDiff = 5;
        /*
         Curve fit y = Ax^2 + bx + c
         Assume slope = 0 @ difficulty 1 (min cost)
         Solving gives y = Ax^2 - 2Ax + minCost + A
         to get A, (typDiffVal - minDiffVal) / (typDiff^2 - 2*typDiff + 1)
        */
        float A = (typMultVal - minMultVal) / ((float)(typDiff^2 - 2 * typDiff + 1));
        return priceMultiplier / Math.round(100.0f * A * (difficulty^2) - 2 * A * difficulty + minMultVal + A) / 100.0f;
    }

    /*******************************************************************************************************************
     * Enchantment Distribution vs Professions and Levels
     ******************************************************************************************************************/
    // Verifies locally stored variables have correct number of enchantments
    private static void updateEnchants() {
        // Check sizing of locally stored variables and adjust as needed
        if (ForgeRegistries.ENCHANTMENTS.getValues().size() != ALL_ENCHANTS.size()) {
            // Clear old maps
            LVL_1_PROFESSION_TO_ENCHANTMENTS = new HashMap<>();
            LVL_2_PROFESSION_TO_ENCHANTMENTS = new HashMap<>();
            LVL_3_PROFESSION_TO_ENCHANTMENTS = new HashMap<>();
            LVL_4_PROFESSION_TO_ENCHANTMENTS = new HashMap<>();
            LVL_5_PROFESSION_TO_ENCHANTMENTS = new HashMap<>();

            // Build new maps
            for (VillagerProfession profs : ForgeRegistries.PROFESSIONS) {
                LVL_1_PROFESSION_TO_ENCHANTMENTS.put(profs, new ArrayList<>());
                LVL_2_PROFESSION_TO_ENCHANTMENTS.put(profs, new ArrayList<>());
                LVL_3_PROFESSION_TO_ENCHANTMENTS.put(profs, new ArrayList<>());
                LVL_4_PROFESSION_TO_ENCHANTMENTS.put(profs, new ArrayList<>());
                LVL_5_PROFESSION_TO_ENCHANTMENTS.put(profs, new ArrayList<>());
            }

            // Build All Enchant list and profession to enchant mapping
            ALL_ENCHANTS = new ArrayList<>();
            for(Enchantment enchantment : ForgeRegistries.ENCHANTMENTS.getValues()) {
                ALL_ENCHANTS.add(enchantment);
                // Add enchantments to mapping array.  If Arcanic, data is stored in the class.  Otherwise, follow
                // vanilla rarity mechanics with exceptions.
                if (enchantment instanceof ArcanicEnchant) {
                    /*
                    COMMON(1),
                    UNCOMMON(2),
                    RARE(3),
                    EPIC(4),
                    LEGENDARY(5),
                    MYTHICAL(6),
                    ARCANIC(7);
                     */
                    Map<Integer, ArrayList<String>> trade_options = ((ArcanicEnchant) enchantment).getProfessionTrades();
                    trade_options.forEach((trade_level, professionNames) -> {
                        professionNames.forEach(professionName -> {
                            VillagerProfession profession = null;
                            if (VANILLA_PROFESSIONS.containsKey(professionName)) {
                                profession = VANILLA_PROFESSIONS.get(professionName);
                            } else if (ARCANE_PROFESSIONS.containsKey(professionName)) {
                                profession = ARCANE_PROFESSIONS.get(professionName).get();
                            }
                            if (profession != null) {
                                switch (trade_level) {
                                    case 1 -> {
                                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(profession).add(enchantment);
                                    }
                                    case 2 -> {
                                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(profession).add(enchantment);
                                    }
                                    case 3 -> {
                                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(profession).add(enchantment);
                                    }
                                    case 4 -> {
                                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(profession).add(enchantment);
                                    }
                                    case 5 -> {
                                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(profession).add(enchantment);
                                    }
                                }
                            }
                        });
                    });
                } else {
                    LVL_1_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                    LVL_2_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                    LVL_3_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                    LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                    LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                    if (enchantment == Enchantments.SOUL_SPEED) {
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.BASTION_PLUNDERER.get()).add(enchantment);
                    } else if (enchantment == Enchantments.MENDING) {
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.UNBREAKING) {
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.BLOCK_EFFICIENCY) {
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.MOB_LOOTING) {
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.BLOCK_FORTUNE) {
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.SILK_TOUCH) {
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.ALL_DAMAGE_PROTECTION) {
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.ARMORER).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.ARMORER).add(enchantment);
                    } else if (enchantment == Enchantments.BLAST_PROTECTION) {
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.FIRE_PROTECTION) {
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.PROJECTILE_PROTECTION) {
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.FALL_PROTECTION) {
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.SHARPNESS) {
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                    } else if (enchantment == Enchantments.SMITE) {
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.BANE_OF_ARTHROPODS) {
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.FIRE_ASPECT) {
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.SWEEPING_EDGE) {
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                    } else if (enchantment == Enchantments.POWER_ARROWS) {
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                    } else if (enchantment == Enchantments.PUNCH_ARROWS) {
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                    } else if (enchantment == Enchantments.FLAMING_ARROWS) {
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.INFINITY_ARROWS) {
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.LOYALTY) {
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.RIPTIDE) {
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else if (enchantment == Enchantments.CHANNELING) {
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                        LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        LVL_5_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                    } else {
                        if (enchantment.isCurse()) {
                            LVL_1_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                            LVL_2_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                            LVL_3_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                            LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                            LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                        } else if (enchantment.isTreasureOnly()) {
                            if (enchantment.getRarity().getWeight() >= Enchantment.Rarity.UNCOMMON.getWeight()) {
                                // Common or Uncommon
                                LVL_3_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                                LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                            } else if (enchantment.getRarity().getWeight() >= Enchantment.Rarity.RARE.getWeight()) {
                                // Rare
                                LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                                LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                                LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                            } else {
                                // Very Rare
                                LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                                LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                                LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                                LVL_4_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                                LVL_5_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                            }
                        } else {
                            if (enchantment.getRarity().getWeight() >= Enchantment.Rarity.UNCOMMON.getWeight()) {
                                // Common or Uncommon
                                LVL_1_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_2_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_3_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                            } else if (enchantment.getRarity().getWeight() >= Enchantment.Rarity.RARE.getWeight()) {
                                // Rare
                                LVL_3_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                                LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                            } else {
                                // Very Rare
                                LVL_4_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_5_PROFESSION_TO_ENCHANTMENTS.get(VillagerProfession.LIBRARIAN).add(enchantment);
                                LVL_1_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                                LVL_2_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                                LVL_3_PROFESSION_TO_ENCHANTMENTS.get(ModVillagers.WIZARD.get()).add(enchantment);
                            }
                        }
                    }
                }
            }
        }
    }

    /*******************************************************************************************************************
     * Data storage classes
     ******************************************************************************************************************/
    public static class tradeItems {
        private ItemLike tradeItem;
        private int cost;
        private int variance;
        private float priceMultiplier;

        public tradeItems(int pCost, @Nonnull ItemLike cheapItem, ItemLike expensiveItem, float[] multiplier) {
            init(pCost, 9, cheapItem, expensiveItem, new int[] {5, 3}, multiplier);
        }

        public tradeItems(int pCost, @Nonnull ItemLike cheapItem, ItemLike expensiveItem) {
            init(pCost, 9, cheapItem, expensiveItem, new int[] {5, 3}, new float[] {0.05F, 0.02F});
        }

        public tradeItems(int pCost, @Nonnull ItemLike cheapItem, ItemLike expensiveItem, int[] pVariance, float[] multiplier) {
            init(pCost, 9, cheapItem, expensiveItem, pVariance, multiplier);
        }

        public tradeItems(int pCost, @Nonnull ItemLike cheapItem, ItemLike expensiveItem, int[] pVariance) {
            init(pCost, 9, cheapItem, expensiveItem, pVariance, new float[] {0.05F, 0.02F});
        }

        public tradeItems(int pCost, int divisor, @Nonnull ItemLike cheapItem, ItemLike expensiveItem, float[] multiplier) {
            init(pCost, divisor, cheapItem, expensiveItem, new int[] {5, 3}, multiplier);
        }

        public tradeItems(int pCost, int divisor, @Nonnull ItemLike cheapItem, ItemLike expensiveItem) {
            init(pCost, divisor, cheapItem, expensiveItem, new int[] {5, 3}, new float[] {0.05F, 0.02F});
        }

        public tradeItems(int pCost, int divisor, @Nonnull ItemLike cheapItem, ItemLike expensiveItem, int[] pVariance, float[] multiplier) {
            init(pCost, divisor, cheapItem, expensiveItem, pVariance, multiplier);
        }

        public tradeItems(int pCost, int divisor, @Nonnull ItemLike cheapItem, ItemLike expensiveItem, int[] pVariance) {
            init(pCost, divisor, cheapItem, expensiveItem, pVariance, new float[] {0.05F, 0.02F});
        }

        private void init(int pCost, int divisor, @Nonnull ItemLike cheapItem, ItemLike expensiveItem, int[] pVariance, float[] multiplier) {
            if (multiplier.length != 2) {
                multiplier = new float[] {0.05F, 0.02F};
            }
            if (pVariance.length != 2) {
                pVariance = new int[] {5, 3};
            }
            if (expensiveItem == null || pCost <= (cheapItem.asItem().getMaxStackSize() * CHEAP_TO_EXPENSIVE_THRESHOLD)) {
                cost = Mth.clamp(pCost, 1, cheapItem.asItem().getMaxStackSize());
                tradeItem = cheapItem;
                priceMultiplier = multiplier[0];
                variance = pVariance[0];
            } else {
                cost = Mth.clamp(pCost / divisor, 1, cheapItem.asItem().getMaxStackSize());
                tradeItem = expensiveItem;
                priceMultiplier = multiplier[1];
                variance = pVariance[1];
            }
        }

        public ItemStack generateStack() {
            if (variance > 0) {
                return new ItemStack(tradeItem,
                        Mth.clamp(cost + ThreadLocalRandom.current().nextInt(0, variance), 1,
                                tradeItem.asItem().getMaxStackSize()));
            } else {
                return new ItemStack(tradeItem, cost);
            }
        }

        public float getPriceMultiplier() {
            return priceMultiplier;
        }
    }

    /*******************************************************************************************************************
     * Static method
     ******************************************************************************************************************/
    static {
        // Generate list of all vanilla trades
        VillagerTrades.TRADES.forEach((key, value) -> {
            Int2ObjectMap<VillagerTrades.ItemListing[]> copy = new Int2ObjectOpenHashMap<>();
            value.int2ObjectEntrySet().forEach(ent -> copy.put(ent.getIntKey(), Arrays.copyOf(ent.getValue(), ent.getValue().length)));
            VANILLA_TRADES.put(key, copy);
        });

        // Generate lookup table for all costs
        for (int minDiffVal = 0; minDiffVal < 65; minDiffVal++) {
            for (int typDiffVal = 0; typDiffVal < 65; typDiffVal++) {
                if (minDiffVal == 0 || typDiffVal == 0) {
                    SCALED_QUANTITIES[minDiffVal][typDiffVal] = 0;
                } else {
                    SCALED_QUANTITIES[minDiffVal][typDiffVal] = scaleQuadratic(minDiffVal, typDiffVal);
                }
            }
        }

        VANILLA_PROFESSIONS.put("ARMORER", VillagerProfession.ARMORER);
        VANILLA_PROFESSIONS.put("BUTCHER", VillagerProfession.BUTCHER);
        VANILLA_PROFESSIONS.put("CARTOGRAPHER", VillagerProfession.CARTOGRAPHER);
        VANILLA_PROFESSIONS.put("CLERIC", VillagerProfession.CLERIC);
        VANILLA_PROFESSIONS.put("FARMER", VillagerProfession.FARMER);
        VANILLA_PROFESSIONS.put("FISHERMAN", VillagerProfession.FISHERMAN);
        VANILLA_PROFESSIONS.put("FLETCHER", VillagerProfession.FLETCHER);
        VANILLA_PROFESSIONS.put("LEATHERWORKER", VillagerProfession.LEATHERWORKER);
        VANILLA_PROFESSIONS.put("LIBRARIAN", VillagerProfession.LIBRARIAN);
        VANILLA_PROFESSIONS.put("MASON", VillagerProfession.MASON);
        VANILLA_PROFESSIONS.put("SHEPHERD", VillagerProfession.SHEPHERD);
        VANILLA_PROFESSIONS.put("TOOLSMITH", VillagerProfession.TOOLSMITH);
        VANILLA_PROFESSIONS.put("WEAPONSMITH", VillagerProfession.WEAPONSMITH);

        ARCANE_PROFESSIONS.put("WIZARD", ModVillagers.WIZARD);
        ARCANE_PROFESSIONS.put("JEWELER", ModVillagers.JEWELER);
        ARCANE_PROFESSIONS.put("BASTION_PLUNDERER", ModVillagers.BASTION_PLUNDERER);
    }
}


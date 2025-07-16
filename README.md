# 🌿 PhiLia Amulet - 友爱护符

> 💖 A Minecraft mod that prevents friendly fire between you and your pets, summons, or friendly mobs.  
> 💖 一个防止你误伤友好生物、宠物与召唤物的模组。

---

## 🧭 Overview | 模组简介

**EN**  
**PhiLia Amulet** is a lightweight and configurable Minecraft mod (Forge) that prevents unintentional damage to friendly or owned mobs (like villagers, pets, summons, etc.), even from ranged, AoE, or magic attacks. Pets and summons from the same player won’t fight each other, and you can customize protection targets through **a global config whitelist** and **per-amulet whitelist**.

**CN**  
**友爱护符 PhiLia Amulet** 是一个轻量级、可配置的 Forge 模组，防止你在使用近战、远程、范围或魔法攻击时误伤友方单位（如村民、宠物、召唤物等）。你还可以通过**配置文件的全局白名单**以及**每个护符独立维护的白名单**来自定义保护目标。同一玩家拥有的宠物和召唤物也不会相互攻击。

---

## ⚙️ Features | 特性

- ✅ Prevents you from damaging mobs on the **global whitelist** and your amulet's **personal whitelist**  
  ✅ 防止你伤害配置文件中全局白名单及你护符内独立白名单中的生物  

- ✅ Right-click with the amulet to manage the amulet's **personal whitelist** (Shift = Remove)  
  ✅ 右键实体可添加或移除护符独立白名单的生物（Shift+右键移除）  

- ✅ Blocks friendly fire between pets/summons with the **same owner**  
  ✅ 阻止同一主人的宠物/召唤物之间的误伤与互殴  

- ✅ Your pets won't harm you, and (optionally) can't harm themselves  
  ✅ 宠物不会伤害你，且（可选）不会伤害自己  

- ✅ Fully configurable via `PhiliaAmuletConfig.toml`  
  ✅ 所有行为可通过配置文件自定义  

- ✅ Localized whitelist tooltips with translated entity names  
  ✅ 白名单支持本地化生物名和提示文本

---

## 🧪 How It Works | 工作原理

- When you hurt a mob: If you’re wearing the **Philia Amulet**, and the mob is on **either the global whitelist or your amulet’s personal whitelist**, damage is canceled.  
  当你试图伤害一个生物时：如果你佩戴护符，且目标在**全局白名单或护符个人白名单**中，伤害将被取消。

- If attacker and victim **share the same owner**, damage is canceled (unless config allows it).  
  如果攻击者与受害者具有相同的主人（例如宠物或召唤物），则伤害被阻止（除非配置允许）。

- Whitelist management for each amulet is done **per item**, stored in NBT; modify it by **right-clicking** on mobs.  
  每个护符的个人白名单单独维护，存储在物品 NBT 中；你可以通过**右键生物**来添加或移除。

- The **global whitelist** is defined in the config file and applies universally to all amulets.  
  **全局白名单**由配置文件统一管理，所有护符通用。

---

## 🔧 Configuration | 配置文件

Config file path | 配置文件路径：  
`<minecraft_folder>/config/PhiliaAmuletConfig.toml`

| Config Option               | Description (EN)                                                                 | 描述（中文）                                           |
|----------------------------|----------------------------------------------------------------------------------|--------------------------------------------------------|
| `Entity Whitelist`         | Global list of entity IDs protected by all amulets                              | 所有护符共用的全局白名单实体 ID 列表                   |
| `No Self-Harm`             | Prevents harming yourself or pets harming themselves                            | 禁止自残，宠物也不能攻击自己                           |
| `No Melee Damage Protection`| If enabled, disables melee protection (AoE and ranged still protected)          | 启用后，护符将不会阻止近战攻击                         |
| `Pets are friendly to whitelisted mobs` | Pets/summons won't attack mobs in your whitelist when you're wearing the amulet | 宠物不会攻击在白名单中的生物（你需佩戴护符）          |
| `Pets are friendly to each other` | Prevents pets/summons with same owner from hurting each other              | 阻止同一玩家的宠物互相伤害                             |
| `I don't care`             | Allow the player to hurt their own pets/summons (even if they are friendly)     | 允许玩家攻击自己的宠物或召唤物                         |

---

## 🖱️ Whitelist Management | 白名单管理

- ▶️ **Right-click** a mob with the Philia Amulet in your main hand to **add it** to that amulet's personal whitelist  
- 🔁 **Shift + Right-click** to **remove** a mob from the amulet’s personal whitelist  
- 💬 In-game messages will show localized entity names  
- 🔍 Hold Shift while viewing the item tooltip to see the amulet's personal whitelist  


---

## 🛠️ Compatibility | 兼容性

| Supported Mod                | Description                             | 描述                                     |
|-----------------------------|-----------------------------------------|------------------------------------------|
| Iron's Spells 'n Spellbooks | Summons & spell effects auto-protected  | 魔法与召唤单位自动适配                   |
| Goety                       | Summoned skeletons and undead servants  | 死灵、仆从自动适配                       |
| Any `Tameable`, `Projectile`, `AreaEffectCloud` etc. based mod | Full support | 使用标准生物/投射物系统的模组自动兼容   |


---

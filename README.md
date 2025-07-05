# 🌿 PhiLia Amulet - 友爱护符

> 💖 A Minecraft mod that prevents friendly fire between you and your pets, summons, or friendly mobs.  
> 💖 一个防止你误伤友好生物、宠物与召唤物的模组。

---

## 🧭 Overview | 模组简介

**EN**  
**PhiLia Amulet** is a lightweight and configurable Minecraft mod (Forge) that prevents unintentional damage to certain mobs (like villagers, iron golems, guards, etc.) when using area-of-effect or ranged attacks from other magic-based mods like *Iron's Spells 'n Spellbooks* or *Goety*. It also blocks pets and summons with the **same owner** from attacking each other due to friendly fire or accidental aggression.

**CN**  
**友爱护符 PhiLia Amulet** 是一个轻量级、可配置的 Forge 模组，旨在解决使用魔法模组（如 *Iron's Spells 'n Spellbooks* 和 *Goety*）时容易误伤友方单位的问题。佩戴护符后，你将不会误伤特定生物（如村民、铁傀儡、村庄守卫等），同时还会阻止同一玩家拥有的宠物或召唤物之间的互相攻击。

---

## ⚙️ Features | 特性

- ✅ Prevents you from damaging specific mobs when wearing the **Philia Amulet**  
  ✅ 佩戴友爱护符时，防止你伤害特定的白名单生物  
- ✅ Blocks friendly fire between pets/summons with the **same owner**  
  ✅ 阻止同一主人的宠物/召唤物之间的误伤与互殴  
- ✅ Fully configurable via `PhiliaAmuletConfig.toml`  
  ✅ 所有行为可通过配置文件进行自定义  
- ✅ Compatible with:  
  ✅ 模组兼容性广泛，包括：
  - `Iron's Spells 'n Spellbooks`  
  - `Goety`  
  - `Curios API`（用于护符槽位）  
  - Any mod using standard `Tameable`, `OwnableEntity`, or custom `MagicSummon` logic  
    任意使用 `Tameable`, `OwnableEntity` 或自定义 `MagicSummon` 的模组

---

## 🧪 How It Works | 工作原理

### When you hurt a mob:  
### 当你试图伤害一个生物时：

- If you’re wearing the **Philia Amulet**, and the mob is **whitelisted**, damage is canceled.  
  如果你佩戴了友爱护符，且目标生物在保护白名单中，伤害将被取消。

- If the attacker and the victim **share the same owner** (e.g. both summoned by the same player), damage is canceled.  
  如果攻击者与受害者具有相同的主人（例如都被同一个玩家召唤），伤害将被取消。

- Prevents unnecessary aggression or AI conflict between friendly mobs.  
  避免友方单位之间产生不必要的敌意或 AI 混乱。

---

## 🔧 Configuration | 配置文件

Config file path | 配置文件路径：  
`<minecraft_folder>/config/PhiliaAmuletConfig.toml`

You can customize the following options:  
你可以自定义以下配置项：

| Config Option | Description (EN) | 描述（中文） |
|---------------|------------------|----------------|
| `entityWhitelist` | List of entity IDs protected by the amulet | 护符保护的生物 ID 列表 |
| `NoMeleeProtection` | Allow melee to bypass amulet protection (default: false) | 是否允许近战攻击无视护符保护（默认关闭） |
| `BlockFriendlyEntityDamage` | Prevent damage between pets/summons with same owner (default: true) | 是否阻止同一主人的宠物/召唤物互相伤害（默认启用） |

---

## 📦 Installation | 安装方式

1. Install Forge
   安装 Forge
2. Download this mod and place it into the `mods/` folder  
   下载本模组并放入 `mods/` 文件夹  
3. Necessary mods:  
   搭配安装的模组：
   - [Curios API](https://www.curseforge.com/minecraft/mc-mods/curios) – Required for amulet slot support  
     用于护符槽的支持，必需依赖  


---

## 🛠️ Compatibility | 兼容性

| Supported Mod | Description | 描述 |
|---------------|-------------|------|
| Iron's Spells 'n Spellbooks | Supports its summoned mobs | 支持其召唤生物 |
| Goety | Supports skeleton minions, undead servants, etc. | 支持骷髅仆从、死灵召唤单位等 |
| Curios | Required for amulet equipment slot | 护符槽位依赖 |
| Mods with `TameableAnimal`, `OwnableEntity`, `Projectile`, `AreaEffectCloud` | Auto-compatible | 自动兼容使用以上类的模组 |

---

## 💬 Feedback & Suggestions | 反馈与建议

欢迎在 CurseForge 或 GitHub 页面提交建议与反馈！  
Feel free to submit issues, suggestions, or PRs on CurseForge or GitHub!

---


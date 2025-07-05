# 🌿 Philia Amulet - 友爱护符

> 💖 A Minecraft mod that prevents friendly fire between you and your pets, summons, or friendly mobs.  
> 💖 一个防止你误伤友好生物和宠物召唤物的模组。

---

## 🧭 Overview | 模组简介

**EN**  
**Philia Amulet** is a lightweight and configurable Minecraft mod (Forge) that prevents unintentional damage to certain mobs (like villagers, iron golems, guards, etc.) when using area-of-effect or ranged attacks from other magic-based mods like *Iron's Spells 'n Spellbooks* or *Goety*. It also blocks pets and summons with the **same owner** from attacking each other due to friendly fire or accidental aggression.

**CN**  
**友爱护符 Philia Amulet** 是一个轻量级可配置的 Forge 模组，解决魔法模组误伤友方单位的问题，尤其适配了如 *Iron's Spells 'n Spellbooks*、*Goety* 等模组的远程或范围攻击行为。佩戴护符后，可避免伤害特定生物，同时阻止同主人控制的召唤物、宠物互相伤害。

---

## ⚙️ Features | 特性

- ✅ Prevents you from damaging specific mobs when wearing the **Philia Amulet**  
- ✅ Blocks friendly fire between pets/summons with the **same owner**  
- ✅ Fully configurable via `PhiliaAmuletConfig.toml`  
- ✅ Compatible with:  
  - `Iron's Spells 'n Spellbooks`  
  - `Goety`  
  - `Curios API` (used for wearing the amulet)  
  - Any mod using standard `Tameable`, `OwnableEntity`, or custom `MagicSummon` logic

---

## 🧪 How It Works | 工作原理

### When you hurt a mob:
- If you’re wearing the **Philia Amulet**, and the mob is **whitelisted**, damage is canceled.
- If the attacker and the victim **share the same owner** (e.g. both summoned by the same player), damage is canceled.
- Prevents unnecessary aggression or AI conflict between friendly mobs.

---

## 🔧 Configuration | 配置文件

模组配置文件位置：  
<minecraft_folder>/config/PhiliaAmuletConfig.toml

你可以调整以下内容：

| 配置项 | 说明 |
|--------|------|
| `entityWhitelist` | 受护符保护的生物 ID 列表 |
| `NoMeleeProtection` | 是否允许近战攻击仍然伤害白名单生物（默认关闭） |
| `BlockFriendlyEntityDamage` | 是否阻止同一主人的宠物/召唤物互相伤害（默认启用） |

---

## 📦 Installation | 安装方式

1. 安装 [Minecraft Forge](https://files.minecraftforge.net/)（推荐版本：`1.20.1`）
2. 下载本模组并放入 `mods/` 文件夹
3. 同时推荐安装：
   - [Curios API](https://www.curseforge.com/minecraft/mc-mods/curios)（用于护符栏位）
   - 你常用的魔法模组（如 Iron's Magic、Goety）

---

## 🛠️ Compatibility | 兼容性

| 支持的模组 | 描述 |
|------------|------|
| Iron's Spells 'n Spellbooks | 支持其召唤生物（通过 `MagicSummon.getSummoner()`） |
| Goety | 支持召唤骷髅仆从、死灵单位等 |
| Curios | 用于护符穿戴检测 |
| 任意支持 `TameableAnimal`, `OwnableEntity`, `Projectile`, `AreaEffectCloud` 的模组 | 自动兼容 |

---

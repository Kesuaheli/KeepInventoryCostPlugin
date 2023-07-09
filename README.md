# KeepInventoryCostPlugin

KeepInventoryCost is a Minecraft server plugin that allows survival players to keep their inventory and experience levels after they die.

If a player dies and has enough money in their bank, the configured amount gets automatically paid and the player keeps all their items and xp.

---

## Download

Download is available [here on Hangar](https://hangar.papermc.io/Kesuaheli/KeepInventoryCost), the plugin hosting site by PaperMC.

---

## Usage

After adding the plugin `.jar` to your [Paper Server](https://papermc.io)s `plugins/` folder <i>(and resarting the server)</i>, KeepInventoryCost is ready to go.  
<u><b>By default everyones personal keep inventory setting is disabled.</b></u> To enable your personal keep inventory simply run `/keepinventory set true`. Now, if you die, you automatically charged the amount of [the cost setting](#cost). All your items and experience levels are saved in your inventory and will not be dropped. You will also receive an [info message](#messagedeathpaid), saying you have been charged to keep your inventory.  
If is not possible to withdraw from your bank account (for example your balance is too low), you will receive an [information message](#messagedeathno_money) and all your items and xp drop like normal. 

---

## Requirements

KeepInventoryCost needs a [Paper Server](https://papermc.io).

You also have to have the [Vault plugin](https://www.spigotmc.org/resources/vault.34315/) and a economy plugin of your choice already installed in order to connect to the users balance. Otherwise the plugin will automatically disable

---

## Commands

Sorted by permission

<details open>
	<summary>
		<code>de.kesuaheli.keepinventorycost.command.base</code>
	</summary>

<i>This permission is set for <u>everyone</u> by default.</i>

- `/keepinventory get`
  Returns the players current setting.
- `/keepinventory set <true|false>`
  Changes whether to pay or not after death.

</details>

<details open>
	<summary>
		<code>de.kesuaheli.keepinventorycost.command.admin</code>
	</summary>

<i>This permission is set for <u>operators</u> by default.</i>

- `/keepinventoryadmin reload`
  Reloads changes made to the `config.yml` without restarting the server

</details>

---

## Config

Located at `plugins/KeepInventoryCost/config.yml`

Default contents:

```yml
# how expensive it is to keep their items after a death
cost: 100

# how messages should be displayed
message:
  enabled: "Enabled"
  disabled: "Disabled"

  reload: "Successfully reloaded config!"
  setting.get: "Your KeepInv setting is currently %s."
  setting.set: "Your KeepInv setting is now %s!"
  setting.set_refuse: "Your KeepInv setting is already %s!"
  death.no_money: "You didn't have enough %s to pay for your items. You need %s."
  death.paid: "You paid %s to keep your items and XP!"

```

### `cost`

Type: integer

Defines how expensive it is to pay for keeping their inventory.

- If its smaller than 0, it means its free to keep the inventory Even if the player has a negative balance.
- Set to 0 to makes it free too, but the player must not have a negative balance <i>(-> at least 0)</i>.
- Anything greater than 0 means that amount will be withdrawn on players death to keep their inventory.

### `message.enabled` `message.disabled`

The name of the state to be inserted in messages like [`message.setting.get`](#messagesettingget)

### `message.reload`

The message to show when the config file is reloaded by the [`/keepinventoryadmin` command](#commands).

### `message.setting.get`

The message to show when the player executes the [`/keepinventory get` command](#commands).

- The first parameter is filled with the current state using [`message.enabled` or `message.disabled`](#messageenabled-messagedisabled).

### `message.setting.set`

The message to show when the player executes the [`/keepinventory set` command](#commands).

- The first parameter is filled with the new state using [`message.enabled` or `message.disabled`](#messageenabled-messagedisabled).

### `message.setting.set_refuse`

The message to show when the player executes the [`/keepinventory set` command](#commands), but it was already on this state.

- The first parameter is filled with the current state using [`message.enabled` or `message.disabled`](#messageenabled-messagedisabled).

### `message.death.no_money`

The message to show when the player dies and enabled keep inventory, but has not enough money.

- The first parameter is the name of the currency.  
- The second parameter is the amount the player would have needed using [cost](#cost) and the name of the currency.

### `message.death.paid`

The message to show when the player dies, enabled keep inventory and has enough money.

- The first parameter is the amount the player paid using [cost](#cost) and the name of the currency.

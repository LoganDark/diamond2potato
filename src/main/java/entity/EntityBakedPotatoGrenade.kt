package net.logandark.diamond2potato.entity

import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource

class EntityBakedPotatoGrenade(oldItem: EntityItem) : EntityItem(oldItem.world, oldItem.posX, oldItem.posY, oldItem.posZ) {
	private val itemThrower = oldItem.thrower
	private var tick = 0
	private var maxTick = 100

	init {
		item = ItemStack(Items.BAKED_POTATO, oldItem.item.count, oldItem.item.itemDamage)
		setPickupDelay(20)
	}

	override fun onCollideWithPlayer(entityIn: EntityPlayer?) {
		val ownerPlayer = this.world.minecraftServer!!.playerList.getPlayerByUsername(this.itemThrower)

		if (entityIn == ownerPlayer) {
			super.onCollideWithPlayer(entityIn!!)
		}
	}

	private fun createExplosion() {
		setDead()
		world.createExplosion(this, this.posX, this.posY, this.posZ, 2F + (30F * item.count / 64F), true)
	}

	override fun onUpdate() {
		super.onUpdate()

		if (!world.isRemote) {
			if (tick == maxTick) {
				createExplosion()
			} else {
				tick++
			}
		}
	}

	override fun isEntityInvulnerable(source: DamageSource?): Boolean {
		if (source!!.isExplosion) {
			return true
		}

		return super.isEntityInvulnerable(source)
	}
}
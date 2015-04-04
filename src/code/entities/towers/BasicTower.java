package code.entities.towers;

import java.util.LinkedList;

import code.entities.Projectile;

public class BasicTower extends Tower {

	public BasicTower(float x, float y, String imgPath) {
		super(x, y, imgPath);
		damage = BasicTowerInfo.getDMG();
		price = BasicTowerInfo.getPRICE();
		attackSpeed = BasicTowerInfo.getATKS();
	}

	@Override
	protected Projectile generateProjectile(double rotation, float Xdirection, float Ydirection) {
		// TODO Auto-generated method stub
		return new Projectile(rotation, x + 12, y + 8, Xdirection, Ydirection);
	}

}

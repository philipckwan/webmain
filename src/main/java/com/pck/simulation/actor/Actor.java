package com.pck.simulation.actor;

public abstract class Actor {

	private int hunger = 100;

	public enum HungerStatus {
		Not_hungry, Ready_to_eat, Hungry, Starving
	}

	private HungerStatus hungerStatus = HungerStatus.Not_hungry;

	private int age = 0;

	public enum AgeStatus {
		Young, Adult, Old
	}

	private AgeStatus ageStatus = AgeStatus.Young;

	private String initial;

	protected Actor(String initial) {
		this.initial = initial;
	}

	public abstract void born();

	public abstract void eat();

	public abstract void reproduce();

	public abstract void heartbeat();

	public abstract void die();

	public String getInitial() {
		return initial;
	}
}

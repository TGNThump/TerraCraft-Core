package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;

import com.google.common.collect.Lists;

public abstract class Channel {

	private Integer id;
	private String name;
	private String tag;
	
	private boolean autojoin = false;
	private boolean leaveable = true;
	
	private Integer range = -1;
	
	private ArrayList<UUID> joinedPlayers = Lists.newArrayList();
	private ArrayList<UUID> mutedPlayers = Lists.newArrayList();
	
	public ArrayList<UUID> getJoinedPlayers(){
		return joinedPlayers;
	}
	
	public ArrayList<UUID> getMutedPlayers(){
		return mutedPlayers;
	}
	
	public void add(UUID uuid){
		if (contains(uuid)) return;
		joinedPlayers.add(uuid);
	}
	
	public void add(Player player){
		add(player.getUniqueId());
	}
	
	public void add(Account account){
		add(account.getPlayer());
	}
	
	public void remove(UUID uuid){
		if (!contains(uuid)) return;
		joinedPlayers.remove(uuid);
	}
	
	public void remove(Player player){
		remove(player.getUniqueId());
	}
	
	public void remove(Account account){
		remove(account.getPlayer());
	}
	
	public boolean contains(UUID uuid){
		return joinedPlayers.contains(uuid);
	}
	
	public boolean contains(Player player){
		return contains(player.getUniqueId());
	}
	
	public boolean contains(Account account){
		return contains(account.getPlayer());
	}
	
	public void mute(UUID uuid){
		if (isMuted(uuid)) return;
		mutedPlayers.add(uuid);
	}
	
	public void mute(Player player){
		mute(player.getUniqueId());
	}
	
	public void mute(Account account){
		mute(account.getPlayer());
	}
	
	public void unmute(UUID uuid){
		if (!isMuted(uuid)) return;
		mutedPlayers.remove(uuid);
	}
	
	public void unmute(Player player){
		unmute(player.getUniqueId());
	}
	
	public void unmute(Account account){
		unmute(account.getPlayer());
	}
	
	public boolean isMuted(UUID uuid){
		return mutedPlayers.contains(uuid);
	}
	
	public boolean isMuted(Player player){
		return isMuted(player.getUniqueId());
	}
	
	public boolean isMuted(Account account){
		return isMuted(account.getPlayer());
	}
	
	public abstract void processChatEvent(Player sender, String message);

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getTag() {
		return tag;
	}

	public Integer getRange() {
		return range;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public boolean isAutojoin() {
		return autojoin;
	}

	public void setAutojoin(boolean autojoin) {
		this.autojoin = autojoin;
	}
	
	public void autojoin(){
		this.autojoin = true;
	}
	
	public boolean canJoin(Player player){
		// TODO: Add Permissions
		return true;
	}

	public boolean isLeaveable() {
		return leaveable;
	}

	public void setLeaveable(boolean leaveable) {
		this.leaveable = leaveable;
	}
	
	public String getDisplayName(Player player){
		return name;
	}
}

package me.ninethousand.ninehack.managers.social;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.features.client.Friends;
import me.ninethousand.ninehack.util.ChatUtil;
import me.ninethousand.ninehack.util.TextUtil;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class FriendManager {
    private static final ArrayList<Friend> friends = new ArrayList<>();

    public static boolean isFriend(String name) {
        boolean isFriend = false;

        for (Friend friend : getFriends()) {
            if (friend.getName().equalsIgnoreCase(name) && Friends.INSTANCE.isEnabled()) {
                isFriend = true;
            }
        }

        return isFriend;
    }

    public static Friend getFriendByName(String name) {
        Friend namedFriend = null;

        for (Friend friend : getFriends()) {
            if (friend.getName().equalsIgnoreCase(name)) {
                namedFriend = friend;
            }
        }

        return namedFriend;
    }

    public static ArrayList<Friend> getFriends() {
        return friends;
    }

    public static void addFriend(String name) {
        friends.add(new Friend(name));
    }

    public static void delFriend(String name) {
        friends.remove(getFriendByName(name));
    }
}

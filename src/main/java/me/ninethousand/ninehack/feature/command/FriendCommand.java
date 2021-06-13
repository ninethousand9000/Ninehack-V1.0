package me.ninethousand.ninehack.feature.command;

import me.ninethousand.ninehack.managers.social.Friend;
import me.ninethousand.ninehack.managers.social.FriendManager;
import me.ninethousand.ninehack.util.ChatUtil;
import me.ninethousand.ninehack.util.TextUtil;
import me.yagel15637.venture.command.AbstractCommand;

public final class FriendCommand extends AbstractCommand {
    public FriendCommand() {
        super("Friends a player", "friend/f/ [player]", "friend", "f");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) return;

        for (String string : args) {
            for (Friend friend : FriendManager.getFriends()) {
                ChatUtil.sendClientMessageSimple(TextUtil.coloredString("Removed Player " + friend.getName() + " from your friends :(!", TextUtil.Color.RED));

                if (friend.getName().equalsIgnoreCase(string)) {
                    FriendManager.delFriend(friend.getName());
                    ChatUtil.sendClientMessageSimple(TextUtil.coloredString("Removed Player " + friend.getName() + " from your friends :(!", TextUtil.Color.RED));
                }
                else {
                    FriendManager.addFriend(friend.getName());
                    ChatUtil.sendClientMessageSimple(TextUtil.coloredString("Added Player " + friend.getName() + " to your friends :)!", TextUtil.Color.GREEN));
                }
            }
        }
    }
}

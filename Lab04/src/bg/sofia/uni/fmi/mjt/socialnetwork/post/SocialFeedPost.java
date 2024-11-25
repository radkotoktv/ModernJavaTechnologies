package bg.sofia.uni.fmi.mjt.socialnetwork.post;

import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Objects;

public class SocialFeedPost implements Post {
    private static int idCounter = 0;
    private final String uniqueId;
    private final UserProfile author;
    private final String content;
    private final LocalDateTime creationDate;
    private final Map<ReactionType, HashSet<UserProfile>> reactions;

    public SocialFeedPost(UserProfile author, String content) {
        this.author = author;
        this.content = content;
        this.creationDate = LocalDateTime.now();
        this.reactions = new HashMap<>();

        this.uniqueId = String.valueOf(idCounter);
        idCounter++;
    }

    @Override
    public String getUniqueId() {
        return this.uniqueId;
    }

    @Override
    public UserProfile getAuthor() {
        return this.author;
    }

    @Override
    public LocalDateTime getPublishedOn() {
        return creationDate;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public boolean addReaction(UserProfile userProfile, ReactionType reactionType) {
        try {
            if (userProfile == null) throw new IllegalArgumentException("Null profile cannot react to a post!");
            if (reactionType == null)
                throw new IllegalArgumentException("You cannot react with null to a post!");
        } catch (IllegalArgumentException e) {
            throw e;
        }

        for (Map.Entry<ReactionType, HashSet<UserProfile>> entry : reactions.entrySet()) {
            ReactionType type = entry.getKey();
            Set<UserProfile> users = entry.getValue();
            if (users.contains(userProfile)) {
                if (type == reactionType) {
                    return false;
                } else {
                    users.remove(userProfile);
                    break;
                }
            }
        }

        HashSet<UserProfile> userSet = reactions.get(reactionType);
        if (userSet == null) {
            userSet = new HashSet<>();
            reactions.put(reactionType, userSet);
        }
        userSet.add(userProfile);
        return true;
    }

    @Override
    public boolean removeReaction(UserProfile userProfile) {
        try {
            if (userProfile == null) throw new IllegalArgumentException("Null profile cannot remove a reaction!");
        } catch (IllegalArgumentException e) {
            throw e;
        }

        for (Map.Entry<ReactionType, HashSet<UserProfile>> entry : reactions.entrySet()) {
            Set<UserProfile> users = entry.getValue();
            if (users.remove(userProfile)) {
                if (users.isEmpty()) {
                    reactions.remove(entry.getKey());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<ReactionType, Set<UserProfile>> getAllReactions() {
        return Map.copyOf(reactions);
    }

    @Override
    public int getReactionCount(ReactionType reactionType) {
        try {
            if (reactionType == null) throw new IllegalArgumentException("Null cannot be a reaction type!");
        } catch (IllegalArgumentException e) {
            throw e;
        }

        return reactions.get(reactionType).size();
    }

    @Override
    public int totalReactionsCount() {
        int sum = 0;
        Set<ReactionType> keys = reactions.keySet();
        for (ReactionType key : keys) {
            sum += reactions.get(key).size();
        }
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Post other)) return false;
        return this.uniqueId.equals(other.getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, author, content, creationDate, reactions);
    }
}

package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

public class DefaultUserProfile implements UserProfile {
    private final String username;
    Collection<Interest> interests;
    Collection<UserProfile> friends;

    public DefaultUserProfile(String username) {
        this.username = username;
        this.interests = new HashSet<>();
        this.friends = new HashSet<>();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public Collection<Interest> getInterests() {
        return Set.copyOf(interests);
    }

    @Override
    public boolean addInterest(Interest interest) {
        try {
            if (interest == null) throw new IllegalArgumentException("You cannot add a null interest!");
        } catch (IllegalArgumentException e) {
            throw e;
        }
        return interests.add(interest);
    }

    @Override
    public boolean removeInterest(Interest interest) {
        try {
            if (interest == null) throw new IllegalArgumentException("You cannot remove a null interest!");
        } catch (IllegalArgumentException e) {
            throw e;
        }
        return interests.remove(interest);
    }

    @Override
    public Collection<UserProfile> getFriends() {
        return Set.copyOf(friends);
    }

    @Override
    public boolean addFriend(UserProfile userProfile) {
        try {
            if (userProfile == null) throw new IllegalArgumentException("Cannot add null as a friend!");
            if (userProfile == this) throw new IllegalArgumentException("Cannot add yourself as a friend!");
        } catch (IllegalArgumentException e) {
            throw e;
        }
        if (this.isFriend(userProfile)) {
            return false;
        }
        return friends.add(userProfile);
    }

    @Override
    public boolean unfriend(UserProfile userProfile) {
        try {
            if (userProfile == null) throw new IllegalArgumentException("Cannot unfriend null!");
        } catch (IllegalArgumentException e) {
            throw e;
        }
        if (!friends.contains(userProfile)) {
            return false;
        }
        return friends.remove(userProfile);
    }

    @Override
    public boolean isFriend(UserProfile userProfile) {
        try {
            if (userProfile == null)
                throw new IllegalArgumentException("Null users cannot be friends!");
        } catch (IllegalArgumentException e) {
            throw e;
        }
        return friends.contains(userProfile);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof UserProfile other)) return false;
        return this.username.equals(other.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, interests, friends);
    }
}

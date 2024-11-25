package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.SocialFeedPost;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.Interest;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Collection;
import java.util.SortedSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Comparator;

public class SocialNetworkImpl implements SocialNetwork {
    private final Set<UserProfile> users;
    private final List<Post> posts;

    public SocialNetworkImpl() {
        this.users = new HashSet<>();
        this.posts = new ArrayList<>();
    }

    @Override
    public void registerUser(UserProfile userProfile) throws UserRegistrationException {
        try {
            if (userProfile == null) throw new IllegalArgumentException("Cannot register a null user!");
            if (users.contains(userProfile)) throw new UserRegistrationException("User already registered!");
        } catch (IllegalArgumentException e) {
            throw e;
        }
        users.add(userProfile);
    }

    @Override
    public Set<UserProfile> getAllUsers() {
        return Set.copyOf(users);
    }

    @Override
    public Post post(UserProfile userProfile, String content) throws UserRegistrationException {
        try {
            if (userProfile == null)
                throw new IllegalArgumentException("Null profiles can't post!");
            if (!users.contains(userProfile))
                throw new UserRegistrationException("Non-registered users can't post!");
            if (content.isEmpty() || content.equals(" "))
                throw new IllegalArgumentException("Posts with no content aren't allowed!");
        } catch (IllegalArgumentException e) {
            throw e;
        }

        SocialFeedPost newPost = new SocialFeedPost(userProfile, content);
        posts.add(newPost);
        return newPost;
    }

    @Override
    public Collection<Post> getPosts() {
        return List.copyOf(posts);
    }

    @Override
    public Set<UserProfile> getReachedUsers(Post post) {
        UserProfile author = post.getAuthor();
        Collection<Interest> authorInterests = author.getInterests();
        Set<UserProfile> reachedUsers = new HashSet<>();
        Set<UserProfile> visited = new HashSet<>();
        Queue<UserProfile> queue = new LinkedList<>();
        queue.add(author);
        visited.add(author);
        while (!queue.isEmpty()) {
            UserProfile currentUser = queue.poll();
            for (UserProfile friend : currentUser.getFriends()) {
                if (!visited.contains(friend)) {
                    visited.add(friend);
                    queue.add(friend);
                }
                if (!friend.equals(author) && !reachedUsers.contains(friend)) {
                    Collection<Interest> friendInterests = friend.getInterests();
                    boolean hasCommonInterest = false;
                    for (Interest interest : authorInterests) {
                        if (friendInterests.contains(interest)) {
                            hasCommonInterest = true;
                            break;
                        }
                    }
                    if (hasCommonInterest) reachedUsers.add(friend);
                }
            }
        }
        return reachedUsers;
    }

    @Override
    public Set<UserProfile> getMutualFriends(UserProfile userProfile1, UserProfile userProfile2)
            throws UserRegistrationException {
        try {
            if (userProfile1 == null || userProfile2 == null)
                throw new IllegalArgumentException("Null profiles cannot have friends!");
            if (!users.contains(userProfile1) || !users.contains(userProfile2))
                throw new UserRegistrationException("Non-registered profiles cannot have friends!");
        } catch (IllegalArgumentException e) {
            throw e;
        }

        Collection<UserProfile> friends1 = userProfile1.getFriends();
        Collection<UserProfile> friends2 = userProfile2.getFriends();
        Set<UserProfile> mutualFriends = new HashSet<>();

        // Find mutual friends without using stream or filter
        for (UserProfile friend : friends1) {
            if (friends2.contains(friend)) {
                mutualFriends.add(friend);
            }
        }

        return mutualFriends;
    }

    @Override
    public SortedSet<UserProfile> getAllProfilesSortedByFriendsCount() {
        SortedSet<UserProfile> sortedProfiles = new TreeSet<>(new Comparator<>() {
            @Override
            public int compare(UserProfile u1, UserProfile u2) {
                int friendCountComparison = Integer.compare(u2.getFriends().size(), u1.getFriends().size());
                if (friendCountComparison != 0) {
                    return friendCountComparison;
                }
                return u1.getUsername().compareTo(u2.getUsername());
            }
        });

        sortedProfiles.addAll(users);
        return sortedProfiles;
    }
}

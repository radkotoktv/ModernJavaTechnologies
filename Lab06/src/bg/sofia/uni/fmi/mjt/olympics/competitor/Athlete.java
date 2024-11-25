package bg.sofia.uni.fmi.mjt.olympics.competitor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Athlete implements Competitor {

    private final String identifier;
    private final String name;
    private final String nationality;

    private final Set<Medal> medals;

    public Athlete(String identifier, String name, String nationality) {
        validateString(identifier, "Identifier");
        validateString(name, "Name");
        validateString(nationality, "Nationality");

        this.identifier = identifier;
        this.name = name;
        this.nationality = nationality;
        this.medals = new HashSet<>();
    }

    public void addMedal(Medal medal) {
        validateMedal(medal);
        medals.add(medal);
    }

    private void validateString(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("String cannot be null or empty");
        }
    }

    private void validateMedal(Medal medal) {
        if (medal == null) {
            throw new IllegalArgumentException("Medal cannot be null");
        }
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNationality() {
        return nationality;
    }

    @Override
    public Set<Medal> getMedals() {
        return Collections.unmodifiableSet(medals);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Athlete athlete = (Athlete) o;
        return Objects.equals(identifier, athlete.identifier) &&
                Objects.equals(name, athlete.name) &&
                Objects.equals(nationality, athlete.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, name, nationality);
    }

    @Override
    public int compareTo(Competitor o) {
        if (o == null) {
            throw new IllegalArgumentException("Competitor cannot be null");
        }
        int medalComparison = Integer.compare(o.getMedals().size(), this.getMedals().size());
        if (medalComparison != 0) {
            return medalComparison;
        }
        return this.name.compareTo(o.getName());
    }
}

import com.google.gson.annotations.SerializedName;

public class Developer {
    private String name;
    @SerializedName("DevAge")
    private int age;

    public Developer(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}

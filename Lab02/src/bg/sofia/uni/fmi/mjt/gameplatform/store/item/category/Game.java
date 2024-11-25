package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Game implements StoreItem {
    private String title;
    private BigDecimal price;
    private LocalDateTime releaseDate;
    private String genre;
    private boolean discounted = false;
    private double rating;
    public Game(String title, BigDecimal price, LocalDateTime releaseDate, String genre)
    {
        this.title = title;
        this.price = price;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.rate(0);
    }

    @Override
    public String getTitle()
    {
        return this.title;
    }

    @Override
    public BigDecimal getPrice()
    {
        return this.price;
    }

    @Override
    public double getRating() {
        return this.rating;
    }

    @Override
    public LocalDateTime getReleaseDate()
    {
        return this.releaseDate;
    }

    @Override
    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    @Override
    public void setReleaseDate(LocalDateTime releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    @Override
    public void rate(double rating)
    {
        this.rating = rating;
    }

    @Override
    public boolean getDiscounted()
    {
        return this.discounted;
    }

    @Override
    public void setDiscounted(boolean discounted)
    {
        this.discounted = discounted;
    }
}

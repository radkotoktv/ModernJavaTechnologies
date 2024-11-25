package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GameBundle implements StoreItem {
    private String title;
    private BigDecimal price;
    private LocalDateTime releaseDate;
    private Game[] games;
    private boolean discounted = false;
    private double rating;
    public GameBundle(String title, BigDecimal price, LocalDateTime releaseDate, Game[] games)
    {
        this.title = title;
        this.price = price;
        this.releaseDate = releaseDate;
        this.games = games;
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
        double sum = 0;
        for(Game game: games)
        {
            sum += game.getRating();
        }
        return sum/games.length;
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
    public void setDiscounted(boolean discounted)
    {
        this.discounted = discounted;
    }

    @Override
    public boolean getDiscounted()
    {
        return this.discounted;
    }
}

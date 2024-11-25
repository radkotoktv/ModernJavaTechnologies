package bg.sofia.uni.fmi.mjt.gameplatform.store;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.ItemFilter;

import java.math.BigDecimal;

public class GameStore implements StoreAPI {
    private final StoreItem[] availableItems;
    public GameStore(StoreItem[] availableItems)
    {
        this.availableItems = availableItems;
    }

    @Override
    public StoreItem[] findItemByFilters(ItemFilter[] itemFilters)
    {
        StoreItem[] toReturn = new StoreItem[availableItems.length];
        int idx = 0;
        for(StoreItem item : availableItems)
        {
            boolean matches = true;
            for(ItemFilter filter : itemFilters)
            {
                if(!filter.matches(item))
                {
                    matches = false;
                }
            }
            if(matches)
            {
                toReturn[idx] = item;
                idx++;
            }
        }
        return toReturn;
    }

    @Override
    public void applyDiscount(String promoCode)
    {
        for(StoreItem item: availableItems)
        {
            if(item.getDiscounted())
            {
                continue;
            }
            if(promoCode.equals("VAN40"))
            {
                item.setPrice(item.getPrice().multiply(BigDecimal.valueOf(0.6)));
                item.setDiscounted(true);
            }
            else if(promoCode.equals("100YO"))
            {
                item.setPrice(BigDecimal.valueOf(0.0));
                item.setDiscounted(true);
            }
        }
    }

    @Override
    public boolean rateItem(StoreItem item, int rating)
    {
        if(rating < 1 || rating > 5)
        {
            return false;
        }
        item.rate(rating);
        return item.getRating() == rating;
    }
}

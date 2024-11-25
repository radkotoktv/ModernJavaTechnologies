package bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

public class TitleItemFilter implements ItemFilter {
    private final String title;
    private final boolean caseSensitive;
    public TitleItemFilter(String title, boolean caseSensitive)
    {
        this.title = title;
        this.caseSensitive = caseSensitive;
    }

    @Override
    public boolean matches(StoreItem item) {
        return caseSensitive ? item.getTitle().equals(title) : item.getTitle().equalsIgnoreCase(title);
    }
}

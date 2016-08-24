package com.brandonio21.chloromedia.ExclusionTables;

import com.brandonio21.chloromedia.Data.ProviderItem;

public interface ExclusionTable {
    void excludeItem(ProviderItem item);
    void unexcludeItem(ProviderItem item);
    boolean isExcluded(ProviderItem item);
}

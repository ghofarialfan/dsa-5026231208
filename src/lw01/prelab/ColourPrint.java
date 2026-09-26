public class ColourPrint extends PrintJob {
    private static final int FIRST_TIER_PAGES = 10;
    private static final int FIRST_TIER_PRICE = 1500;
    private static final int NEXT_TIER_PRICE = 1000;
    private static final int SETUP_FEE_PER_COPY = 2000;

    public ColourPrint(String id, int pages) {
        super(id, pages);
    }

    @Override
    public int calculateCharge() {
        int pages = getPages();
        int pageCharge;
        if (pages <= FIRST_TIER_PAGES) {
            pageCharge = pages * FIRST_TIER_PRICE;
        } else {
            pageCharge = FIRST_TIER_PAGES * FIRST_TIER_PRICE
                    + (pages - FIRST_TIER_PAGES) * NEXT_TIER_PRICE;
        }
        return pageCharge + SETUP_FEE_PER_COPY;
    }

    @Override
    public String label() {
        return "Colour";
    }
}

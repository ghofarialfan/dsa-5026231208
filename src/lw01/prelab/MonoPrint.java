public class MonoPrint extends PrintJob {
    private static final int PRICE_PER_PAGE = 500;

    public MonoPrint(String id, int pages) {
        super(id, pages);
    }

    @Override
    public int calculateCharge() {
        return getPages() * PRICE_PER_PAGE;
    }

    @Override
    public String label() {
        return "Mono";
    }
}

public abstract class PrintJob implements Chargeable {
    private final String id;
    private final int pages;

    protected PrintJob(String id, int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException("pages must be greater than zero");
        }
        this.id = id;
        this.pages = pages;
    }

    protected int getPages() {
        return pages;
    }

    @Override
    public abstract int calculateCharge();

    public int calculateCharge(int copies) {
        if (copies <= 0) {
            throw new IllegalArgumentException("copies must be greater than zero");
        }
        return copies * calculateCharge();
    }

    public String label() {
        return "Print";
    }

    public final String summary() {
        return id + " | " + label() + " | " + calculateCharge();
    }
}

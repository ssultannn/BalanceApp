package az.developia.BalanceApp.enums;

public enum SortBy {
	 DATE("date"),
	    AMOUNT("amount");

	    private final String value;

	    SortBy(String value) {
	        this.value = value;
	    }

	    public String getValue() {
	        return value;
	    }
}

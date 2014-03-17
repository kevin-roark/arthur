import java.util.HashMap;

public class SymbolTable {

    private HashMap<String, Object> table;
    private SymbolTable prev;

    public SymbolTable() {
        this.table = new HashMap<String, Object>();
        this.prev = null;
    }

    public SymbolTable(SymbolTable prev) {
        this.table = new HashMap<String, Object>();
        this.prev = prev;
    }

    public HashMap<String, Object> getMap() {
        return this.table;
    }

    public SymbolTable getPrev() {
        return this.prev;
    }
}
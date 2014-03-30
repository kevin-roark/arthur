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

    public void put(String key, Object val) {
      this.getMap().put(key, val);
    }

    public Object get(String key) {
      SymbolTable cur = this;
      Object val = null;
      while(cur != null) {
        val = cur.getMap().get(key);
        if (val != null)
          return val;
        cur = cur.getPrev();
      }
      return null;
    }

    public static SymbolTable getGlobalTable() {
      SymbolTable table = new SymbolTable();

      Function get = new Function("get", "Media");
      table.put("get", get);

      Function add = new Function("add", "void");
      table.put("add", add);

      Function ms = new Function("ms", "num");
      table.put("ms", ms);

      addColors(table);

      return table;
    }

    public static void addColors(SymbolTable table) {
      table.put("BLUE", new Color(0, 0, 255));
      table.put("GREEN", new Color(0, 255, 0));
      table.put("RED", new Color(255, 0, 0));
    }
}

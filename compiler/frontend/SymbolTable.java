import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private HashMap<String, Object> table;
    private SymbolTable prev;
    private String title;
    private int entryCount;

    public SymbolTable() {
        this.table = new HashMap<String, Object>();
        this.prev = null;
        this.title = "untitled";
        this.entryCount = 0;
    }

    public SymbolTable(SymbolTable prev) {
        this.table = new HashMap<String, Object>();
        this.prev = prev;
        this.title = "untitled";
        this.entryCount = 0;
    }

    public SymbolTable(SymbolTable prev, String title) {
        this.table = new HashMap<String, Object>();
        this.prev = prev;
        this.title = title;
        this.entryCount = 0;
    }

    public HashMap<String, Object> getMap() {
        return this.table;
    }

    public SymbolTable getPrev() {
        return this.prev;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void put(String key, Object val) {
      this.getMap().put(key, val);
      this.entryCount++;
    }

    public Object get(String key) {
      SymbolTable cur = this;
      Object val = null;
      while(cur != null) {
        val = cur.getMap().get(key);
        //System.out.println(val);
        if (val != null)
          return val;
        cur = cur.getPrev();
      }
      return null;
    }

    public String toString() {
      String s = "Symbol table | " + this.title + " | Entries: " + this.entryCount;
      for (Map.Entry<String, Object> entry : this.getMap().entrySet()) {
        String name = entry.getKey();
        Object token = entry.getValue();
        s += "\n  " + name + ": " + token;
      }
      return s;
    }

    public static SymbolTable getGlobalTable() {
      SymbolTable table = new SymbolTable();
      table.setTitle("global");

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
      table.put("WHITE", new Color(255, 255, 255));
    }
}

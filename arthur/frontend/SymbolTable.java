package arthur.frontend;

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

      Function img = new Function("image", "Image");
      table.put("image", img);

      Function vid = new Function("vid", "Video");
      table.put("video", vid);

      Function sound = new Function("sound", "Sound");
      table.put("sound", sound);

      Function frame = new Function("frame", "Frame");
      table.put("frame", frame);

      Function add = new Function("add", "void");
      table.put("add", add);

      Function ms = new Function("ms", "num");
      table.put("ms", ms);

      Function rand = new Function("rand", "num");
      table.put("rand", rand);

      Function cooler = new Function("cooler", "color");
      table.put("cooler", cooler);

      addColors(table);

      return table;
    }

    public static Var colorVar(String name) {
      return new Var(name, "color");
    }

    public static void addColors(SymbolTable table) {
      table.put("RED", colorVar("RED"));
      table.put("WHITE", colorVar("WHITE"));
      table.put("BLACK", colorVar("BLACK"));
      table.put("BLUE", colorVar("BLUE"));
      table.put("GREEN", colorVar("GREEN"));
      table.put("ORANGE", colorVar("ORANGE"));
      table.put("YELLOW", colorVar("YELLOW"));
      table.put("PERRYWINKLE", colorVar("PERRYWINKLE"));
      table.put("ARTHURS_SKIN", colorVar("ARTHURS_SKIN"));
      table.put("SARCOLINE", colorVar("SARCOLINE"));
      table.put("COQUELICOT", colorVar("COQUELICOT"));
      table.put("SMARAGDINE", colorVar("SMARAGDINE"));
      table.put("ALMOND", colorVar("ALMOND"));
      table.put("ASPARAGUS", colorVar("ASPARAGUS"));
      table.put("BURNT_SIENNA", colorVar("BURNT_SIENNA"));
      table.put("CERULEAN", colorVar("CERULEAN"));
      table.put("DANDELION", colorVar("DANDELION"));
      table.put("DENIM", colorVar("DENIM"));
      table.put("ELECTRIC_LIME", colorVar("ELECTRIC_LIME"));
      table.put("FUZZY_WUZZY", colorVar("FUZZY_WUZZY"));
      table.put("GOLDENROD", colorVar("GOLDENROD"));
      table.put("JAZZBERRY_JAM", colorVar("JAZZBERRY_JAM"));
      table.put("MAC_AND_CHEESE", colorVar("MAC_AND_CHEESE"));
      table.put("MAHOGANY", colorVar("MAHOGANY"));
      table.put("MANGO_TANGO", colorVar("MANGO_TANGO"));
      table.put("MAUVELOUS", colorVar("MAUVELOUS"));
      table.put("PURPLE_PIZZAZZ", colorVar("PURPLE_PIZZAZZ"));
      table.put("RAZZMATAZZ", colorVar("RAZZMATAZZ"));
      table.put("SALMON", colorVar("SALMON"));
      table.put("SILVER", colorVar("SILVER"));
      table.put("TICKLE_ME_PINK", colorVar("TICKLE_ME_PINK"));
      table.put("WILD_BLUE_YONDER", colorVar("WILD_BLUE_YONDER"));
      table.put("WISTERIA", colorVar("WISTERIA"));
      table.put("LASER_LEMON", colorVar("LASER_LEMON"));
      table.put("EGGPLANT", colorVar("EGGPLANT"));
      table.put("CHARTREUSE", colorVar("CHARTREUSE"));
    }
}

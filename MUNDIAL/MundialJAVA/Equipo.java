import java.util.HashMap;
import java.util.Map;

public class Equipo {
    private String nombre;
    private Map<String, Integer> estadisticas;

    public Equipo(String nombre) {
        this.nombre = nombre;
        this.estadisticas = new HashMap<>(Map.of("PJ", 0, "G", 0, "E", 0, "P", 0, "GF", 0, "GC", 0, "Pts", 0));
    }

    public String getNombre() {
        return nombre;
    }

    public Map<String, Integer> getEstadisticas() {
        return estadisticas;
    }

    public void actualizarEstadisticas(int pj, int g, int e, int p, int gf, int gc, int pts) {
        estadisticas.put("PJ", estadisticas.get("PJ") + pj);
        estadisticas.put("G", estadisticas.get("G") + g);
        estadisticas.put("E", estadisticas.get("E") + e);
        estadisticas.put("P", estadisticas.get("P") + p);
        estadisticas.put("GF", estadisticas.get("GF") + gf);
        estadisticas.put("GC", estadisticas.get("GC") + gc);
        estadisticas.put("Pts", estadisticas.get("Pts") + pts);
    }
}
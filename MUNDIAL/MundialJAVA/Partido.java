public class Partido {
    String equipoLocal;
    int golesLocal;
    String equipoVisitante;
    int golesVisitante;
    String estadio;

    public Partido(String equipoLocal, int golesLocal, String equipoVisitante, int golesVisitante, String estadio) {
        this.equipoLocal = equipoLocal;
        this.golesLocal = golesLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesVisitante = golesVisitante;
        this.estadio = estadio;
    }
}
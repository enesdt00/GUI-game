public class GameOfLifeKontroll{
    private GameOfLifeModel model;
    private GameOfLifeView view;
  

    public void start(){
        model = new GameOfLifeModel();
       model.lagRutenett();
        view = new GameOfLifeView(this);
    }
    public void hentRutenett(){
        model.lagRutenett();
    }
    public void antallLevde(){
        int levende=model.hentAntallLevende();
        view.hentAntallLevende(levende);
    }

    public char hentStatus(int rad, int kol) {
        return model.hentStatus(rad, kol);
    }

    public void oppdatering(int rad, int kol) {
        model.oppdatering(rad, kol);
       // view.oppdatering();
       
        
    }
}
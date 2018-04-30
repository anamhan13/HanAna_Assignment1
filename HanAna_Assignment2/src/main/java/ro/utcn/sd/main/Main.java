package ro.utcn.sd.main;

import javafx.application.Application;
import javafx.stage.Stage;
import ro.utcn.sd.controller.LoginController;
import ro.utcn.sd.view.LoginView;

public class Main extends Application{

	private Stage primaryStage;
	
	
    public static void main(String[] args) 
    {
       
//    	Tournament tournament = new Tournament();
//    	tournament.setName("RomCup");
//    	tournament.setPlace("Bucharest");
//    	tournament.setStatus("Upcoming");
//    	
//    	TournamentDao tournamentDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getTournamentDao();
//    	tournamentDao.insert(tournament);
//
//    	tournamentDao.closeConnection();
    	
    	/*
    	Player p1 = new Player("Ana", "ana", "hehe", "Teius", true,14);
    	Player p2 = new Player("Carina", "cary", "nioh", "Teius", false,20);
    	Player p3 = new Player("Anca", "anchis", "hihi", "Alba", false,10);
    	
    	PlayerDao playerDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getPlayerDao(); 
    	
    	
    	playerDao.insert(p1);
    	playerDao.insert(p2);
    	playerDao.insert(p3);
    	
    	
    	Set<Player> playersSet = new HashSet<>();
    	playersSet.add(p2);
    	playersSet.add(p3);
    	
    	
    	TournamentDao tournamentDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getTournamentDao();
    	Tournament tournament = tournamentDao.find(1);
    	
    	tournament.setPlayersList(playersSet);
    	
    	Set<Tournament> tournamentsSet = new HashSet<Tournament>();
    	tournamentsSet.add(tournament);
    	
    	p2.setTournamentsList(tournamentsSet);
    	p3.setTournamentsList(tournamentsSet);
    	
    	tournamentDao.update(tournament);
    	playerDao.update(p2);
    	playerDao.update(p3);
    	
    	tournamentDao.closeConnection();
    	playerDao.closeConnection();
    	*/
    	
    	/*
    	PlayerDao playerDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getPlayerDao(); 
    	Player p1 = playerDao.find(5);
    	Player p2 = playerDao.find(6);
    	
    	TournamentDao tournamentDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getTournamentDao();
    	Tournament tournament = tournamentDao.find(1);
    	
    	Match match = new Match();
    	match.setPlayer1(p1);
    	match.setPlayer2(p2);
    	match.setTournament(tournament);
    	
    	MatchDao matchDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getMatchDao();
    	matchDao.insert(match);
    	
    	Set<Match> matchesSet = new HashSet<>();
    	matchesSet.add(match);
    	tournament.setMatches(matchesSet);
    	
    	tournamentDao.update(tournament);
    	
    	tournamentDao.closeConnection();
    	playerDao.closeConnection();
    	matchDao.closeConnection();
    	*/
    	
    	/*
    	MatchDao matchDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getMatchDao();
    	Match match = new Match();
    	match = matchDao.find(1);
    	
    	Game game = new Game();
    	game.setScore1(3);
    	game.setScore2(2);
    	game.setMatch(match);
    	
    	GameDao gameDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getGameDao();
    	gameDao.insert(game);
    	
    	Set<Game> gamesSet = new HashSet<>();
    	gamesSet.add(game);
    	match.setGames(gamesSet);
    	
    	matchDao.update(match);
    	
    	gameDao.closeConnection();
    	matchDao.closeConnection();
    	*/
    	
    	/*
    	MatchDao matchDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getMatchDao();
    	Match match = new Match();
    	match = matchDao.find(1);
    	
    	
    	
    	GameDao gameDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getGameDao();
    	
    	Game game2 = gameDao.find(1);
    
    	
    	gameDao.delete(game2);
    	
    	gameDao.closeConnection();
    	matchDao.closeConnection();
    	*/
    	//System.out.println( System.getProperties());
    	launch(args);
        System.out.println("Done");
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Login window");
		
		
		LoginView loginView = new LoginView(primaryStage);
		LoginController loginController = new LoginController(loginView);
		loginController.showLoginView();
	}

}

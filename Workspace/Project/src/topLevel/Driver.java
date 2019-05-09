package topLevel;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import scheduleByWeeks.Schedule;
import teamStructure.Conference;
import teamStructure.Division;
import teamStructure.Team;



public class Driver{
	public Conference AFC, NFC;
	public Schedule season;
	public PlayoffCalc playoffs;
	public static boolean allPlayed;
	public String lastUpdated;
	public double[][] full3232Array;
	public String[] names3232Array;
	public static SchedType type;
	public Driver() {
		type = SchedType.regularSeasonStarted;
		allPlayed = false;
		
		Team NE = new Team("New England Patriots", new Color(12, 35, 64), new Color(162, 170, 173)),
				NYJ = new Team("New York Jets", new Color(12, 55, 29), new Color(255, 255, 255)),
				BUF = new Team("Buffalo Bills", new Color(12, 46, 130), new Color(255, 0, 0)),
				MIA = new Team("Miami Dolphins", new Color(0, 142, 151), new Color(255, 255, 255)),
				HOU = new Team("Houston Texans", new Color(9, 31, 44), new Color(255, 0, 0)),
				IND = new Team("Indianapolis Colts", new Color(0, 20, 137), new Color(255, 255, 255)),
				JAX = new Team("Jacksonville Jaguars", new Color(0, 96, 115), new Color(255, 255, 255)),
				TEN = new Team("Tennessee Titans", new Color(65, 143, 222), new Color(255, 255, 255)),
				PIT = new Team("Pittsburgh Steelers", new Color(0, 0, 0), new Color(255, 184, 28)),
				CLE = new Team("Cleveland Browns", new Color(56, 47, 45), new Color(235, 51, 0)),
				BAL = new Team("Baltimore Ravens", new Color(26, 25, 95), new Color(255, 255, 255)),
				CIN = new Team("Cincinnati Bengals", new Color(252, 76, 2), new Color(0, 0, 0)),
				LAC = new Team("Los Angeles Chargers", new Color(12, 35, 64), new Color(255, 184, 28)),
				DEN = new Team("Denver Broncos", new Color(252, 76, 2), new Color(12, 35, 64)),
				OAK = new Team("Oakland Raiders", new Color(0, 0, 0), new Color(191, 192, 191)),
				KC = new Team("Kansas City Chiefs", new Color(200, 16, 46), new Color(255, 255, 255));

		Team GB = new Team("Green Bay Packers", new Color(23, 94, 34), new Color(255, 184, 28)),
				DET = new Team("Detroit Lions", new Color(0, 105, 177), new Color(162, 170, 173)),
				CHI = new Team("Chicago Bears", new Color(5, 28, 44), new Color(220, 68, 5)),
				MIN = new Team("Minnesota Vikings", new Color(84, 41, 109), new Color(255, 184, 28)),
				WSH = new Team("Washington Redskins", new Color(134, 38, 51), new Color(255, 205, 0)),
				DAL = new Team("Dallas Cowboys", new Color(4, 30, 66), new Color(134, 147, 151)),
				NYG = new Team("New York Giants", new Color(0, 30, 98), new Color(255, 0, 0)),
				PHI = new Team("Philadelphia Eagles", new Color(0, 72, 81), new Color(134, 147, 151)),
				NO = new Team("New Orleans Saints", new Color(162, 141, 91), new Color(0, 0, 0)),
				CAR = new Team("Carolina Panthers", new Color(0, 133, 202), new Color(255, 255, 255)),
				ATL = new Team("Atlanta Falcons", new Color(166, 25, 46), new Color(255, 255, 255)),
				TB = new Team("Tampa Bay Buccaneers", new Color(200, 16, 46), new Color(61, 57, 53)),
				SEA = new Team("Seattle Seahawks", new Color(0, 21, 51), new Color(77, 255, 0)),
				SF = new Team("San Francisco 49ers", new Color(157, 42, 70), new Color(255, 255, 255)),
				ARI = new Team("Arizona Cardinals", new Color(155, 39, 67), new Color(255, 255, 255)),
				LAR = new Team("Los Angeles Rams", new Color(4, 30, 66), new Color(255, 255, 255));

		Division AEast = new Division(NE, NYJ, BUF, MIA), AWest = new Division(LAC, DEN, OAK, KC),
				ASouth = new Division(HOU, IND, JAX, TEN), ANorth = new Division(PIT, CLE, BAL, CIN);

		Division NNorth = new Division(GB, DET, CHI, MIN), NEast = new Division(WSH, DAL, NYG, PHI),
				NSouth = new Division(NO, CAR, ATL, TB), NWest = new Division(SEA, SF, ARI, LAR);
		AFC = null;
		NFC = null;
		season = null;
		try {
			AFC = new Conference(ANorth, AEast, ASouth, AWest);
			NFC = new Conference(NNorth, NEast, NSouth, NWest);
			season = new Schedule(NFC, AFC);
		} catch (Exception e) {
			System.out.println("PROBLEMS");
			e.printStackTrace();
		}

		Team[] AFCTeams = AFC.places();
		Team[] NFCTeams = NFC.places();

		int count = 0;
		for (int i = 0; i < 16; i++) {
			if (Integer.parseInt(AFCTeams[i].getStats().GamesPlayed.text) == 16)
				count += 1;
			if (Integer.parseInt(NFCTeams[i].getStats().GamesPlayed.text) == 16)
				count += 1;
		}

		if (count == 32) {
			allPlayed = true;
			for (int i = 0; i < 16; i++) {
				AFCTeams[i].setWins(0);
				NFCTeams[i].setWins(0);
			}
			season.makeNotPlayed();
		}

		playoffs = new PlayoffCalc(NFC.seeding(), AFC.seeding());
		playoffs.calculate();
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		lastUpdated = dateFormat.format(date); //2016/11/16 12:08:43
		
		Team[] allTeams = new Team[32];
		names3232Array = new String[32];
		
		for(int i = 0; i < 16; i++)
		{
			allTeams[i] = AFCTeams[i];
			allTeams[i+16] = NFCTeams[i];
			names3232Array[i] = AFCTeams[i].name;
			names3232Array[i+16] = NFCTeams[i].name;
		}
		
		full3232Array = PlayoffCalc.makeFullPercents(allTeams);
	}

	public void toJSON() {
		ObjectMapper Obj = new ObjectMapper();
		Obj.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		try {

			// get Oraganisation object as a json string
			String str = "var forecastData =" + Obj.writeValueAsString(this);
			
			
			BufferedWriter write = new BufferedWriter(new FileWriter(new File("data.js")));
			write.write(str);
			write.close();
			System.out.println(str);
		}

		catch (IOException e) {
			System.out.println("PROBLEMS HERE");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Driver prog = new Driver();
		prog.toJSON();
		/*int count = 0;
		Team[] AFCTeams = prog.AFC.places();
		Team[] NFCTeams = prog.NFC.places();

//		for (int i = 0; i < 16; i++)
//		{
//			System.out.println(AFCTeams[i].wins);
//			System.out.println(NFCTeams[i].wins);
//		}

		System.out.println();
		AFCTeams = prog.AFC.places();
		NFCTeams = prog.NFC.places();
		System.out.println("AFC");
		for (int i = 0; i < 16; i++) {
			System.out.println(AFCTeams[i]);
		}
		System.out.println();
		System.out.println("NFC");
		for (int i = 0; i < 16; i++) {
			System.out.println(NFCTeams[i]);
		}

		System.out.println("STANDINGS");
		System.out.println();
		System.out.println("AFC East");
		System.out.println(prog.AFC.East);

		System.out.println();
		System.out.println("AFC North");
		System.out.println(prog.AFC.North);

		System.out.println();
		System.out.println("AFC South");
		System.out.println(prog.AFC.South);

		System.out.println();
		System.out.println("AFC West");
		System.out.println(prog.AFC.West);

		System.out.println();
		System.out.println("NFC East");
		System.out.println(prog.NFC.East);

		System.out.println();
		System.out.println("NFC North");
		System.out.println(prog.NFC.North);

		System.out.println();
		System.out.println("NFC South");
		System.out.println(prog.NFC.South);

		System.out.println();
		System.out.println("NFC West");
		System.out.println(NWest);

		double sumWins = 0;
		for (int i = 0; i < 16; i++) {
			sumWins += AFCTeams[i].wins;
			sumWins += NFCTeams[i].wins;
		}

		System.out.println();
		System.out.print(sumWins);
		
		
		System.out.println(prog.NFC.West);*/

	}

}

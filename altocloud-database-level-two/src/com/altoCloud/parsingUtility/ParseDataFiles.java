
package com.altoCloud.parsingUtility;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;

import com.altoCloud.dbQuery.StationDetailsExtraQuery;
import com.altoCloud.dbQuery.StationDetailsQuery;
import com.altoCloud.dbQuery.WeatherQuery;
import com.altoCloud.domain.StationDetailsExtra;
import com.altoCloud.domain.Station_Details;
import com.altoCloud.domain.StationDetailsKey;
import com.altoCloud.domain.Weather;
//import com.altoCloud.domain.level3.StationDetailsExtra;


/**
 * @author RENISH
 * 
 */
public class ParseDataFiles {

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {

		// new ParseDataFiles().parseWeatherDataFile();
		// new ParseDataFiles().parseMesowestTblFile();
		//new ParseDataFiles().parseMesowestCSVTblFile();
		// new ParseDataFiles().parseWeatherDataFile();
		WeatherQuery wr= new WeatherQuery();
		
		double tmp= wr.findAverageTempByState("UT", 10);
		 System.out.println(tmp);
		 List<Weather> data=wr.getAllByMnetId(8);
		 System.out.println(data.size());
		 
		 
		// ArrayList<Station_Details> sd= new ArrayList<Station_Details>();
		// StationDetailsQuery sq = new StationDetailsQuery();
		// sd= sq.findById("D0691");
		// System.out.println(sd.size());
		// if(sd.size()== 0){
		// System.out.println("null");
		// }
		//

		// StationDetailsQuery sq= new StationDetailsQuery();
		// List<Station_Details> list= new ArrayList<Station_Details>();
		// list= sq.findById("ABAUT");
		// Station_Details ad= sq.findByKey("ABAUT", "Abajo Peak");

		// System.out.println(ad.getStn_name());

	}

	public void parseMesowestCSVTblFile() {
		System.out.println("gsa");
		try {
			FileInputStream fstream = new FileInputStream(
					"c:/testfolder/mesowest_csv_tbl_Sat Oct 13 224646 PDT 2012.out");

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine = "";
			// Read File Line By Line
			strLine = br.readLine();

			// Read File Line By Line
			String data[] = new String[22];
			StationDetailsExtraQuery query = new StationDetailsExtraQuery();
			StationDetailsQuery sq = new StationDetailsQuery();
			while ((strLine = br.readLine()) != null) {
				System.out.println("whiel started");
				System.out.println("" + strLine);
				data = strLine.split(",");
				// System.out.println("hUnGrY ReNiSh" + data.length);

				// 0-1-2 // primary id,secondary id,station name,
				// 3,4,5 //state,country,latitude,
				// 6,7,8 //longitude,elevation,mesowest network id,
				// 9,10,11 //network name,status,primary provider id,
				// 12,13,14 //primary provider,secondary provider
				// id,secondaryprovider,
				// 15,16,17 //tertiary provider id,tertiary provider,wims_id;

				// KIYA,,Abbeville Chris Crusta Memorial
				// Airport,LA,US,29.97578,-92.08422,
				// 4.9,1,NWS/FAA,ACTIVE,2,National Weather Service,,,,,;
				// Level-2 Weather Domain
				// Station_details_key key =new Station_details_key();
				// key.setStn(data[0]);
				// key.setStn_name(data[2]);
				StationDetailsExtra detailsExtra = new StationDetailsExtra();
				Station_Details station_Details = new Station_Details();
				ArrayList<Station_Details> stationlist = new ArrayList<Station_Details>();
				stationlist = sq.findById(data[0]);
				if (stationlist.size() > 1) {
					double templat = Double.parseDouble(data[5]);
					double templon = Double.parseDouble(data[6]);
					double roundlat = (Math.round(templat * 100.0) / 100.0) * 100;
					double roundlon = (Math.round(templon * 100.0) / 100.0) * 100;
					for (int p = 0; p < stationlist.size(); p++) {
						if ((stationlist.get(p).getLat() == roundlat)
								&& (stationlist.get(p).getLon() == roundlon)) {
							station_Details = stationlist.get(p);
						}

					}
				} else {
					if (stationlist.size() == 1) {
						System.out.println("sttaion size is 1");
						station_Details = stationlist.get(0);
					} else if (stationlist.size() == 0) {
						//station_Details = sq.findByKey(data[0], data[2]);
						// if(station_Details==null){
						System.out.println("Station not present");
						station_Details.setOther_id(9999999);
						StationDetailsKey key1 = new StationDetailsKey();
						key1.setStn(data[0]);
						key1.setStn_name(data[2]);
						station_Details.setStn(data[0]);
						station_Details.setStn_name(data[2]);
						// station_Details.setKey(key1);
						station_Details.setCountry(data[4]);
						station_Details.setState(data[3]);
						station_Details.setLat(Double.parseDouble(data[5]));
						station_Details.setLon(Double.parseDouble(data[6]));
						station_Details.setElev(Double.parseDouble(data[7]));
						station_Details.setMnet(Integer.parseInt(data[8]));
						station_Details.setState(data[10]);
						sq.add(station_Details);

					}
				}

				// if (stationlist.size() == 0) {
				// System.out.println("Station not present");
				// station_Details.setOther_id(9999999);
				// station_Details.setStn(data[0]);
				// station_Details.setStn_name(data[2]);
				// station_Details.setCountry(data[4]);
				// station_Details.setState(data[3]);
				// station_Details.setLat(Double.parseDouble(data[5]));
				// station_Details.setLon(Double.parseDouble(data[6]));
				// station_Details.setElev(Double.parseDouble(data[7]));
				// station_Details.setMnet(Integer.parseInt(data[8]));
				// station_Details.setState(data[10]);
				// sq.add(station_Details);
				//
				// }
				// detailsExtra.setStnSecId(data[1]);
				detailsExtra.setStn_details(station_Details);
				detailsExtra.setSecondary_id(data[1]);
				detailsExtra.setNetwork_name(data[9]);
				if (data[11].length() != 0)
					detailsExtra.setPrimary_provider_id(Integer
							.parseInt(data[11]));
				if (data[12].length() != 0)
					detailsExtra.setPrimary_provider(data[12]);

				if (data[13].length() != 0) {
					detailsExtra.setSecondary_provider_id(Integer
							.parseInt(data[13]));
				}
				if (data[14].length() != 0) {
					detailsExtra.setSecondary_provider(data[14]);
				}
				if (data[15].length() != 0) {
					detailsExtra.setTertiary_provider_id(Integer
							.parseInt(data[15]));
				}
				if (data[16].length() != 0) {
					detailsExtra.setTertiary_provider(data[16]);
				}
				query.add(detailsExtra);
				System.out.println("Station Extra details stored");

			}

			// Close the input stream

			in.close();
		} catch (Exception e) {
			try {
				Thread.sleep(50000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public void parseWeatherDataFile() throws ParseException {
		StationDetailsQuery sq = new StationDetailsQuery();
		WeatherQuery wq = new WeatherQuery();
		try {
			FileInputStream fstream = new FileInputStream(
					"c:/testfolder/mesowest_out_Fri Oct 12 183726 PDT 2012.out");

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;
			String pattern = "\\s{1,}";

			// Read File Line By Line
			for (int i = 0; i < 4; i++) {
				strLine = br.readLine();
			}

			// Read File Line By Line
			String data[] = null;
			String stationName = "";
			while ((strLine = br.readLine()) != null) {
				stationName = "";
				strLine = strLine.replaceAll(pattern, "|");
				System.out.println("" + strLine);
				data = strLine.split("\\|");
				// System.out.println("hUnGrY ReNiSh" + data.length);
				// PARM =
				// MNET;SLAT;SLON;SELV;TMPF;SKNT;DRCT;GUST;PMSL;ALTI;DWPF;RELH;WTHR;P24I
				// |BULLF|20121003/0215|8.00|37.52|-110.73|1128.00|78.70|2.74|264.20|3.39|-9999.00|-9999.00|46.41|32.09|-9999.00|-9999.00

				// Level-2 Weather Domain
				Weather weather = new Weather();
				// weather.setStationCode(data[1]);
				// sq.findById(data[1]);
				Station_Details station_Details = new Station_Details();
				ArrayList<Station_Details> stationlist = new ArrayList<Station_Details>();
				stationlist = sq.findById(data[1]);
				if (stationlist.size() > 1) {
					double templat = Double.parseDouble(data[4]) * 100;
					double templon = Double.parseDouble(data[5]) * 100;
					for (int p = 0; p < stationlist.size(); p++) {
						if ((stationlist.get(p).getLat() == templat)
								&& (stationlist.get(p).getLon() == templon)) {
							station_Details = stationlist.get(p);
						}

					}
				} else
					station_Details = stationlist.get(0);
				weather.setStn_id(station_Details);
				// SimpleDateFormat dateFormat = new SimpleDateFormat(
				// "yy-MM-dd hh:mm");
				// java.util.Date parsedDate = null;
				// try {
				// parsedDate = dateFormat.parse(data[2]);
				// } catch (ParseException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// java.sql.Timestamp timestamp = new java.sql.Timestamp(
				// parsedDate.getTime());
				// weather.setDate(timestamp);
				// weather.setTimestamp(data[2]);
				final String INPUT1 = "yyyyMMdd/HHmm";
				final String OUTPUT1 = "yyyy-MM-dd HH.mm";
				String in1 = data[2];
				java.util.Date date = (new SimpleDateFormat(INPUT1)).parse(in1);
				Timestamp t = new Timestamp(date.getTime());
				weather.setDate(t);
				// System.out.println(call);
				// weather.setMNET(data[3]);
				// weather.setSLAT(data[4]);
				// weather.setSLON(data[5]);
				// weather.setSELV(data[6]);
				weather.setTMPF(Double.parseDouble(data[7]));
				weather.setSKNT(Double.parseDouble(data[8]));
				weather.setDRCT(Double.parseDouble(data[9]));
				weather.setGUST(Double.parseDouble(data[10]));
				weather.setPMSL(Double.parseDouble(data[11]));
				weather.setALTI(Double.parseDouble(data[12]));
				weather.setDWPF(Double.parseDouble(data[13]));
				weather.setRELH(Double.parseDouble(data[14]));
				weather.setWTHR(Double.parseDouble(data[15]));
				weather.setP24I(Double.parseDouble(data[16]));

				System.out.println("Weather data stored");
				wq.add(weather);
			}

			// Close the input stream

			in.close();
		} catch (Exception e) {
			try {
				Thread.sleep(50000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	public void parseMesowestTblFile() {

		try {
			FileInputStream fstream = new FileInputStream(
					"c:/testfolder/mesowest_tbl_Sat Oct 13 214255 PDT 2012.out");
			StationDetailsQuery sq = new StationDetailsQuery();
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine = null;
			String pattern = "\\s{1,}";

			// Read File Line By Line
			String data[] = null;
			String stationName = "";
			while ((strLine = br.readLine()) != null) {

				stationName = "";
				strLine = strLine.replaceAll(pattern, "|");
				System.out.println("" + strLine);
				data = strLine.split("\\|");
				// System.out.println("renish mad" + data.length);

				// MesowestTblStationInfo stationInfo = new
				// MesowestTblStationInfo();
				Station_Details stationInfo = new Station_Details();
				// Station_details_key key = new Station_details_key();
				// key.setStn(data[0]);

				stationInfo.setStn(data[0]);
				stationInfo.setOther_id(Long.parseLong(data[1]));
				for (int i = 2; i < data.length - 7; i++) {
					stationName = stationName + data[i] + " ";
				}
				System.out.println("Station Name:" + stationName);
				stationInfo.setStn_name(stationName);
				// stationInfo.setKey(key);
				stationInfo.setState(data[data.length - 7]);
				stationInfo.setCountry(data[data.length - 6]);
				stationInfo.setLat(Double.parseDouble(data[data.length - 5]));
				stationInfo.setLon(Double.parseDouble(data[data.length - 4]));
				stationInfo.setElev(Double.parseDouble(data[data.length - 3]));
				stationInfo.setMnet(Integer.parseInt(data[data.length - 2]));
				stationInfo.setStatus(data[data.length - 1]);

				System.out.println("Station Details stored");
				sq.add(stationInfo);
			}

			// Close the input stream

			in.close();
		} catch(ConstraintViolationException e) {
		    System.out.println("exception caught");
		} catch(JDBCConnectionException e) {
			System.out.println("exception jdbc caught");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

package com.altoCloud.parsingUtility;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.altoCloud.dbQuery.StationDetailsExtraQuery;
import com.altoCloud.dbQuery.StationDetailsQuery;
import com.altoCloud.dbQuery.WeatherQuery;
import com.altoCloud.domain.StationDetailsExtra;
import com.altoCloud.domain.Station_Details;
import com.altoCloud.domain.Weather;

//import com.altoCloud.domain.level3.StationDetailsExtra;

/**
 * @author RENISH
 * 
 */
public class StoreData {
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {

		String filename;
		long start = System.currentTimeMillis();
		filename = "c:/testfolder/mesowest_csv_tbl_Sat Oct 13 224646 PDT 2012.out";
		new StoreData().parseMesowestCSVTblFile2(filename);
		filename = "c:/testfolder/mesowest_csv_tbl_Mon Oct 15 125808 PDT 2012.out";
		new StoreData().parseMesowestCSVTblFile2(filename);
		filename = "c:/testfolder/mesowest_out_Fri Oct 12 192230 PDT 2012.out";
		new StoreData().parseWeatherDataFile(filename);
		filename = "c:/testfolder/mesowest_out_Mon Oct 15 125816 PDT 2012.out";
		new StoreData().parseWeatherDataFile(filename);

		filename = "c:/testfolder/mesowest_out_Mon Oct 15 132818 PDT 2012.out";
		new StoreData().parseWeatherDataFile(filename);
		long stop = System.currentTimeMillis();
		System.out.println("time" + (stop - start));
		WeatherQuery wr = new WeatherQuery();
		System.out.println(wr.findAverageTempByState("UT", 10));
		System.out.println(wr.findAverageTempByState("UT", 10));
		// System.out.println(wr.findHottestState(10));
		// System.out.println("time"+ (stop-start));

		// double tmp= wr.findAverageTempByState("UT", 10);
		// System.out.println(tmp);
		// List<Weather> data=wr.getAllByMnetId(8);
		// System.out.println(data.size());

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

	public void parseMesowestCSVTblFile2(String filename) {
		System.out.println("gsa");
		try {
			FileInputStream fstream = new FileInputStream(filename);

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine = "";
			// Read File Line By Line
			strLine = br.readLine();

			// Read File Line By Line
			String data[] = new String[22];
			// StationDetailsExtraQuery query = new StationDetailsExtraQuery();
			int count = 0;
			StationDetailsExtraQuery sq = new StationDetailsExtraQuery();
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
				Station_Details station_Details = parseStationDetails(data);

				detailsExtra.setStn_details(station_Details);
			//	detailsExtra.setSecondary_id(data[1]);

				// if (data[11].length() != 0)
				detailsExtra.setPrimary_provider_id(parseIntValue(data[11]));
				if (data[12].length() != 0)
					detailsExtra.setPrimary_provider(data[12]);

				if (data[13].length() != 0) {
					detailsExtra
							.setSecondary_provider_id(parseIntValue(data[13]));
				}
				if (data[14].length() != 0) {
					detailsExtra.setSecondary_provider(data[14]);
				}
				if (data[15].length() != 0) {
					detailsExtra
							.setTertiary_provider_id(parseIntValue(data[15]));
				}
				if (data[16].length() != 0) {
					detailsExtra.setTertiary_provider(data[16]);
				}
				detailsExtra.setStatus(data[10]);
				sq.add(detailsExtra);
				count++;
				System.out.println("Station Extra details stored");

			}

			// Close the input stream
			System.out.println("count" + count);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Station_Details parseStationDetails(String[] data) {
		Station_Details station_Details = new Station_Details();
		StationDetailsQuery sq = new StationDetailsQuery();
		System.out.println("1");
		station_Details.setSecondary_id(data[1]);
		System.out.println("2");
		station_Details.setStn(data[0]);
		System.out.println("3");
		station_Details.setStn_name(data[2]);
		System.out.println("4");
		station_Details.setCountry(data[4]);
		System.out.println("5");
		station_Details.setState(data[3]);

		System.out.println("6");
		double roundlat = (Math.round(parseDoubleValue(data[5]) * 100.0) / 100.0);
		station_Details.setLat(roundlat);

		double roundlon = (Math.round(parseDoubleValue(data[6]) * 100.0) / 100.0);
		System.out.println("7");
		station_Details.setLon(roundlon);

		double roundel = (Math.round(parseDoubleValue(data[7]) * 100.0) / 100.0);
		System.out.println("7");
		station_Details.setElev(roundel);

		System.out.println("8");
		station_Details.setMnet(parseIntValue(data[8]));
		System.out.println("9");
		station_Details.setNetwork_name(data[9]);
		if (data[9] != null || data[9].length() != 0)
			station_Details.setNetwork_name(data[9]);
		System.out.println("10");
		sq.add(station_Details);
		System.out.println("11");
		System.out.println("Station details stored");
		return station_Details;
	}

	double parseDoubleValue(String str) {
		Double temp = 0.0;
		try {
			temp = Double.parseDouble(str);
		} catch (NumberFormatException E) {
			// E.printStackTrace();
			temp = 0.0;
		}
		return temp;
	}

	int parseIntValue(String str) {
		int temp = 0;
		try {
			temp = Integer.parseInt(str);
		} catch (Exception E) {
			// E.printStackTrace();
			temp = 0;
		}
		return temp;
	}

	public void parseWeatherDataFile(String filename) throws ParseException {
		StationDetailsQuery sq = new StationDetailsQuery();
		WeatherQuery wq = new WeatherQuery();
		try {
			FileInputStream fstream = new FileInputStream(
					"c:/testfolder/mesowest_out_Mon Oct 15 125816 PDT 2012.out");

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
				System.out.println(data.length);
				if (data.length == 17) {
					Weather weather = new Weather();
					// weather.setStationCode(data[1]);
					// sq.findById(data[1]);
					Station_Details station_Details = new Station_Details();
					ArrayList<Station_Details> stationlist = new ArrayList<Station_Details>();
					stationlist = sq.findById(data[1]);
					if (stationlist.size() > 1) {
						double templat = parseDoubleValue(data[4]);
						double templon = parseDoubleValue(data[5]);
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
					java.util.Date date = (new SimpleDateFormat(INPUT1))
							.parse(in1);
					Timestamp t = new Timestamp(date.getTime());
					weather.setDate(t);
					// System.out.println(call);
					// weather.setMNET(data[3]);
					// weather.setSLAT(data[4]);
					// weather.setSLON(data[5]);
					// weather.setSELV(data[6]);
					weather.setTMPF(parseDoubleValue(data[7]));
					weather.setSKNT(parseDoubleValue(data[8]));
					weather.setDRCT(parseDoubleValue(data[9]));
					weather.setGUST(parseDoubleValue(data[10]));
					weather.setPMSL(parseDoubleValue(data[11]));
					weather.setALTI(parseDoubleValue(data[12]));
					weather.setDWPF(parseDoubleValue(data[13]));
					weather.setRELH(parseDoubleValue(data[14]));
					weather.setWTHR(parseDoubleValue(data[15]));
					weather.setP24I(parseDoubleValue(data[16]));

					System.out.println("Weather data stored");
					wq.add(weather);
				}

				else
					continue;
				// Close the input stream
			}
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
}

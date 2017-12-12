package connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;

import dto.GameInfo;
import etc.ResponseCode;
import etc.URLs;

public class GameInfoConnection {

	/**
	 * 게임정보 업데이트 메소드
	 * @param GameInfo
	 * @return 결과값
	 */
	@SuppressWarnings("unchecked")
	public String updateConnection(GameInfo gameInfo) {
		URL url;
		HttpURLConnection conn = null;
		String result;
		try {
			url = new URL(URLs.url+"gameInfo/update");
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			JSONObject json = new JSONObject();
			json.put("id", gameInfo.getId());
			json.put("roomId", gameInfo.getRoomId());
			json.put("inputNum", gameInfo.getInputNum());
			json.put("orderUserId", gameInfo.getOrderUserId());
			
			OutputStream out = conn.getOutputStream();
			out.write(json.toString().getBytes());
			out.flush();
			out.close();
			
			if(conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while((result = br.readLine())!=null) {
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = Integer.toString(ResponseCode.connect_error);
		return result; 	
	}
	
	/**
	 * 게임정보 setting 메소드(게임 시작전에 숫자 세팅)
	 * @param GameInfo
	 * @return 결과값
	 */
	@SuppressWarnings("unchecked")
	public String settingConnection(GameInfo gameInfo) {
		URL url;
		HttpURLConnection conn = null;
		String result;
		try {
			url = new URL(URLs.url+"gameInfo/setting");
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			JSONObject json = new JSONObject();
			json.put("id", gameInfo.getId());
			json.put("roomId", gameInfo.getRoomId());
			json.put("awayNumber", gameInfo.getAwayNumber());
			if(gameInfo.getHostNumber() != null) {
				json.put("hostNumber", gameInfo.getHostNumber());
			}
			
			
			OutputStream out = conn.getOutputStream();
			out.write(json.toString().getBytes());
			out.flush();
			out.close();
			if(conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while((result = br.readLine())!=null) {
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = Integer.toString(ResponseCode.connect_error);
		return result; 	
	}
	
	/**
	 * 게임이 끝난 후 방의 게임정보 초기화 메소드
	 * @param GameInfo
	 * @return 결과값
	 */
	public String gameEndConnection(int id) {
		URL url;
		HttpURLConnection conn = null;
		String result;
		try {
			url = new URL(URLs.url+"gameInfo/gameEnd?id="+id);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoInput(true);
			conn.setDoOutput(false);
			
			if(conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while((result = br.readLine())!=null) {
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = Integer.toString(ResponseCode.connect_error);
		return result; 	
	}
	
	/**
	 * id로 방의 게임정보 받아오는 메소드(준비 /준비확인)
	 * @param id
	 * @return GameInfo
	 */
	public String findByIdConnection(int id) {
		URL url;
		HttpURLConnection conn = null;
		String result;
		try {
			url = new URL(URLs.url+"gameInfo/findById?id="+id);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoInput(true);
			conn.setDoOutput(false);
			
			if(conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while((result = br.readLine())!=null) {
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = Integer.toString(ResponseCode.connect_error);
		return result; 	
	}
	
	/**
	 * roomId로 방의 게임정보 받아오는 메소드(준비 /준비확인)
	 * @param roomId
	 * @return GameInfo
	 */
	public String findByRoomIdConnection(int roomId) {
		URL url;
		HttpURLConnection conn = null;
		String result;
		try {
			url = new URL(URLs.url+"gameInfo/findByRoomId?roomId="+roomId);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoInput(true);
			conn.setDoOutput(false);
			
			if(conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while((result = br.readLine())!=null) {
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = Integer.toString(ResponseCode.connect_error);
		return result; 	
	}
	
	/**
	 * id로 방의 게임정보 삭제하는 메소드(방 제거시)
	 * @param id
	 * @return x
	 *//*
	public String deleteConnection(int id) {
		URL url;
		HttpURLConnection conn = null;
		String result;
		try {
			url = new URL(URLs.url+"gameInfo/delete?id="+id);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoInput(true);
			conn.setDoOutput(false);
			
			System.out.println(conn.getResponseCode());
			if(conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while((result = br.readLine())!=null) {
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = Integer.toString(ResponseCode.connect_error);
		return result; 	
	}
	
	*//**
	 * roomId로 방의 게임정보 삭제하는 메소드(방 제거시)
	 * @param roomId
	 * @return x
	 *//*
	public String deleteByRoomIdConnection(int roomId) {
		URL url;
		HttpURLConnection conn = null;
		String result;
		try {
			url = new URL(URLs.url+"gameInfo/deleteByRoomId?roomId="+roomId);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoInput(true);
			conn.setDoOutput(false);
			
			System.out.println(conn.getResponseCode());
			if(conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while((result = br.readLine())!=null) {
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = Integer.toString(ResponseCode.connect_error);
		return result; 	
	}*/
}

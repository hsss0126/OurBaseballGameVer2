package connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;

import dto.RoomInfo;
import etc.ResponseCode;
import etc.URLs;

public class RoomInfoConnection {

	/**
	 * 방 만들기 메소드
	 * @param roomName / level / hostId / hostNickName
	 * @return 결과값
	 */
	@SuppressWarnings("unchecked")
	public String createConnection(String ...arg) {
		URL url;
		HttpURLConnection conn = null;
		String result;
		try {
			url = new URL(URLs.url+"roomInfo/create");
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
			json.put("roomName", arg[0]);
			json.put("level", arg[1]);
			json.put("hostId", arg[2]);
			System.out.println("생성할 방 정보"+json.toString());
			
			OutputStream out = conn.getOutputStream();
			out.write(json.toString().getBytes());
			out.flush();
			out.close();
			System.out.println(conn.getResponseCode());
			if(conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while((result = br.readLine())!=null) {
					return result;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = Integer.toString(ResponseCode.connect_error);
		return result; 	
	}
	
	/**
	 * 생성된 방 목록 받아오기
	 * @param x
	 * @return 조회된 생성된 방목록
	 */
	public String listConnection(String ...arg) {
		URL url;
		HttpURLConnection conn = null;
		String result;
		try {
			String orderIndex = URLEncoder.encode(arg[0], "UTF-8");
			url = new URL(URLs.url+"roomInfo/list?orderBy="+orderIndex);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = Integer.toString(ResponseCode.connect_error);
		return result; 	
	}
	
	/**
	 * 방정보 업데이트 메소드 (방 입장 시 / 레벨 변경 시 / 방 나갈 시)
	 * @param RoomInfo
	 * @return 결과값
	 */
	@SuppressWarnings("unchecked")
	public String updateConnection(RoomInfo roomInfo) {
		URL url;
		HttpURLConnection conn = null;
		String result;
		try {
			url = new URL(URLs.url+"roomInfo/update");
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
			json.put("id", roomInfo.getId());
			json.put("hostId", roomInfo.getHostId());
			json.put("awayId", roomInfo.getAwayId());
			json.put("userCount", roomInfo.getUserCount());
			json.put("level", roomInfo.getLevel()!=0 ? roomInfo.getLevel() : 0);
			System.out.println("업데이트 할 방 정보"+json.toString());
			
			OutputStream out = conn.getOutputStream();
			out.write(json.toString().getBytes());
			out.flush();
			out.close();
			System.out.println(conn.getResponseCode());
			if(conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while((result = br.readLine())!=null) {
//					System.out.println(result);
					return result;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = Integer.toString(ResponseCode.connect_error);
		return result; 	
	}
	
	/**
	 * id로 방 정보 받아오는 메소드(방 정보 최신화)
	 * @param id
	 * @return x
	 */
	public String findByIdConnection(String ...arg) {
		URL url;
		HttpURLConnection conn = null;
		String result;
		try {
			String id = URLEncoder.encode(arg[0], "UTF-8");
			url = new URL(URLs.url+"roomInfo/findById?id="+id);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = Integer.toString(ResponseCode.connect_error);
		return result; 	
	}
	
	/**
	 * id로 방 삭제하는 메소드(방장 나갈 시 삭제)
	 * @param id
	 * @return 조회된 방 정보
	 */
	public String deleteConnection(String ...arg) {
		URL url;
		HttpURLConnection conn = null;
		String result;
		try {
			String id = URLEncoder.encode(arg[0], "UTF-8");
			url = new URL(URLs.url+"roomInfo/delete?id="+id);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = Integer.toString(ResponseCode.connect_error);
		return result; 	
	}
	
	/**
	 * id로 방 삭제하는 메소드(방장 나갈 시 삭제)
	 * @param id
	 * @return 조회된 방 정보
	 */
	public String deleteByHostIdConnection(String ...arg) {
		URL url;
		HttpURLConnection conn = null;
		String result;
		try {
			String id = URLEncoder.encode(arg[0], "UTF-8");
			url = new URL(URLs.url+"roomInfo/deleteByHostId?id="+id);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = Integer.toString(ResponseCode.connect_error);
		return result; 	
	}
}

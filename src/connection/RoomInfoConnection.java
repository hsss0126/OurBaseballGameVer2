package connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;

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
			System.out.println("연결 세팅");
			
			JSONObject json = new JSONObject();
			json.put("roomName", arg[0]);
			json.put("level", arg[1]);
			json.put("hostId", arg[2]);
			System.out.println(json.toString());
			
			OutputStream out = conn.getOutputStream();
			System.out.println("아웃풋 연결");
			out.write(json.toString().getBytes());
			out.flush();
			out.close();
			System.out.println("json전송");
			System.out.println(conn.getResponseCode());
			if(conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while((result = br.readLine())!=null) {
					System.out.println(result);
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
			System.out.println("연결 세팅");
			
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
